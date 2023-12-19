package com.v_petr.qrandbarcodescanner.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.v_petr.qrandbarcodescanner.data.model.User
import com.v_petr.qrandbarcodescanner.utils.FirebaseTables
import com.v_petr.qrandbarcodescanner.utils.SharedPrefConstants
import com.v_petr.qrandbarcodescanner.utils.UiState

class AuthRepositoryImpl(
    private val database: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val appPreferences: SharedPreferences,
    private val gson: Gson
) : AuthRepository {

    override fun registerUser(
        email: String,
        password: String,
        user: User, result: (UiState<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.id = auth.currentUser?.uid ?: ""
                    updateUserInfo(user) { state ->
                        when (state) {
                            is UiState.Success -> {
                                storeSession(id = user.id) { itUser ->
                                    if (itUser == null) {
                                        result.invoke(UiState.Failure("User register successfully, session is not stored"))
                                    } else {
                                        result.invoke(UiState.Success("User register successfully"))
                                    }
                                }
                            }

                            is UiState.Failure -> result.invoke(UiState.Failure(state.error))
                            else -> {}
                        }
                    }
                } else {
                    try {
                        throw task.exception ?: Exception("Invalid authentication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(UiState.Failure("Authentication failed, Password should be at least 6 characters"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(UiState.Failure("Authentication failed, Invalid email entered"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(UiState.Failure("Authentication failed, Email already registered."))
                    } catch (e: Exception) {
                        result.invoke(UiState.Failure(e.message))
                    }
                }
            }.addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storeSession(task.result.user?.uid ?: "") { user ->
                        if (user == null) {
                            result.invoke(UiState.Failure("Failed to store session"))

                        } else {
                            result.invoke(UiState.Success("Login successfully"))
                        }
                    }
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "loginUser: addOnFailureListener")
                result.invoke(UiState.Failure("Authentication failed"))
            }
    }

    override fun updateUserInfo(user: User, result: (UiState<String>) -> Unit) {
        val document = database.collection(FirebaseTables.USERS).document(user.id)
        document
            .set(user)
            .addOnCompleteListener {
                result.invoke(UiState.Success("User has been update successfully"))
            }
            .addOnFailureListener {
                Log.d(TAG, "updateUserInfo: addOnFailureListener")
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun forgotPassword(email: String, result: (UiState<String>) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                result.invoke(UiState.Success("Email has been sent"))
            } else {
                Log.d(TAG, "forgotPassword: forgotPassword addOnCompleteListener else")
                result.invoke(UiState.Failure(task.exception?.localizedMessage))
            }
        }.addOnFailureListener {
            Log.d(TAG, "forgotPassword: forgotPassword addOnFailureListener")
            result.invoke(UiState.Failure("Authentication failed, check email"))
        }
    }

    override fun logout(result: () -> Unit) {
        auth.signOut()
        appPreferences.edit().putString(SharedPrefConstants.USER_SESSION, null).apply()
        result.invoke()
    }

    override fun storeSession(id: String, result: (User?) -> Unit) {
        database.collection(FirebaseTables.USERS).document(id)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = it.result.toObject(User::class.java)
                    appPreferences.edit()
                        .putString(SharedPrefConstants.USER_SESSION, gson.toJson(user)).apply()
                    result.invoke(user)
                } else {
                    result.invoke(null)
                }
            }
            .addOnFailureListener {
                result.invoke(null)
            }
    }

    override fun getSession(result: (User?) -> Unit) {
        val userString = appPreferences.getString(SharedPrefConstants.USER_SESSION, null)
        if (userString == null) {
            result.invoke(null)
        } else {
            val user = gson.fromJson(userString, User::class.java)
            result.invoke(user)
        }
    }

    override fun getUser(result: (User?) -> Unit) {
        database.collection(FirebaseTables.USERS).document(auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "getUser DocumentSnapshot data: ${document.data}")
                    result.invoke(document.toObject(User::class.java))
                } else {
                    Log.d(TAG, "getUser No such document")
                }
            }.addOnFailureListener { exception ->
                Log.d(TAG, "getUser get failed with ", exception)
            }
    }

    companion object {
        const val TAG = "AuthRepositoryImpl"
    }
}
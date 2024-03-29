package com.v_petr.qrandbarcodescanner.di

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.v_petr.qrandbarcodescanner.data.repository.AuthRepository
import com.v_petr.qrandbarcodescanner.data.repository.AuthRepositoryImpl
import com.v_petr.qrandbarcodescanner.data.repository.MaterialIssueRecordRepository
import com.v_petr.qrandbarcodescanner.data.repository.MaterialIssueRecordRepositoryImpl
import com.v_petr.qrandbarcodescanner.data.repository.PartCodeRepository
import com.v_petr.qrandbarcodescanner.data.repository.PartCodeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMaterialIssueRecordRepositoryImpl(
        database: FirebaseFirestore,
    ): MaterialIssueRecordRepository {
        return MaterialIssueRecordRepositoryImpl(database)
    }

    @Provides
    @Singleton
    fun provideAuthRepositoryImpl(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
        appPreferences: SharedPreferences,
        gson: Gson
    ): AuthRepository {
        return AuthRepositoryImpl(database, auth, appPreferences, gson)
    }

    @Provides
    @Singleton
    fun providePartCodeRepositoryImpl(
        database: FirebaseDatabase
    ): PartCodeRepository {
        return PartCodeRepositoryImpl(database)
    }

}
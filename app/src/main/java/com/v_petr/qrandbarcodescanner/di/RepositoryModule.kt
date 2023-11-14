package com.v_petr.qrandbarcodescanner.di

import com.google.firebase.database.FirebaseDatabase
import com.v_petr.qrandbarcodescanner.data.repository.MaterialIssueRecordRepository
import com.v_petr.qrandbarcodescanner.data.repository.MaterialIssueRecordRepositoryImpl
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
        database: FirebaseDatabase,
    ): MaterialIssueRecordRepository {
        return MaterialIssueRecordRepositoryImpl(database)
    }
}
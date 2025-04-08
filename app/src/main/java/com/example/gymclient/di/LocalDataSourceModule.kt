package com.example.gymclient.di

import android.content.Context
import androidx.room.Room
import com.example.gymclient.data.db.AttendanceDao
import com.example.gymclient.data.db.Database
import com.example.gymclient.data.db.PaymentDao
import com.example.gymclient.data.db.WorkoutDao
import com.example.gymclient.data.repository.LocalDataSource
import com.example.gymclient.data.repository.LocalDataSourceImpl
import com.example.gymclient.domain.WorkoutRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "adminsDatabase"
        ).fallbackToDestructiveMigration()
            .build()
    }



    @Provides
    fun providePaymentDao(database: Database): PaymentDao {
        return database.getPayments()
    }

    @Provides
    fun provideAttendance(database: Database): AttendanceDao {
        return database.getAttendance()
    }

    @Provides
    fun provideWorkout(database: Database): WorkoutDao{
        return database.getWorkouts()
    }





    @Provides
    fun bindLocalDataSource(
        LocalDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource {
        return LocalDataSourceImpl
    }



}

package com.deloitte.wtest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.deloitte.wtest.database.postalcode.PostalCode
import com.deloitte.wtest.database.postalcode.PostalCodeDao

@Database(entities = [PostalCode::class], version = 2)
abstract class DatabaseConnection : RoomDatabase() {

    abstract val postalCodeDao: PostalCodeDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: DatabaseConnection

        fun getInstance(context: Context): DatabaseConnection {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, DatabaseConnection::class.java, "postal_code_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }


}
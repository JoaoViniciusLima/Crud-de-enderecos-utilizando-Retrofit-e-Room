package com.example.address.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.address.database.dao.AddressDao
import com.example.address.domain.Address

@Database(entities = [Address::class], version = 1)
abstract class DatabaseHelper : RoomDatabase() {

    abstract fun addressDao(): AddressDao

    companion object {
        fun getInstance(context: Context): DatabaseHelper {
            return Room.databaseBuilder(context, DatabaseHelper::class.java, "address.db")
                .allowMainThreadQueries()
                .build()
        }
    }

}
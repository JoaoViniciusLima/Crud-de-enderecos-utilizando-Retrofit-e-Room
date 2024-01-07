package com.example.address.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.address.domain.Address

@Dao
interface AddressDao {
    @Query("SELECT * FROM Address")
    fun findAll() :List<Address>
    @Query("SELECT * FROM Address WHERE id = :id")
    fun findByID(id : Long) : Address
    @Insert
    fun insert(address: Address)
    @Insert
    fun insertAll(vararg address: Address)
    @Delete
    fun delete(address: Address)
    @Update
    fun update(address: Address)
}
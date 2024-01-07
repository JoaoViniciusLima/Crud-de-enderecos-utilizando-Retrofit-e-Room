package com.example.address.domain

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Parcelize
@Entity
class Address(
    @PrimaryKey(autoGenerate = true) var id : Long = 0L,
    var cep: String,
    val rua: String,
    val cidade: String,
    val bairro: String,
    val estado: String,
): Parcelable
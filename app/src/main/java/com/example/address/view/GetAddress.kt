package com.example.address.view

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.address.MainActivity
import com.example.address.databinding.GetCepBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.address.domain.Address
import com.example.address.service.AddressService
import com.example.address.api.RetrofitHelper
import com.example.address.database.DatabaseHelper


class GetAddress: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = GetCepBinding.inflate(layoutInflater)

        val back = binding.back
        back.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val button = binding.confirmButton

        button.setOnClickListener{

            val cep = binding.editTextCep.text.toString()
            var address : Address?
            val scope = MainScope()
            val dao = DatabaseHelper.getInstance(this).addressDao()

            scope.launch {
                    try {
                    withContext(Dispatchers.IO) {
                        address =
                            AddressService(RetrofitHelper().addressApi()).findAddressByCep(cep)
                        if (address != null) {
                            address!!.cep = address!!.cep.substring(0, 5) + "-" + address!!.cep.substring(5, address!!.cep.length)
                            dao.insert(address!!)
                        }
                    }

                    if (address != null) {
                        val intent = Intent(this@GetAddress, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        binding.errorMenssage.visibility = View.VISIBLE
                    }

                    }catch (error: Exception){
                        Log.d("error", error.toString())
                    }
            }
        }

        setContentView(binding.root)
    }
}
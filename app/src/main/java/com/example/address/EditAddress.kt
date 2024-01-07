package com.example.address

import android.os.Build
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.address.database.DatabaseHelper
import com.example.address.databinding.EditAddressBinding
import com.example.address.domain.Address
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditAddress: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val address = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("address", Address::class.java)
        } else {
            intent.getParcelableExtra("address")
        }

        val binding = EditAddressBinding.inflate(layoutInflater)

        binding.editTextRua.setText(address?.rua)
        binding.editTextBairro.setText(address?.bairro)
        binding.editTextCidade.setText(address?.cidade)
        binding.editTextEstado.setText(address?.estado)
        binding.editTextCep.setText(address?.cep)



        val back = binding.back
        back.setOnClickListener{
            val intent = Intent(this, ListAddress::class.java)
            startActivity(intent)
        }

        val button = binding.confirmButton

        button.setOnClickListener {
            val cep = binding.editTextCep.text.toString()
            val rua = binding.editTextRua.text.toString()
            val bairro = binding.editTextBairro.text.toString()
            val cidade = binding.editTextCidade.text.toString()
            val estado = binding.editTextEstado.text.toString()

            val address = Address(id = address!!.id, cep = cep, bairro = bairro, cidade = cidade, estado = estado, rua = rua)

            val scope = MainScope()
            val dao = DatabaseHelper.getInstance(this).addressDao()

            scope.launch {
                try {
                    withContext(Dispatchers.IO) {
                        dao.update(address)
                    }

                    val intent = Intent(this@EditAddress, ListAddress::class.java)
                    startActivity(intent)

                    }catch (erro: Exception){
                        print(erro)
                    }
            }
        }


        setContentView(binding.root)
    }
}
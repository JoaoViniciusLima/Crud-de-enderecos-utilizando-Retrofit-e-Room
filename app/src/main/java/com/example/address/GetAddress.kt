package com.example.address

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.address.databinding.GetCepBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.address.domain.Address
import com.example.address.service.AddressService
import com.example.address.api.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import android.view.inputmethod.InputMethodManager
import android.content.Context

//import com.example.haro_agenda.databinding.FormNotaBinding
class GetAddress: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val binding = GetCepBinding.inflate(layoutInflater)

        val button = binding.botao

        button.setOnClickListener{

            var cep = binding.editTextCep.text.toString()
            var address : Address?
            val scope = MainScope()
            scope.launch {
                withContext(Dispatchers.IO) {
                     address =
                         AddressService(RetrofitHelper().addressApi()).findAddressByCep(cep)
                }
                if(address != null) {
                    binding.rua.setText(address?.rua)
                    binding.bairro.setText(" - ${address?.bairro}")
                    binding.cidade.setText("${address?.cidade}/${address?.estado}")
                    binding.cep.setText(" - ${address?.cep}")
                    binding.mensagemDeErro.visibility = View.INVISIBLE
                    binding.cardView.visibility = View.VISIBLE


                } else{
                    binding.mensagemDeErro.visibility = View.VISIBLE
                }
            }
            val view: View? = currentFocus

            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }


        }

        setContentView(binding.root)

    }
}
package com.example.address.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.example.address.R
import com.example.address.database.DatabaseHelper
import com.example.address.databinding.AddressCardBinding
import com.example.address.databinding.GetCepBinding
import com.example.address.domain.Address
import com.example.address.view.EditAddress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Adapter(val context: Context, val address: MutableList<Address>) : BaseAdapter() {
    override fun getCount(): Int {
        return address.size
    }

    override fun getItem(position: Int): Any {
        return address[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?,
    ): View {


        val inflater = LayoutInflater.from(context)
        val binding = AddressCardBinding.inflate(inflater, parent, false)

        val rua = binding.rua
        val bairro = binding.bairro
        val cidadeEstadoCep = binding.cidadeEstadoCep

        if(address[position].rua.isNotEmpty()){
            rua.text = address[position].rua

        } else{
            rua.text = "Rua não encontrada"
        }
        if(address[position].bairro.isNotEmpty()){
            bairro.text = address[position].bairro

        } else{
            bairro.text = "Bairro não encontrado"
        }

        cidadeEstadoCep.text = "${address[position].cidade}/${address[position].estado} - ${address[position].cep}"

        val popup = binding.popup
        popup.setOnClickListener{ view ->
            val popupMenu = PopupMenu(context, view)
            val inflater = popupMenu.menuInflater
            inflater.inflate(R.menu.popup, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.update -> {
                        val intent = Intent(context, EditAddress::class.java)
                        val bundle = Bundle()
                        bundle.putParcelable("address", address[position])

                        intent.putExtras(bundle)
                        context.startActivity(intent)
                        true
                    }
                    R.id.delete -> {
                        val scope = MainScope()
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                val dao = DatabaseHelper.getInstance(context).addressDao()
                                dao.delete(address[position])
                            }
                            address.removeAt(position)
                            notifyDataSetChanged()
                        }
                        true

                    }
                    else -> false
                }
            }

            popupMenu.show()
        }


        return binding.root
    }
}
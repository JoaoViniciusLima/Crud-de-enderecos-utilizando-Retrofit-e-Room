package com.example.address
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.example.address.database.DatabaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListAddress : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list)

        val navegationButton = findViewById<FloatingActionButton>(R.id.addAddressButton)

        val dao = DatabaseHelper.getInstance(this).addressDao()
        var adresses = dao.findAll()

        var listView = findViewById<ListView>(R.id.lista)

        var adapter = Adapter(this, adresses.toMutableList())

        listView.adapter = adapter

        navegationButton.setOnClickListener {
            val intent = Intent(this, GetAddress::class.java)
            startActivity(intent)
        }
    }
}

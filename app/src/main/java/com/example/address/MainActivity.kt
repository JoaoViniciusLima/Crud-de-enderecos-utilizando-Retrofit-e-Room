package com.example.address
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.address.adapters.Adapter
import com.example.address.database.DatabaseHelper
import com.example.address.view.GetAddress
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list)

        val navegationButton = findViewById<FloatingActionButton>(R.id.addAddressButton)

        val dao = DatabaseHelper.getInstance(this).addressDao()
        var adresses = dao.findAll()

        var listView = findViewById<ListView>(R.id.list)

        var adapter = Adapter(this, adresses.toMutableList())

        listView.adapter = adapter

        navegationButton.setOnClickListener {
            val intent = Intent(this, GetAddress::class.java)
            startActivity(intent)
        }
    }
}

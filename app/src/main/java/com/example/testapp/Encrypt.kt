package com.example.testapp

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.Class.encode

class Encrypt : AppCompatActivity() {
    private var etenc: EditText? = null
    private var enctv: TextView? = null
    private var save: Button? = null
    private var enc: Button? = null
    private var cpb: ClipboardManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encrypt)
        etenc = findViewById(R.id.input)
        enctv = findViewById(R.id.output)
        save = findViewById(R.id.button)
        enc = findViewById(R.id.btnSubmit)
        cpb = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        enc?.setOnClickListener{
            val temp = etenc?.text.toString()
            val rv = encode.enc(temp)
            enctv?.text = rv
        }
        save?.setOnClickListener{
            val data = enctv?.text.toString().trim { it <= ' ' }
            if (data.isNotEmpty()) {
                val temp = ClipData.newPlainText("text", data)
                cpb!!.setPrimaryClip(temp)
                Toast.makeText(applicationContext, "Copied. You can save them in 'ADD A NOTE'", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
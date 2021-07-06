package com.example.testapp

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.Class.decode

class Decrypt : AppCompatActivity() {
    private var etdec: EditText? = null
    private var dectv: TextView? = null
    private var a: Button? = null
    private var b: Button? = null
    private var cplboard: ClipboardManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decrypt)
        etdec = findViewById(R.id.input)
        dectv = findViewById(R.id.output)
        a = findViewById(R.id.btnSubmit)
        b = findViewById(R.id.copy)
        cplboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        a?.setOnClickListener {
            val temp = etdec?.text.toString()
            val rv = decode.dec(temp)
            dectv?.text = rv
        }
        b?.setOnClickListener {
            val data = dectv?.text.toString().trim { it <= ' ' }
            if (data.isNotEmpty()) {
                val temp = ClipData.newPlainText("text", data)
                cplboard!!.setPrimaryClip(temp)
                Toast.makeText(applicationContext, "Copied. You can save them in 'ADD A NOTE'", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
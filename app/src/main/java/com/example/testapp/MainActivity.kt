package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.os.Process.killProcess
import android.os.Process.myPid
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private var addnote: CardView? = null
    private var notes: CardView? = null
    private var encrypt: CardView? = null
    private var decrypt: CardView? = null
    private var guide: CardView? = null
    private var logout: CardView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logout = findViewById(R.id.kill)
        encrypt = findViewById(R.id.test_donation)
        decrypt = findViewById(R.id.test_receive)
        addnote = findViewById(R.id.test_chat2)
        notes = findViewById(R.id.test_ngo)
        guide = findViewById(R.id.test_chat)
        guide?.setOnClickListener { startActivity(Intent(applicationContext, Guide::class.java)) }
        addnote?.setOnClickListener { startActivity(Intent(applicationContext, AddNote::class.java)) }
        notes?.setOnClickListener { startActivity(Intent(applicationContext, Notes::class.java))
            Toast.makeText(applicationContext, "Swipe a note in any direction to delete it", Toast.LENGTH_LONG).show()}
        encrypt?.setOnClickListener { startActivity(Intent(applicationContext, Encrypt::class.java)) }
        decrypt?.setOnClickListener { startActivity(Intent(applicationContext, Decrypt::class.java)) }
        logout?.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(applicationContext, Login::class.java))
            finish()
        }
    }
}
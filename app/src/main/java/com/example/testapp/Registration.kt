package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Registration : AppCompatActivity() {
    private var mEmail: EditText? = null
    private var mPassword: EditText? = null
    private var mRegisterBtn: Button? = null
    private var mLoginBtn: TextView? = null
    private var fAuth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        mEmail = findViewById(R.id.Email)
        mPassword = findViewById(R.id.password)
        mRegisterBtn = findViewById(R.id.registerBtn)
        mLoginBtn = findViewById(R.id.createText)
        progressBar = findViewById(R.id.progressBar)
        fAuth = FirebaseAuth.getInstance()
        if (fAuth!!.currentUser != null) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
        mRegisterBtn?.setOnClickListener(View.OnClickListener {
            val email = mEmail?.text.toString().trim { it <= ' ' }
            val password = mPassword?.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(email)) {
                mEmail?.error = "Email is Required"
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                mPassword?.error = "Password is Required"
                return@OnClickListener
            }
            if (password.length < 6) {
                mPassword?.error = "Password must be greater than 6 characters"
                return@OnClickListener
            }
            progressBar?.visibility = View.VISIBLE
            fAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@Registration, "User Created", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    Toast.makeText(this@Registration, "Error!!!" + task.exception!!.message, Toast.LENGTH_SHORT).show()
                    progressBar?.visibility = View.GONE
                }
            }
        })
        mLoginBtn?.setOnClickListener { startActivity(Intent(applicationContext, Login::class.java)) }
    }

    companion object
}
package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private var mEmail: EditText? = null
    private var mPassword: EditText? = null
    private var mLoginBtn: Button? = null
    private var mCreateBtn: TextView? = null
    private var btnReset: TextView? = null
    private var fAuth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null
    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mEmail = findViewById(R.id.editText2)
        mPassword = findViewById(R.id.editText3)
        progressBar = findViewById(R.id.progressBar2)
        fAuth = FirebaseAuth.getInstance()
        mLoginBtn = findViewById(R.id.loginBtn)
        mCreateBtn = findViewById(R.id.createText)
        btnReset = findViewById(R.id.btn_reset_password)
        mLoginBtn?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val email = mEmail?.text.toString().trim { it <= ' ' }
                val password = mPassword?.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(email)) {
                    mEmail?.error = "Email is Required"
                    return
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword?.error = "Password is Required"
                    return
                }
                if (password.length < 6) {
                    mPassword?.error = "Password must be greater than 6 characters"
                    return
                }
                progressBar?.visibility = View.VISIBLE
                fAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    } else {
                        Toast.makeText(this@Login, "Error!!!" + task.exception!!.message, Toast.LENGTH_SHORT).show()
                        progressBar?.visibility = View.GONE
                    }
                }
            }
        })
        mCreateBtn?.setOnClickListener { startActivity(Intent(applicationContext, Registration::class.java)) }
        btnReset?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val email = mEmail?.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(application, "Enter your registered email id", Toast.LENGTH_SHORT).show()
                    return
                }
                progressBar?.visibility = View.VISIBLE
                fAuth!!.sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this@Login, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@Login, "Failed to send reset email!", Toast.LENGTH_SHORT).show()
                            }
                            progressBar?.visibility = View.GONE
                        }
            }
        })
    }
    companion object
}
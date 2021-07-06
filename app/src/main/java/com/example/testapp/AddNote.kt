package com.example.testapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.*

class AddNote : AppCompatActivity() {
    private var details: EditText? = null
    private var submit: Button? = null
    private var fAuth: FirebaseAuth? = null
    var fStore: FirebaseFirestore? = null
    var userId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        details = findViewById(R.id.details)
        submit = findViewById(R.id.btnSubmit)
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        userId = Objects.requireNonNull(fAuth!!.currentUser)?.uid
        submit?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (v.id == R.id.btnSubmit) {
                    alertDialog()
                }
            }

            private fun alertDialog() {
                val dialog = AlertDialog.Builder(this@AddNote)
                dialog.setMessage("Notes once saved cannot be edited. However you can delete the note and save it once again.")
                dialog.setTitle("Warning")
                dialog.setPositiveButton("I Agree"
                ) { _: DialogInterface?, _: Int ->
                    if (details?.text.isNullOrEmpty()){
                        details?.error="Note is Empty"
                        return@setPositiveButton
                    }
                    val details=details?.text.toString()
                    val documentReference = fStore!!.collection("users").document(userId!!).collection("Notes").document()
                    val user: MutableMap<String, Any> = HashMap()
                    user["Title"] = details
                    user["time"] = FieldValue.serverTimestamp()
                    user["UserId"] = userId!!
                    documentReference.set(user, SetOptions.merge()).addOnSuccessListener { Toast.makeText(this@AddNote, "Note Saved", Toast.LENGTH_SHORT).show() }.addOnFailureListener { Toast.makeText(this@AddNote, "Details not Submitted", Toast.LENGTH_SHORT).show() }
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    Toast.makeText(applicationContext, "You will now be redirected to the Home Page", Toast.LENGTH_LONG).show()
                }
                dialog.setNegativeButton("Go Back") { _: DialogInterface?, _: Int -> }
                val alertDialog = dialog.create()
                alertDialog.show()
            }
        })
    }
}
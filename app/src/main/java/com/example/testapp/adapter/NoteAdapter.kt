package com.example.testapp.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.adapter.NoteAdapter.NoteHolder
import com.example.testapp.Class.Note
import com.example.testapp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
class NoteAdapter(options: FirestoreRecyclerOptions<Note?>) : FirestoreRecyclerAdapter<Note, NoteHolder>(options) {
    override fun onBindViewHolder(holder: NoteHolder, position: Int, model: Note) {
        holder.textViewTitle.text = model.Title
        holder.cb.setOnClickListener {
            val cpb = holder.textViewTitle.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val data = holder.textViewTitle.text.toString().trim { it <= ' ' }
            if (data.isNotEmpty()) {
                val temp = ClipData.newPlainText("text", data)
                cpb.setPrimaryClip(temp)
                Toast.makeText(holder.itemView.context, "Copied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteHolder(v)
    }
    fun deleteItem(position: Int) {
        snapshots.getSnapshot(position).reference.delete()
    }
    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView = itemView.findViewById(R.id.text_view_name)
        var cb: ImageButton = itemView.findViewById(R.id.copybut)
    }
}
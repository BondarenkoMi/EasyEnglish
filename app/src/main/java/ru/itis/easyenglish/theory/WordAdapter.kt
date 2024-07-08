package ru.itis.easyenglish.theory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.itis.easyenglish.R

class WordAdapter(private val words: List<Word>) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]
        holder.bind(word)
    }

    override fun getItemCount() = words.size

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: Word) {
            itemView.findViewById<TextView>(R.id.word_text).text = "${word.value} - ${word.translate}"
        }
    }
}
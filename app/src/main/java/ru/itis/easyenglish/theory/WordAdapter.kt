package ru.itis.easyenglish.theory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.itis.easyenglish.R

class WordAdapter(private val words: List<WordEntity>) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val englishWord: TextView = itemView.findViewById(R.id.english_word)
        val russianWord: TextView = itemView.findViewById(R.id.russian_word)
        val wordLevel: TextView = itemView.findViewById(R.id.level)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]
        holder.englishWord.text = word.englishWord
        holder.russianWord.text = word.russianWord
        val levelText = when (word.level) {
            1 -> "A1"
            2 -> "A2"
            3 -> "B1"
            4 -> "B2"
            else -> "Unknown"
        }
        holder.wordLevel.text = "$levelText"
    }

    override fun getItemCount(): Int {
        return words.size
    }
}
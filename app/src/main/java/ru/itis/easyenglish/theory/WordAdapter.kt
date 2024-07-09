package ru.itis.easyenglish.theory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
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
            val wordTextView = itemView.findViewById<TextView>(R.id.word_text)
            wordTextView.text = "${word.value} - ${word.translate}"

            val favoriteButton = itemView.findViewById<ImageButton>(R.id.favorite_button)
            updateFavoriteButton(favoriteButton, word.isFavorite)

            if (word.isPassed) {
                itemView.setBackgroundColor(0xFF00FF00.toInt()) // Зеленый фон
            } else {
                itemView.setBackgroundColor(0xFFFF0000.toInt()) // Красный фон
            }

            favoriteButton.setOnClickListener {
                if (word.isFavorite) {
                    word.isFavorite = false
                    updateFavoriteButton(favoriteButton, false)
                    Snackbar.make(itemView, "Убрано из избранных.", Snackbar.LENGTH_SHORT).show()
                } else {
                    word.isFavorite = true
                    updateFavoriteButton(favoriteButton, true)
                    Snackbar.make(itemView, "Добавлено в избранные.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        private fun updateFavoriteButton(button: ImageButton, isFavorite: Boolean) {
            if (isFavorite) {
                button.setImageResource(R.drawable.star_yellow)
            } else {
                button.setImageResource(R.drawable.star)
            }
        }
    }
}
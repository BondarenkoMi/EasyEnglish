package ru.itis.easyenglish.theory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.itis.easyenglish.R
class WordAdapter(
    private var words: List<WordEntity>,
    private val wordRepository: WordRepository
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        return WordViewHolder(view, wordRepository)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]
        holder.bind(word)
    }

    override fun getItemCount() = words.size

    fun updateWords(newWords: List<WordEntity>) {
        this.words = newWords
        notifyDataSetChanged()
    }

    class WordViewHolder(itemView: View, private val wordRepository: WordRepository) : RecyclerView.ViewHolder(itemView) {
        private val wordTextView: TextView = itemView.findViewById(R.id.word_text)
        private val favoriteButton: ImageButton = itemView.findViewById(R.id.favorite_button)

        fun bind(word: WordEntity) {
            wordTextView.text = "${word.englishWord} - ${word.russianWord}"
            updateFavoriteButton(word.savedStatus == true)

            when (word.completedStatus) {
                true -> wordTextView.setTextColor(0xFF7fff00.toInt()) // Зеленый
                false -> wordTextView.setTextColor(0xFFFB607F.toInt()) // Красный
                null -> wordTextView.setTextColor(0xFF808080.toInt()) // Серый
            }

            favoriteButton.setOnClickListener {
                if (word.savedStatus == true) {
                    deleteFromFavorites(word)
                } else {
                    addToFavorites(word)
                }
            }
        }

        private fun addToFavorites(word: WordEntity) {
            word.savedStatus = true
            updateFavoriteButton(true)

            var snackbar = Snackbar.make(itemView, "Добавлено в избранные.", Snackbar.LENGTH_SHORT)
            var viewsnack = snackbar.getView()
            val params = viewsnack.layoutParams as FrameLayout.LayoutParams
            params.bottomMargin = 170
            viewsnack.layoutParams = params
            snackbar.show()

            CoroutineScope(Dispatchers.IO).launch {
                wordRepository.updateWord(word)
            }
        }

        private fun deleteFromFavorites(word: WordEntity) {
            word.savedStatus = false
            updateFavoriteButton(false)

            var snackbar = Snackbar.make(itemView, "Удалено из избранных.", Snackbar.LENGTH_SHORT)
            var viewsnack = snackbar.getView()
            val params = viewsnack.layoutParams as FrameLayout.LayoutParams
            params.bottomMargin = 170
            viewsnack.layoutParams = params
            snackbar.show()

            CoroutineScope(Dispatchers.IO).launch {
                wordRepository.updateWord(word)
            }
        }

        private fun updateFavoriteButton(isFavorite: Boolean) {
            if (isFavorite) {
                favoriteButton.setImageResource(R.drawable.star_filled_button)
            } else {
                favoriteButton.setImageResource(R.drawable.star_button)
            }
        }
    }
}
package ru.itis.easyenglish.theory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.itis.easyenglish.R

class TheoryLevelAdapter(
    private val words: List<WordEntity>,
    private val wordRepository: WordRepository
) : RecyclerView.Adapter<TheoryLevelAdapter.LevelViewHolder>() {

    private val levels = listOf("A1", "A2", "B1", "B2")
    private val levelWords = mapOf(
        "A1" to words.filter { it.level == 1 },
        "A2" to words.filter { it.level == 2 },
        "B1" to words.filter { it.level == 3 },
        "B2" to words.filter { it.level == 4 }
    )
    private val expandedLevels = mutableSetOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.theory_item_level, parent, false)
        return LevelViewHolder(view, wordRepository)
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val level = levels[position]
        holder.bind(level, levelWords[level] ?: emptyList(), expandedLevels.contains(level))
        holder.itemView.setOnClickListener {
            if (expandedLevels.contains(level)) {
                expandedLevels.remove(level)
            } else {
                expandedLevels.add(level)
            }
            notifyItemChanged(position)
        }
    }

    override fun getItemCount() = levels.size

    class LevelViewHolder(itemView: View, private val wordRepository: WordRepository) : RecyclerView.ViewHolder(itemView) {
        private val levelTextView: TextView = itemView.findViewById(R.id.level_text)
        private val favoritesSwitch: Switch = itemView.findViewById(R.id.favorites_switch)
        private val levelContainer: RelativeLayout = itemView.findViewById(R.id.level_container)
        private val wordsListView: RecyclerView = itemView.findViewById(R.id.words_list)
        private lateinit var wordAdapter: WordAdapter

        fun bind(level: String, words: List<WordEntity>, isExpanded: Boolean) {
            levelTextView.text = level
            when (level) {
                "A1" -> levelContainer.setBackgroundColor(0xFF5DD377.toInt())
                "A2" -> levelContainer.setBackgroundColor(0xFF62CEDA.toInt())
                "B1" -> levelContainer.setBackgroundColor(0xFFF2F54F.toInt())
                "B2" -> levelContainer.setBackgroundColor(0xFFF78336.toInt())
            }

            favoritesSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (wordsListView.isVisible) {
                    filterWords(words, isChecked)
                } else {
                    favoritesSwitch.isChecked = false
                    Snackbar.make(itemView, "Сначала откройте список", Snackbar.LENGTH_SHORT).show()
                }
            }

            if (isExpanded) {
                wordsListView.isVisible = true
                wordsListView.alpha = 0f
                wordsListView.animate()
                    .alpha(1f)
                    .setDuration(300)
                    .setListener(null)
                wordsListView.layoutManager = LinearLayoutManager(itemView.context)
                wordAdapter = WordAdapter(words, wordRepository)
                wordsListView.adapter = wordAdapter
            } else {
                wordsListView.animate()
                    .alpha(0f)
                    .setDuration(300)
                    .withEndAction {
                        wordsListView.isVisible = false
                    }
            }
        }

        private fun filterWords(words: List<WordEntity>, showFavorites: Boolean) {
            val filteredWords = if (showFavorites) {
                words.filter { it.savedStatus == true }
            } else {
                words
            }
            wordAdapter.updateWords(filteredWords)
        }
    }
}

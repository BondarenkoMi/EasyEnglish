package ru.itis.easyenglish.theory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
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
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        private val favoritesSwitch: com.google.android.material.switchmaterial.SwitchMaterial = itemView.findViewById(R.id.favorites_switch)
        private val levelContainer: androidx.cardview.widget.CardView = itemView.findViewById(R.id.level_container)
        private val wordsListView: RecyclerView = itemView.findViewById(R.id.words_list)
        private lateinit var wordAdapter: WordAdapter

        fun bind(level: String, words: List<WordEntity>, isExpanded: Boolean) {
            levelTextView.text = level
            when (level) {
                "A1" -> levelContainer.setBackgroundResource(R.drawable.gradient1)
                "A2" -> levelContainer.setBackgroundResource(R.drawable.gradient2)
                "B1" -> levelContainer.setBackgroundResource(R.drawable.gradient3)
                "B2" -> levelContainer.setBackgroundResource(R.drawable.gradient4)
            }

            favoritesSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (wordsListView.isVisible) {
                    filterWords(words, isChecked)
                } else {
                    favoritesSwitch.isChecked = false
                    var snackbar = Snackbar.make(itemView, "Сначала откройте список", Snackbar.LENGTH_SHORT)
                    var viewsnack = snackbar.getView()
                    val params = viewsnack.layoutParams as FrameLayout.LayoutParams
                    params.bottomMargin = 170
                    viewsnack.layoutParams = params
                    snackbar.show()
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

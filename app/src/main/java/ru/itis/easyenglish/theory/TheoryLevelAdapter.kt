package ru.itis.easyenglish.theory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.itis.easyenglish.R

class TheoryLevelAdapter(private val words: List<Word>) : RecyclerView.Adapter<TheoryLevelAdapter.LevelViewHolder>() {

    private val levels = listOf("A1", "A2", "B1", "B2")
    private val levelWords = mapOf(
        "A1" to words.filter { it.dif == 1 },
        "A2" to words.filter { it.dif == 2 },
        "B1" to words.filter { it.dif == 3 },
        "B2" to words.filter { it.dif == 4 }
    )
    private val expandedLevels = mutableSetOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.theory_item_level, parent, false)
        return LevelViewHolder(view)
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
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = levels.size

    class LevelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(level: String, words: List<Word>, isExpanded: Boolean) {
            val levelTextView = itemView.findViewById<TextView>(R.id.level_text)
            levelTextView.text = level
            when (level) {
                "A1" -> levelTextView.setBackgroundColor(0xFF5DD377.toInt())
                "A2" -> levelTextView.setBackgroundColor(0xFF62CEDA.toInt())
                "B1" -> levelTextView.setBackgroundColor(0xFFF2F54F.toInt())
                "B2" -> levelTextView.setBackgroundColor(0xFFF78336.toInt())
            }
            val wordsListView = itemView.findViewById<RecyclerView>(R.id.words_list)
            if (isExpanded) {
                wordsListView.visibility = View.VISIBLE
                wordsListView.layoutManager = LinearLayoutManager(itemView.context)
                wordsListView.adapter = WordAdapter(words)
            } else {
                wordsListView.visibility = View.GONE
            }
        }
    }
}
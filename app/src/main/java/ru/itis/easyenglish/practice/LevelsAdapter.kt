package ru.itis.easyenglish.practice

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.itis.easyenglish.R
import ru.itis.easyenglish.theory.WordRepository

class LevelsAdapter(private val wordRepository: WordRepository) : RecyclerView.Adapter<LevelsAdapter.LevelViewHolder>() {
    val levels: List<Level> = listOf(
        Level(wordRepository, 1, 0),
        Level(wordRepository, 2, 0),
        Level(wordRepository, 3, 0),
        Level(wordRepository, 4, 0),
    )
    private var totalWordsCount: List<Int> = listOf(0, 0, 0, 0)
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class LevelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val level: TextView = itemView.findViewById(R.id.levelDif)
        val backFade: ConstraintLayout = itemView.findViewById(R.id.backFade)
        val countWords: TextView = itemView.findViewById(R.id.count_words)
        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_level, parent, false)
        return LevelViewHolder(itemView)
    }

    override fun getItemCount(): Int = levels.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val currentItem = levels[position]
        when (currentItem.dif) {
            1 -> holder.level.text = "A1"
            2 -> holder.level.text = "A2"
            3 -> holder.level.text = "B1"
            4 -> holder.level.text = "B2"
        }
        holder.countWords.text = "${currentItem.countWords}/${totalWordsCount[position]}"
        when (position) {
            0 -> holder.backFade.setBackgroundResource(R.drawable.gradient1)
            1 -> holder.backFade.setBackgroundResource(R.drawable.gradient2)
            2 -> holder.backFade.setBackgroundResource(R.drawable.gradient3)
            3 -> holder.backFade.setBackgroundResource(R.drawable.gradient4)
        }
        holder.itemView.setOnClickListener {
            holder.itemView.findNavController().navigate(
                resId = R.id.action_navigation_practice_main_to_levelFragment,
                args = LevelFragment.bundle(
                    position + 1
                )
            )
        }
    }

    fun setWordCount(position: Int, count: Int) {
        levels[position].countWords = count
        notifyItemChanged(position)
    }

    fun setTotalWordsCount(totalWordsCount: List<Int>) {
        this.totalWordsCount = totalWordsCount
        notifyDataSetChanged()
    }
}
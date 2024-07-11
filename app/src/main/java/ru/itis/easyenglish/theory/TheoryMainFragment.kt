package ru.itis.easyenglish.theory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.itis.easyenglish.R

class TheoryMainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var levelAdapter: TheoryLevelAdapter
    private lateinit var wordRepository: WordRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_theory_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        wordRepository = WordRepository(WordDatabase.getDatabase(requireContext()).wordDao())

        lifecycleScope.launch {
            val words = wordRepository.getWords()
            levelAdapter = TheoryLevelAdapter(words, wordRepository)
            recyclerView.adapter = levelAdapter
        }

        // Показ Snackbar с сообщением
        var snackbar = Snackbar.make(view, "Чтобы открыть слова, нажмите на нужный уровень", Snackbar.LENGTH_SHORT)
        var viewsnack = snackbar.getView()
        val params = viewsnack.layoutParams as FrameLayout.LayoutParams
        params.bottomMargin = 170
        viewsnack.layoutParams = params
        snackbar.show()
    }
}
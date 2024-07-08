package ru.itis.easyenglish.theory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.itis.easyenglish.R

class TheoryMainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TheoryLevelAdapter
    private val wordRepository = WordRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_theory_main, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = TheoryLevelAdapter(wordRepository.getWords())
        recyclerView.adapter = adapter
        return view
    }
}

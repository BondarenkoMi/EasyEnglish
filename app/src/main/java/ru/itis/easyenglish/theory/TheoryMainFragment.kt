package ru.itis.easyenglish.theory

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.itis.easyenglish.R


class TheoryMainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TheoryLevelAdapter
    private val wordRepository = WordRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_theory_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = TheoryLevelAdapter(wordRepository.getWords())
        recyclerView.adapter = adapter

        val snackbar = Snackbar.make(view, "Чтобы открыть слова, нажмите на нужный уровень",
            Snackbar.LENGTH_SHORT).setAction("Action", null)
        val viewSnack: View = snackbar.getView()
        val params = viewSnack.layoutParams as FrameLayout.LayoutParams
        params.bottomMargin = 200
        viewSnack.layoutParams = params
        snackbar.show()
    }
}
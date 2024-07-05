package ru.itis.easyenglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.itis.easyenglish.databinding.FragmentPracticeMainBinding

class PraciticeMainFragment : Fragment() {
    private var binding: FragmentPracticeMainBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LevelsAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPracticeMainBinding.bind(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_practice_main, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.levels_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = LevelsAdapter()
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        return TODO("Provide the return value")
    }
}
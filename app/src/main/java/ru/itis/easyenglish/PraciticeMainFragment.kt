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
    private var _binding: FragmentPracticeMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LevelsAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPracticeMainBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(requireContext())
        binding.levelsList.layoutManager = layoutManager

        adapter= LevelsAdapter()
        binding.levelsList.adapter = adapter
    }
}

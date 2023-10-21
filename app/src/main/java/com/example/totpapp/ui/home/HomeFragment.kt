package com.example.totpapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.totpapp.R
import com.example.totpapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val itemModelList = ArrayList<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        recyclerView = binding.listView.findViewById(R.id.listView)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapter = CustomListAdapter(itemModelList)
            recyclerView.adapter = adapter
        }
    }

    private fun loadData(){
        val item1 = HomeViewModel("RUG", "giouri123", "234682", 30)
        val item2 = HomeViewModel("RUG", "elena123", "981223", 30)
        val item3 = HomeViewModel("RUG", "johana123", "645732", 30)
        itemModelList.add(item1)
        itemModelList.add(item2)
        itemModelList.add(item3)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
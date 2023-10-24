package com.example.totpapp.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.totpapp.R
import com.example.totpapp.databinding.FragmentHomeBinding
import java.time.Instant

private val itemModelList = ArrayList<HomeViewModel>()

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentHomeBinding? = null
    private var item_list = ArrayList<String>()

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        recyclerView = binding.listView.findViewById(R.id.listView)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        arguments?.let {
            it.getStringArrayList("fields")?.let {fields ->
                item_list = fields
                val new_entry = HomeViewModel(item_list[0], item_list[1], item_list[2], 30)
                itemModelList.add(new_entry)
                Log.e("FIELDSSSSSSSSSSSSSSSSSSSSSSSSS",item_list[0])
            }
        }
        arguments = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUniTimeInSec(): Long {
        val timeValue = Instant.now()
        return timeValue.epochSecond
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
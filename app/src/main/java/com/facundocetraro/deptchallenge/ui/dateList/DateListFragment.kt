package com.facundocetraro.deptchallenge.ui.dateList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.facundocetraro.deptchallenge.data.model.DateWithPhotos
import com.facundocetraro.deptchallenge.databinding.FragmentDateListBinding
import com.facundocetraro.deptchallenge.viewModel.DateListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DateListFragment : Fragment() {

    private lateinit var binding: FragmentDateListBinding

    private val dateListViewModel: DateListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDateListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchDateList()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val dateListAdapter = DateListAdapter(DateListAdapter.OnClickListener {
           showDatePhotos(it)
        })
        binding.dateList.apply {
            adapter = dateListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        lifecycle.coroutineScope.launch {
            dateListViewModel.getImageDatesFlow().collect { imageDateList ->
                dateListAdapter.submitList(imageDateList)
            }
        }

    }

    private fun showDatePhotos(dateWithPhotos: DateWithPhotos) {
        val action =
            DateListFragmentDirections.actionDateListFragmentToPhotoListFragment(dateWithPhotos.imageDate.date)
        findNavController().navigate(action)
    }

    private fun fetchDateList() {
        dateListViewModel.fetchDateList()
    }

}


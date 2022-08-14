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
import coil.load
import com.facundocetraro.deptchallenge.R
import com.facundocetraro.deptchallenge.data.model.DateWithPhotos
import com.facundocetraro.deptchallenge.databinding.FragmentDateListBinding
import com.facundocetraro.deptchallenge.util.ConnectivityUtil
import com.facundocetraro.deptchallenge.viewModel.DateListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DateListFragment : Fragment() {

    private var dateListAdapter: DateListAdapter? = null
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

        dateListViewModel.downloadStatus.observe(viewLifecycleOwner) { downloadStatus ->
            binding.progress.visibility = View.GONE
            when (downloadStatus) {
                DateListViewModel.DownloadStatus.CONTAIN_ELEMENTS -> {
                    binding.dateList.visibility = View.VISIBLE
                    binding.errorContainer.visibility = View.GONE
                }
                DateListViewModel.DownloadStatus.EMPTY_ELEMENTS -> {
                    showEmptyElementMessage()
                }
                else -> {
                    binding.dateList.visibility = View.GONE
                    binding.errorContainer.visibility = View.VISIBLE
                    binding.errorImage.load(R.drawable.warning_error)
                    binding.errorMessage.text = getString(R.string.error_fetching_elements)
                }
            }
        }
        binding.tryAgainButton.setOnClickListener {
            fetchDateList()
        }
    }

    private fun initRecyclerView() {
        if (dateListAdapter == null) {
            dateListAdapter = DateListAdapter(DateListAdapter.OnClickListener {
                showDatePhotos(it)
            })
        }

        binding.dateList.apply {
            adapter = dateListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        lifecycle.coroutineScope.launch {
            dateListViewModel.getImageDatesFlow().collect { imageDateList ->
                dateListAdapter?.submitList(imageDateList)
                binding.progress.visibility = View.GONE
                if (imageDateList.isEmpty()) {
                    checkInternet()
                } else {
                    binding.dateList.visibility = View.VISIBLE
                    binding.errorContainer.visibility = View.GONE
                }
            }
        }
    }

    private fun checkInternet() {
        if (!ConnectivityUtil.hasInternetConnection(requireContext().applicationContext)) {
            binding.dateList.visibility = View.GONE
            binding.errorContainer.visibility = View.VISIBLE
            binding.errorMessage.text = getString(R.string.there_is_not_internet)
            binding.errorImage.load(R.drawable.warning_error)
        } else {
            showEmptyElementMessage()
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

    private fun showEmptyElementMessage() {
        binding.dateList.visibility = View.GONE
        binding.errorContainer.visibility = View.VISIBLE
        binding.errorMessage.text = getString(R.string.empty_elements)
        binding.errorImage.load(R.drawable.empty_element)
    }

}


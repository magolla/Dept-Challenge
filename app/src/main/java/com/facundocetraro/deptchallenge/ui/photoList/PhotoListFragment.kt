package com.facundocetraro.deptchallenge.ui.photoList

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.facundocetraro.deptchallenge.R
import com.facundocetraro.deptchallenge.databinding.FragmentPhotoListBinding
import com.facundocetraro.deptchallenge.viewModel.PhotoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotoListFragment : Fragment() {

    private lateinit var binding: FragmentPhotoListBinding

    private val photoListViewModel: PhotoListViewModel by viewModels()

    private val args: PhotoListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        fetchPhotoList()
        binding.photoToolbar.title = args.photoDate

    }

    private fun fetchPhotoList() {
        photoListViewModel.fetchImagesFromDate(args.photoDate)
    }

    private fun initRecyclerView() {
        val photoAdapter = PhotoListAdapter(PhotoListAdapter.OnClickListener { photo ->
            if (photo.localUri == null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.image_not_loaded_yet),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val result =
                    PhotoListFragmentDirections.actionPhotoListFragmentToPhotoScreenFragment(photo.identifier)
                findNavController().navigate(result)
            }
        })
        binding.photoList.apply {
            adapter = photoAdapter
            val spanCount =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2
            layoutManager = GridLayoutManager(requireContext(), spanCount)
        }
        lifecycle.coroutineScope.launch {
            photoListViewModel.getPhotosFlow(args.photoDate).collect { photoList ->
                photoAdapter.submitList(photoList)
                shouldChangeLoadedIcon(photoList.isNotEmpty() && photoList.all { it.localUri != null })
            }
        }
    }

    private fun shouldChangeLoadedIcon(allImagesDownloaded: Boolean) {
        if (allImagesDownloaded) {
            binding.loadingIcon.load(R.drawable.ic_baseline_play_arrow_24)
            binding.loadingIcon.setOnClickListener {

            }
        } else {
            binding.loadingIcon.load(R.drawable.ic_baseline_cloud_download_24)
        }
    }

}
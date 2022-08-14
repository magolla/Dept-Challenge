package com.facundocetraro.deptchallenge.ui.photoList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
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
            if(photo.localUri == null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.image_not_loaded_yet),
                    Toast.LENGTH_SHORT
                ).show()
            } else {

            }
        })
        binding.photoList.apply {
            adapter = photoAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        lifecycle.coroutineScope.launch {
            photoListViewModel.getPhotosFlow(args.photoDate).collect { photoList ->
                photoAdapter.submitList(photoList)
            }
        }
    }

}
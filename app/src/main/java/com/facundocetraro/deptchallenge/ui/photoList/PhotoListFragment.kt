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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.facundocetraro.deptchallenge.R
import com.facundocetraro.deptchallenge.databinding.FragmentPhotoListBinding
import com.facundocetraro.deptchallenge.util.ConnectivityUtil
import com.facundocetraro.deptchallenge.viewModel.PhotoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotoListFragment : Fragment() {

    private var _binding: FragmentPhotoListBinding? = null
    private val binding get() = _binding!!

    private val photoListViewModel: PhotoListViewModel by viewModels()

    private val args: PhotoListFragmentArgs by navArgs()

    private var photoAdapter: PhotoListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        fetchPhotoList()
        binding.photoToolbar.title = args.photoDate
        binding.tryAgainButton.setOnClickListener {
            fetchPhotoList()
        }
    }

    private fun fetchPhotoList() {
        photoListViewModel.fetchImagesFromDate(args.photoDate)
    }

    private fun initRecyclerView() {
        if (photoAdapter == null) {
            photoAdapter = PhotoListAdapter(
                PhotoListAdapter.OnClickListener { photo ->
                    if (photo.localUri == null) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.image_not_loaded_yet),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val result =
                            PhotoListFragmentDirections.actionPhotoListFragmentToPhotoScreenFragment(
                                photo.identifier
                            )
                        findNavController().navigate(result)
                    }
                }
            )
        }
        binding.photoList.apply {
            adapter = photoAdapter
            val spanCount =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2
            layoutManager = GridLayoutManager(requireContext(), spanCount)
        }
        lifecycleScope.launchWhenStarted {
            photoListViewModel.getPhotosFlow(args.photoDate).collect { photoList ->
                photoAdapter?.submitList(photoList)
                shouldChangeLoadedIcon(photoList.isNotEmpty() && photoList.all { it.localUri != null })
                if (photoList.isEmpty()) {
                    checkInternet()
                } else {
                    binding.photoList.visibility = View.VISIBLE
                    binding.errorContainer.visibility = View.GONE
                }
            }
        }
    }

    private fun checkInternet() {
        if (!ConnectivityUtil.hasInternetConnection(requireContext().applicationContext)) {
            binding.photoList.visibility = View.GONE
            binding.errorContainer.visibility = View.VISIBLE
            binding.errorMessage.text = getString(R.string.there_is_not_internet)
            binding.errorImage.load(R.drawable.warning_error)
        }
    }

    private fun shouldChangeLoadedIcon(allImagesDownloaded: Boolean) {
        if (allImagesDownloaded) {
            binding.loadingIcon.visibility = View.VISIBLE
            binding.progress.visibility = View.GONE
            binding.loadingIcon.setOnClickListener {
                val action =
                    PhotoListFragmentDirections.actionPhotoListFragmentToPlayerScreenFragment(args.photoDate)
                findNavController().navigate(action)
            }
        } else {
            binding.loadingIcon.visibility = View.GONE
            binding.progress.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.facundocetraro.deptchallenge.ui.photoScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import com.facundocetraro.deptchallenge.databinding.FragmentPhotoScreenBinding
import com.facundocetraro.deptchallenge.viewModel.PhotoScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class PhotoScreenFragment : Fragment() {
    private lateinit var binding: FragmentPhotoScreenBinding

    private val photoScreenViewModel: PhotoScreenViewModel by viewModels()

    private val args: PhotoScreenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoScreenViewModel.getPhotoById(args.photoId)
        photoScreenViewModel.photoLiveData.observe(viewLifecycleOwner) { photo ->
            lifecycle.coroutineScope.launch {
                binding.photoView.load(photo.localUri)
            }
        }
    }

}
package com.facundocetraro.deptchallenge.ui.playerScreen

import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import com.facundocetraro.deptchallenge.databinding.FragmentPlayerScreenBinding
import com.facundocetraro.deptchallenge.viewModel.PlayerScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayerScreenFragment : Fragment() {

    private lateinit var binding: FragmentPlayerScreenBinding

    private val playerScreenViewModel: PlayerScreenViewModel by viewModels()

    private val args: PlayerScreenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.coroutineScope.launch {
            playerScreenViewModel.getPhotosFlow(args.photoDate).collect { photoList ->
                val ad = AnimationDrawable()
                photoList.forEach { photo ->
                    val frame = Drawable.createFromPath(photo.localUri)
                    ad.addFrame(frame!!, 1000 / 24)
                }
                binding.animatedImage.background = ad
                ad.start()
            }
        }
    }
}

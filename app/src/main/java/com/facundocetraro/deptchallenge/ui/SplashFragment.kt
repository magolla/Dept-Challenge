package com.facundocetraro.deptchallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.facundocetraro.deptchallenge.R
import com.facundocetraro.deptchallenge.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    companion object {
        private const val SPLASH_DURATION: Long = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            startAfterDelay()
        }
    }

    private suspend fun startAfterDelay() {
        delay(TimeUnit.SECONDS.toMillis(SPLASH_DURATION))
        withContext(Dispatchers.Main) {
            findNavController().navigate(R.id.action_splashFragment_to_dateListFragment)
        }
    }

}
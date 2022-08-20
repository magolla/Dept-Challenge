package com.facundocetraro.deptchallenge.ui.photoMetadata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.facundocetraro.deptchallenge.R
import com.facundocetraro.deptchallenge.databinding.FragmentPhotoMetadataDialogBinding

class PhotoMetadataDialogFragment : DialogFragment() {

    private var _binding: FragmentPhotoMetadataDialogBinding? = null
    private val binding get() = _binding!!

    private val args: PhotoMetadataDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoMetadataDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.photoMetadata.apply {
            binding.imageName.text = getString(R.string.photo_name, image)
            binding.imageDescription.text = getString(R.string.photo_description, caption)
            binding.imageDate.text = getString(R.string.photo_date, date)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

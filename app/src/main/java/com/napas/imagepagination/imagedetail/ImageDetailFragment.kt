package com.napas.imagepagination.imagedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.napas.imagepagination.R
import com.napas.imagepagination.base.BaseFragment
import com.napas.imagepagination.data.model.ImageFilterType
import com.napas.imagepagination.databinding.FragmentImageDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentImageDetailBinding
    private val viewModel by viewModels<ImageDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mbtgFilters.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val filterType = when (checkedId) {
                    R.id.button_blur -> ImageFilterType.BLUR
                    R.id.button_grayscale -> ImageFilterType.GRAYSCALE
                    else -> ImageFilterType.NORMAL
                }
                viewModel.sendEvent(ImageDetailEvent.SelectFilter(filterType))
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                with(it.image) {
                    binding.ivPhoto.load(imageUrl + it.imageFilterType.query)
                    binding.tvAuthor.text = author
                    binding.tvWidth.text = width.toString()
                    binding.tvHeight.text = height.toString()
                }
            }
        }
    }
}
package com.napas.imagepagination.imagelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.napas.imagepagination.R
import com.napas.imagepagination.base.BaseFragment
import com.napas.imagepagination.databinding.FragmentImageListBinding
import com.napas.imagepagination.imagelist.adapter.ImageListAdapter
import com.napas.imagepagination.imagelist.adapter.ImageLoadStateAdapter
import com.napas.imagepagination.model.AlertEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageListFragment : BaseFragment() {

    private lateinit var binding: FragmentImageListBinding
    private val viewModel by viewModels<ImageListViewModel>()
    private val imageLoadStateAdapter by lazy { ImageLoadStateAdapter() }
    private val imageListAdapter by lazy {
        ImageListAdapter {
            viewModel.sendEvent(ImageListEvent.SelectImage(it))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvImages.adapter = imageListAdapter.withLoadStateFooter(imageLoadStateAdapter)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                imageListAdapter.submitData(it.images)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            imageListAdapter.loadStateFlow.collectLatest {
                binding.progress.isVisible = it.refresh is LoadState.Loading
                if (it.refresh is LoadState.Error || it.append is LoadState.Error) {
                    showAlert(AlertEvent(messageRes = R.string.error_generic))
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effect.collect {
                when (it) {
                    is ImageListEffect.OpenImageDetail -> findNavController().navigate(
                        ImageListFragmentDirections.imageListFragmentToImageDetailFragment(it.image)
                    )

                    is ImageListEffect.ShowError -> showAlert(it.alertEvent)
                }
            }
        }
    }
}
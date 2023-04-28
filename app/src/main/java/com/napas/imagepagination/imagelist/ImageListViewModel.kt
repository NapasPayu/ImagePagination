package com.napas.imagepagination.imagelist

import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.napas.imagepagination.base.BaseViewModel
import com.napas.imagepagination.data.ImageRepository
import com.napas.imagepagination.data.model.Image
import com.napas.imagepagination.model.AlertEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ImageListState(
    val images: PagingData<Image> = PagingData.empty(),
    val isLoading: Boolean = false
)

sealed class ImageListEvent {
    data class SelectImage(val image: Image) : ImageListEvent()
}

sealed class ImageListEffect {
    data class OpenImageDetail(val image: Image) : ImageListEffect()
    data class ShowError(val alertEvent: AlertEvent) : ImageListEffect()
}

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : BaseViewModel<ImageListState, ImageListEvent, ImageListEffect>(ImageListState(isLoading = true)) {

    init {
        fetchImages()
    }

    override fun handleEvent(event: ImageListEvent) {
        when (event) {
            is ImageListEvent.SelectImage -> setEffect(ImageListEffect.OpenImageDetail(event.image))
        }
    }

    private fun fetchImages() {
        try {
            viewModelScope.launch {
                imageRepository.getImages()
                    .cachedIn(viewModelScope)
                    .collectLatest {
                        setState {
                            copy(images = it, isLoading = false)
                        }
                    }
            }
        } catch (exception: Exception) {
            setEffect(
                ImageListEffect.ShowError(AlertEvent(message = exception.message))
            )
        }
    }
}
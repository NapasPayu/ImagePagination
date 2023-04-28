package com.napas.imagepagination.imagedetail

import androidx.lifecycle.SavedStateHandle
import com.napas.imagepagination.base.BaseViewModel
import com.napas.imagepagination.data.model.Image
import com.napas.imagepagination.data.model.ImageFilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class ImageDetailState(
    val image: Image,
    val imageFilterType: ImageFilterType
)

sealed class ImageDetailEvent {
    data class SelectFilter(val imageFilterType: ImageFilterType) : ImageDetailEvent()
}

object ImageDetailEffect

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ImageDetailState, ImageDetailEvent, ImageDetailEffect>(
    ImageDetailState(
        image = ImageDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).image,
        imageFilterType = ImageFilterType.NORMAL
    )
) {

    override fun handleEvent(event: ImageDetailEvent) {
        when (event) {
            is ImageDetailEvent.SelectFilter -> setState {
                copy(imageFilterType = event.imageFilterType)
            }
        }
    }
}
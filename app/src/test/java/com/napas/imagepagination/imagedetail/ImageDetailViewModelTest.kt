package com.napas.imagepagination.imagedetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.napas.imagepagination.data.model.Image
import com.napas.imagepagination.data.model.ImageFilterType
import com.napas.imagepagination.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ImageDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeImage = Image(
        id = "0",
        author = "Alejandro Escamilla",
        width = 5000,
        height = 3333,
        imageUrl = "https://picsum.photos/id/0/5000/3333"
    )
    private val fakeSavedStateHandle = SavedStateHandle().apply {
        set("image", fakeImage)
    }
    private lateinit var viewModel: ImageDetailViewModel

    @Before
    fun setup() {
        viewModel = ImageDetailViewModel(fakeSavedStateHandle)
    }

    @Test
    fun `initial image filter is normal`() = runTest {
        val initialState = ImageDetailState(
            image = fakeImage,
            imageFilterType = ImageFilterType.NORMAL
        )

        viewModel.state.test {
            assertEquals(initialState, awaitItem())
        }
    }

    @Test
    fun `change image filters`() = runTest {
        val initialState = ImageDetailState(
            image = fakeImage,
            imageFilterType = ImageFilterType.NORMAL
        )

        viewModel.state.test {
            assertEquals(initialState, awaitItem())

            viewModel.sendEvent(ImageDetailEvent.SelectFilter(ImageFilterType.BLUR))
            assertEquals(initialState.copy(imageFilterType = ImageFilterType.BLUR), awaitItem())

            viewModel.sendEvent(ImageDetailEvent.SelectFilter(ImageFilterType.GRAYSCALE))
            assertEquals(initialState.copy(imageFilterType = ImageFilterType.GRAYSCALE), awaitItem())
        }
    }
}
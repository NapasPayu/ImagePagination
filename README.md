# ImagePagination
This app fetches images from https://picsum.photos/ with pagination and also presents images with a few filters
 
## Summary
- Overall architecture is MVI + Clean Architecture
- Use many of the [Android Jetpack](https://developer.android.com/jetpack)
  - [View binding](https://developer.android.com/topic/libraries/view-binding): Generate a binding class for each XML layout file 
  - [Kotlin flows](https://developer.android.com/kotlin/flow): Notify views when underlying datasource changes
  - [Navigation](https://developer.android.com/guide/navigation/): Handle everything needed for in-app navigation
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): Manage UI-related data in a lifecycle-conscious way
  - [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview): Load and display pages of data from a larger dataset from local storage or over network
- Use [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection
- Use [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) for managing background threads with simplified code and reducing needs for callbacks
- Use [Coil](https://github.com/coil-kt/coil) for image loading
- Add a sample unit test on [ImageDetailViewModelTest.kt](https://github.com/NapasPayu/ImagePagination/blob/main/app/src/test/java/com/napas/imagepagination/imagedetail/ImageDetailViewModelTest.kt)

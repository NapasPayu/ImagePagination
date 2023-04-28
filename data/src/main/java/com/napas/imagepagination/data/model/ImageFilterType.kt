package com.napas.imagepagination.data.model

enum class ImageFilterType(val query: String) {
    NORMAL(""),
    BLUR("?blur=10"),
    GRAYSCALE("?grayscale")
}
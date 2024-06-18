package com.example.libraryapp.data

data class GoogleBooksResponse(
    val items: List<BookItem>
)

data class BookItem(
    val volumeInfo: VolumeInfo,
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val description: String,
    val infoLink: String,
    val pageCount: Int,
    val imageLinks: ImageLinks?,
    val categories: List<String>?
)

data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?
)
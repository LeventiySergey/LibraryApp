package com.example.libraryapp.data

class Books {
    var id: Int = 0
    var title: String = ""
    var authors: String = ""

    constructor(title:String, authors: String){
        this.title=title
        this.authors=authors
    }

}
package com.example.testapp.Class

class Note {
    var Title: String? = null
        private set

    constructor() {
        //empty const needed
    }

    constructor(title: String?) {
        Title = title
    }
}
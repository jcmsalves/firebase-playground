package com.jcmsalves.firebaseplayground.realtimedatabase

data class Game(
    val name: String = "",
    val releaseYear: Int = 0,
    val platform: String = "",
    val score: Int = 0,
    var uuid: String = ""
)
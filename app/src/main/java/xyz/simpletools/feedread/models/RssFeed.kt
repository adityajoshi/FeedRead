package xyz.simpletools.feedread.models

data class RssFeed(
    val name: String,
    val url: String,
    val description: String = ""
) 
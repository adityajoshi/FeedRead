package xyz.simpletools.feedread.models

import java.util.Date

data class RssArticle(
    val title: String,
    val description: String,
    val link: String,
    val pubDate: Date? = null,
    val imageUrl: String? = null,
    val content: String = ""
) 
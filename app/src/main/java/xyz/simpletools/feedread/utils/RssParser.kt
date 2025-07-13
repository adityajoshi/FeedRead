package xyz.simpletools.feedread.utils

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import xyz.simpletools.feedread.models.RssArticle
import java.io.StringReader
import java.text.SimpleDateFormat
import java.util.*

class RssParser {
    
    companion object {
        private const val TAG = "RssParser"
        private val dateFormats = listOf(
            SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US),
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US),
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        )
    }
    
    fun parseRssFeed(xmlString: String): List<RssArticle> {
        val articles = mutableListOf<RssArticle>()
        
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            parser.setInput(StringReader(xmlString))
            
            var eventType = parser.eventType
            var currentArticle: MutableMap<String, String>? = null
            var isItem = false
            var currentTag = ""
            
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        currentTag = parser.name
                        when (currentTag) {
                            "item" -> {
                                isItem = true
                                currentArticle = mutableMapOf()
                            }
                            "title", "description", "link", "pubDate", "content:encoded", "media:content" -> {
                                if (isItem) {
                                    // Start collecting text content
                                }
                            }
                        }
                    }
                    XmlPullParser.TEXT -> {
                        if (isItem && currentArticle != null) {
                            val text = parser.text.trim()
                            if (text.isNotEmpty()) {
                                when (currentTag) {
                                    "title" -> currentArticle["title"] = text
                                    "description" -> currentArticle["description"] = text
                                    "link" -> currentArticle["link"] = text
                                    "pubDate" -> currentArticle["pubDate"] = text
                                    "content:encoded" -> currentArticle["content"] = text
                                }
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        when (parser.name) {
                            "item" -> {
                                isItem = false
                                currentArticle?.let { articleMap ->
                                    val article = RssArticle(
                                        title = articleMap["title"] ?: "",
                                        description = articleMap["description"] ?: "",
                                        link = articleMap["link"] ?: "",
                                        pubDate = parseDate(articleMap["pubDate"]),
                                        content = articleMap["content"] ?: ""
                                    )
                                    articles.add(article)
                                }
                                currentArticle = null
                            }
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing RSS feed", e)
        }
        
        return articles
    }
    
    private fun parseDate(dateString: String?): Date? {
        if (dateString.isNullOrEmpty()) return null
        
        for (format in dateFormats) {
            try {
                return format.parse(dateString)
            } catch (e: Exception) {
                // Try next format
            }
        }
        return null
    }
} 
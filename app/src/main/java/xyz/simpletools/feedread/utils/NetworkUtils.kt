package xyz.simpletools.feedread.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class NetworkUtils {
    
    companion object {
        private const val TAG = "NetworkUtils"
        private val client = OkHttpClient()
    }
    
    suspend fun fetchRssFeed(url: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url(url)
                    .addHeader("User-Agent", "FeedRead/1.0")
                    .build()
                
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        response.body?.string()
                    } else {
                        Log.e(TAG, "Failed to fetch RSS feed: ${response.code}")
                        null
                    }
                }
            } catch (e: IOException) {
                Log.e(TAG, "Network error fetching RSS feed", e)
                null
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error fetching RSS feed", e)
                null
            }
        }
    }
} 
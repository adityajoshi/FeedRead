package xyz.simpletools.feedread

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import xyz.simpletools.feedread.adapters.ArticleAdapter
import xyz.simpletools.feedread.databinding.FragmentSecondBinding
import xyz.simpletools.feedread.models.RssArticle
import xyz.simpletools.feedread.utils.NetworkUtils
import xyz.simpletools.feedread.utils.RssParser
import android.content.Intent
import android.net.Uri

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val args: SecondFragmentArgs by navArgs()
    
    private val networkUtils = NetworkUtils()
    private val rssParser = RssParser()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the title to the feed name
        requireActivity().title = args.feedName

        setupRecyclerView()
        loadArticles()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewArticles.apply {
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun loadArticles() {
        binding.progressBar.visibility = View.VISIBLE
        
        lifecycleScope.launch {
            try {
                val xmlData = networkUtils.fetchRssFeed(args.feedUrl)
                if (xmlData != null) {
                    val articles = rssParser.parseRssFeed(xmlData)
                    displayArticles(articles)
                } else {
                    binding.tvError.text = "Failed to load articles"
                    binding.tvError.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                binding.tvError.text = "Error: ${e.message}"
                binding.tvError.visibility = View.VISIBLE
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun displayArticles(articles: List<RssArticle>) {
        binding.recyclerViewArticles.adapter = ArticleAdapter(articles) { article ->
            // Open article in default web browser
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.link))
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
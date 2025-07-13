package xyz.simpletools.feedread

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.simpletools.feedread.adapters.FeedAdapter
import xyz.simpletools.feedread.databinding.FragmentFirstBinding
import xyz.simpletools.feedread.models.RssFeed

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val feeds = listOf(
        RssFeed("BBC News", "http://feeds.bbci.co.uk/news/rss.xml", "Latest news from BBC"),
        RssFeed("TechCrunch", "https://techcrunch.com/feed/", "Technology news and startup information"),
        RssFeed("The Verge", "https://www.theverge.com/rss/index.xml", "Technology, science, art, and culture"),
        RssFeed("Ars Technica", "https://feeds.arstechnica.com/arstechnica/index", "Technology news and analysis"),
        RssFeed("Engadget", "https://www.engadget.com/rss.xml", "Consumer electronics and technology news")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewFeeds.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = FeedAdapter(feeds) { feed ->
                // Navigate to SecondFragment with feed data
                val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(feed.url, feed.name)
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
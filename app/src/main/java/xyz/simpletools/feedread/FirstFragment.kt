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

    private val feeds = mutableListOf<RssFeed>()
    private lateinit var feedAdapter: FeedAdapter

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
        binding.buttonAddFeed.setOnClickListener {
            val url = binding.editTextFeedUrl.text.toString().trim()
            if (url.isNotEmpty()) {
                val feed = RssFeed(url, url) // Using URL as name and url, can be improved
                feeds.add(feed)
                feedAdapter.notifyItemInserted(feeds.size - 1)
                binding.editTextFeedUrl.text?.clear()
            }
        }
    }

    private fun setupRecyclerView() {
        feedAdapter = FeedAdapter(feeds) { feed ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(feed.url, feed.name)
            findNavController().navigate(action)
        }
        binding.recyclerViewFeeds.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = feedAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

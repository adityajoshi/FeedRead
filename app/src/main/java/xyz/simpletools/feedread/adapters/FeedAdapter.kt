package xyz.simpletools.feedread.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import xyz.simpletools.feedread.databinding.ItemFeedBinding
import xyz.simpletools.feedread.models.RssFeed

class FeedAdapter(
    private val feeds: List<RssFeed>,
    private val onFeedClick: (RssFeed) -> Unit
) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    class FeedViewHolder(private val binding: ItemFeedBinding) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(feed: RssFeed, onFeedClick: (RssFeed) -> Unit) {
            binding.tvFeedName.text = feed.name
            binding.tvFeedDescription.text = feed.description
            
            itemView.setOnClickListener {
                onFeedClick(feed)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = ItemFeedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(feeds[position], onFeedClick)
    }

    override fun getItemCount(): Int = feeds.size
} 
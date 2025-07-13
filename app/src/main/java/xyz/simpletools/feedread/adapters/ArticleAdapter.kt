package xyz.simpletools.feedread.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import xyz.simpletools.feedread.databinding.ItemArticleBinding
import xyz.simpletools.feedread.models.RssArticle
import java.text.SimpleDateFormat
import java.util.*

class ArticleAdapter(
    private val articles: List<RssArticle>,
    private val onArticleClick: (RssArticle) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    class ArticleViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(article: RssArticle, dateFormat: SimpleDateFormat, onArticleClick: (RssArticle) -> Unit) {
            binding.tvArticleTitle.text = article.title
            binding.tvArticleDescription.text = article.description
            
            article.pubDate?.let { date ->
                binding.tvArticleDate.text = dateFormat.format(date)
                binding.tvArticleDate.visibility = android.view.View.VISIBLE
            } ?: run {
                binding.tvArticleDate.visibility = android.view.View.GONE
            }
            
            itemView.setOnClickListener {
                onArticleClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position], dateFormat, onArticleClick)
    }

    override fun getItemCount(): Int = articles.size
} 
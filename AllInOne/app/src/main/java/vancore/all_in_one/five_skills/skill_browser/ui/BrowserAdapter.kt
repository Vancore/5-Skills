package vancore.all_in_one.five_skills.skill_browser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.all_in_one.databinding.ListitemSkillBrowserBinding
import vancore.all_in_one.five_skills.skill_browser.data.BrowserItem

class BrowserAdapter(
    private val browserItems: List<BrowserItem>,
    private val browserItemClickListener: BrowserItemClickListener
) : RecyclerView.Adapter<BrowserItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowserItemViewHolder {
        val binding = ListitemSkillBrowserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BrowserItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BrowserItemViewHolder, position: Int) {
        with(holder){
            val browserItem = browserItems[position]
            with(browserItem) {
                binding.tvBrowserTitle.text = skillTitle
                binding.tvBrowserDescription.text = skillDescription
            }

            itemView.setOnClickListener {
                Toast.makeText(holder.itemView.context, "click on position: ${position + 1}, ${browserItem.skillTitle}", Toast.LENGTH_SHORT).show()
                browserItemClickListener.onBrowserItemClicked(browserItem)
            }

        }
    }

    override fun getItemCount(): Int = browserItems.size
}

class BrowserItemViewHolder (val binding: ListitemSkillBrowserBinding): RecyclerView.ViewHolder(binding.root)

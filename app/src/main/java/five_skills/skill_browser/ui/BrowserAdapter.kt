package five_skills.skill_browser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.all_in_one.databinding.ListitemSkillBinding
import five_skills.shared.view.SkillViewHolder
import shared.models.SkillItem

class BrowserAdapter(
    private val skillItems: List<SkillItem>,
    private val browserItemClickListener: BrowserItemClickListener
) : RecyclerView.Adapter<SkillViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val binding = ListitemSkillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SkillViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        with(holder){
            val browserItem = skillItems[position]
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

    override fun getItemCount(): Int = skillItems.size
}

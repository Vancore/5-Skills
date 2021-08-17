package vancore.all_in_one.five_skills.skill_profile.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.all_in_one.databinding.ListitemSkillBinding
import vancore.all_in_one.shared.models.SkillItem
import vancore.all_in_one.shared.view.SkillViewHolder

class SkillsAdapter(
    private val skillItems: List<SkillItem>,
    private val skillItemClickListener: SkillItemClickListener
) : RecyclerView.Adapter<SkillViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val binding = ListitemSkillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SkillViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        with(holder){
            val skillItem = skillItems[position]
            with(skillItem) {
                binding.tvBrowserTitle.text = skillTitle
                binding.tvBrowserDescription.text = skillDescription
            }

            itemView.setOnClickListener {
                Toast.makeText(holder.itemView.context, "click on position: ${position + 1}, ${skillItem.skillTitle}", Toast.LENGTH_SHORT).show()
                skillItemClickListener.onSkillItemClicked(skillItem)
            }

        }
    }

    override fun getItemCount(): Int = 5
}
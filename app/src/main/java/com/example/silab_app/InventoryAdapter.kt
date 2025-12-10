import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.silab_app.InventoryItem
import com.example.silab_app.R
import com.example.silab_app.databinding.ItemInventoryBinding
class InventoryAdapter(
    private val items: List<InventoryItem>,
    private val onClick: (InventoryItem) -> Unit = {}
) : RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {

    inner class InventoryViewHolder(val binding: ItemInventoryBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: InventoryItem) = with(binding) {

            txtName.text = item.name
            txtLocation.text = item.location
            imgItem.setImageResource(item.imageRes)

            // Status badge
            txtStatus.text = item.status
            if (item.status == "Available") {
                txtStatus.setBackgroundResource(R.drawable.bg_status_available)
                txtStatus.setTextColor(ContextCompat.getColor(root.context, R.color.available))
            } else {
                txtStatus.setBackgroundResource(R.drawable.bg_status_unavailable)
                txtStatus.setTextColor(ContextCompat.getColor(root.context, R.color.not_available))
            }

            root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val binding = ItemInventoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return InventoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}


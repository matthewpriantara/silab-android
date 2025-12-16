import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.silab_app.models.InventoryItem
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

            if (!item.imgRes.isNullOrEmpty()) {
                imgItem.load(item.imgRes)
            } else {
                imgItem.setImageResource(R.drawable.default_img)
            }

            // Status badge
            txtStatus.text = item.status
            if (item.status == "Available") {
                txtStatus.setBackgroundResource(R.drawable.bg_status_available)
                txtStatus.setTextColor(ContextCompat.getColor(root.context, R.color.available))
            } else if (item.status == "Not Available"){
                txtStatus.setBackgroundResource(R.drawable.bg_status_unavailable)
                txtStatus.setTextColor(ContextCompat.getColor(root.context, R.color.not_available))
            } else if (item.status == "process") {
                txtStatus.setBackgroundResource(R.drawable.bg_status_process)
                txtStatus.setTextColor(ContextCompat.getColor(root.context, R.color.process))
            } else if (item.status == "canceled") {
                txtStatus.setBackgroundResource(R.drawable.bg_status_canceled)
                txtStatus.setTextColor(ContextCompat.getColor(root.context, R.color.canceled))
            } else if (item.status == "waiting_to_be_return") {
                txtStatus.setBackgroundResource(R.drawable.bg_status_waiting)
                txtStatus.setTextColor(ContextCompat.getColor(root.context, R.color.waiting_to_be_return))
            } else if (item.status == "approve") {
                txtStatus.setBackgroundResource(R.drawable.bg_status_approve)
                txtStatus.setTextColor(ContextCompat.getColor(root.context, R.color.approve))
            } else if (item.status == "rejected") {
                txtStatus.setBackgroundResource(R.drawable.bg_status_rejected)
                txtStatus.setTextColor(ContextCompat.getColor(root.context, R.color.rejected))
            } else {
                txtStatus.setBackgroundResource(R.drawable.bg_status_done)
                txtStatus.setTextColor(ContextCompat.getColor(root.context, R.color.done))
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


package digitalent.rizqifathurrohman.tugaspraktek

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import digitalent.rizqifathurrohman.tugaspraktek.model.User

class ItemListAct(
    private var userList: List<User>,
    private val onItemLongClickListener: OnItemLongClickListener
) : RecyclerView.Adapter<ItemListAct.ItemViewHolder>() {
    interface OnItemLongClickListener {
        fun onItemLongClick(user: User?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_item_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val user = userList[position]
        holder.tvName.text = user.name
        holder.tvNim.text = user.npm

        holder.itemView.setOnLongClickListener { v: View? ->
            Log.d("ItemListAct", "Item long clicked: " + user.name)
            onItemLongClickListener?.onItemLongClick(user)
            true
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateUserList(userList: List<User>) {
        this.userList = userList
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tvName)
        var tvNim: TextView = itemView.findViewById(R.id.tvNpm)
    }
}
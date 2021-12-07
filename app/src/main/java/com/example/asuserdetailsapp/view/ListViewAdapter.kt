package com.example.asuserdetailsapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.asuserdetailsapp.R
import com.example.asuserdetailsapp.database.User
import com.google.android.material.textview.MaterialTextView

class ListViewAdapter(
    private val clickListener: (position: Int) -> Unit
) : RecyclerView.Adapter<ListViewAdapter.ViewHolders>() {
    private var itemList: ArrayList<User>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders {
        val layoutView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item, null
        )
        return ViewHolders(layoutView)
    }

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        itemList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }

    fun addData(listItems: ArrayList<User>) {
        itemList = listItems
        notifyDataSetChanged()
    }

    inner class ViewHolders(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image)
        var name: MaterialTextView = itemView.findViewById(R.id.name)
        var mobileNumber: MaterialTextView = itemView.findViewById(R.id.mobileNumber)
        var cardView: CardView = itemView.findViewById(R.id.card_view)
        fun bind(item: User) {
            Glide.with(itemView.context)
                .load(item.image)
                .placeholder(R.drawable.ic_default_dp)
                .centerCrop()
                .into(image)
            name.text =
                (item.firstName + " " + item.lastName)
            mobileNumber.text = item.mobile
            cardView.setOnClickListener {
                clickListener(adapterPosition)
            }
        }
    }

}
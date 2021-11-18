package com.deloitte.wtest.utils

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deloitte.wtest.database.postalcode.PostalCode

class RecyclerAdapter() : RecyclerView.Adapter<ViewHolder>() {

    var postalCodeList: List<PostalCode?> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(postalCodeList?.get(position))
    }

    override fun getItemCount(): Int {
        return postalCodeList.size
    }
}
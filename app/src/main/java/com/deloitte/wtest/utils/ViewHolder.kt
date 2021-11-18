package com.deloitte.wtest.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deloitte.wtest.database.postalcode.PostalCode
import com.deloitte.wtest.databinding.RecyclerItemBinding

class ViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RecyclerItemBinding.inflate(inflater, parent, false)
            return ViewHolder(binding)
        }
    }

    fun onBind(postalCode: PostalCode?) {
        binding.postalCode = postalCode
       // binding.executePendingBindings()
    }

}
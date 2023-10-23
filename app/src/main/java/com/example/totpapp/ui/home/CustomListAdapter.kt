package com.example.totpapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.totpapp.R
import java.util.ArrayList

class CustomListAdapter(private val itemList: ArrayList<HomeViewModel>) : RecyclerView.Adapter<CustomListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val org: TextView = itemView.findViewById(R.id.org)
        val user: TextView = itemView.findViewById(R.id.userTag)
        val otp: TextView = itemView.findViewById(R.id.otp)
        val progress: ProgressBar = itemView.findViewById(R.id.progressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.org.text = item.getOrgValue()
        holder.user.text = item.getUserValue()
        holder.otp.text = item.getOtpValue()
        holder.progress.progress = item.getProgressValue()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}

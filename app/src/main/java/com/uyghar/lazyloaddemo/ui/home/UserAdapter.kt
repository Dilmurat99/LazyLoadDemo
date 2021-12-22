package com.uyghar.lazyloaddemo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uyghar.lazyloaddemo.databinding.UserItemBinding
import com.uyghar.lazyloaddemo.model.User
import com.uyghar.lazyloaddemo.model.UserDB

class UserAdapter(val users: ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    inner class UserViewHolder(val userItemBinding: UserItemBinding):RecyclerView.ViewHolder(userItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val userItemBinding = UserItemBinding.inflate(LayoutInflater.from(parent.context))
        return UserViewHolder(userItemBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users.get(position)
        holder.userItemBinding.textName.setText(user?.first_name + " " + user?.last_name)
        holder.userItemBinding.textEmail.setText(user?.email)
        Picasso.get().load(user?.avatar).into(holder.userItemBinding.imageAvatar)
    }

    override fun getItemCount(): Int {
        return users.size
    }
}
package com.mita.gamebuddymobile

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mita.gamebuddymobile.api.UserDataClass


class UserAdapter(private val userList: ArrayList<UserDataClass>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.Name)
        val gender: TextView = itemView.findViewById(R.id.gender)
        val age: TextView = itemView.findViewById(R.id.age)
        val message: TextView = itemView.findViewById(R.id.message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.users_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.name.text = user.name
        holder.gender.text = user.gender
        holder.age.text = user.age.toString()

        holder.message.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name", user.name)
            intent.putExtra("UserIDReceiver", user.id)
            context.startActivity(intent)
        }
    }


    fun updateUserList(users: List<UserDataClass>) {
        userList.clear()
        userList.addAll(users)
        notifyDataSetChanged()
    }
}

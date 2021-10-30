package com.example.recyclerviewadddeleteupdate.Adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewadddeleteupdate.Model.UserData
import com.example.recyclerviewadddeleteupdate.R

class UserAdapter(val context: Context, val userList: ArrayList<UserData>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var mName: TextView
        var mNum: TextView
        var mMenus: ImageView

        init {
            mName = v.findViewById<TextView>(R.id.mTitle)
            mNum = v.findViewById<TextView>(R.id.mSubTitle)
            mMenus = v.findViewById(R.id.mMenus)
            mMenus.setOnClickListener {
                popupMenus(it)
            }
        }

        private fun popupMenus(v: View) {
            val position = userList[adapterPosition]
            val popupMenus = PopupMenu(context, v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when (it.itemId) {

                    R.id.editText -> {
                        val v = LayoutInflater.from(context).inflate(R.layout.add_item, null)
                        val name = v.findViewById<EditText>(R.id.UserName)
                        val number = v.findViewById<EditText>(R.id.UserNo)
                        AlertDialog.Builder(context)
                            .setView(v)
                            .setPositiveButton("Ok") { dialog, _ ->
                                position.userName = name.text.toString()
                                position.userMb = number.text.toString()
                                notifyDataSetChanged()
                                Toast.makeText(
                                    context,
                                    "User Information is Edited",
                                    Toast.LENGTH_SHORT
                                ).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }

                    R.id.deleteText -> {
                        AlertDialog.Builder(context)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_baseline_warning_24)
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes"){
                                dialog,_->
                                userList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(context, "Deleted this Information", Toast.LENGTH_SHORT)
                                    .show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    else -> true
                }

            }

            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_rv, parent, false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.mName.text = newList.userName
        holder.mNum.text = newList.userMb
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
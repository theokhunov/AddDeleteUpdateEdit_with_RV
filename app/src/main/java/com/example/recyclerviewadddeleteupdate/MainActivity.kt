package com.example.recyclerviewadddeleteupdate


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewadddeleteupdate.Adapter.UserAdapter
import com.example.recyclerviewadddeleteupdate.Model.UserData
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var addBtn: FloatingActionButton
    private lateinit var recycleV: RecyclerView
    private lateinit var userList: ArrayList<UserData>
    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userList = ArrayList()

        addBtn = findViewById(R.id.addBtn)
        recycleV = findViewById(R.id.my_rv)


        userAdapter = UserAdapter(this,userList)


        recycleV.layoutManager = LinearLayoutManager(this)
        recycleV.adapter = userAdapter

        addBtn.setOnClickListener {
            addInfo()
        }


    }

    private fun addInfo() {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_item,null)
        val userName = v.findViewById<EditText>(R.id.UserName)
        val userNo = v.findViewById<EditText>(R.id.UserNo)
        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)


        addDialog.setPositiveButton("Ok"){
                dialog,_->
            val names = userName.text.toString()
            val number = userNo.text.toString()
            userList.add(UserData("Name: $names","Mobile No. : $number"))
            userAdapter.notifyDataSetChanged()
            Toast.makeText(this,"Adding User Information Success",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()

        }


        addDialog.create()
        addDialog.show()

    }
}
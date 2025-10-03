package com.example.todo_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var profileIcon:ImageView
    private lateinit var addTask:FloatingActionButton
    private lateinit var auth:FirebaseAuth
    private lateinit var taskList:ArrayList<Task>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        auth=FirebaseAuth.getInstance()

//        val tasks = DummyDB.getAllData()
        taskList = ArrayList()
        recyclerView.adapter = TaskAdapter(taskList)

        profileIcon=findViewById(R.id.profileIcon)
        addTask=findViewById(R.id.add_task)

        fetchTasks()

        addTask.setOnClickListener(View.OnClickListener {
            val i = Intent(this,AddTaskActivity::class.java)
            startActivity(i)
            finish()
        })
        profileIcon.setOnClickListener(View.OnClickListener {
            val i = Intent(this,ProfileActivity::class.java)
            startActivity(i)
            finish()
        })
    }
    private fun fetchTasks(){
        val current=auth.currentUser;
        if(current!=null){
            val id=current.uid
            val ref = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Tasks")
            ref.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    taskList.clear()
                    for(taskSnapshot in snapshot.children){
                        val task=taskSnapshot.getValue(Task::class.java)
                        if(task != null){
                            taskList.add(task)
                        }
                    }
                    TaskAdapter(taskList).notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MainActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
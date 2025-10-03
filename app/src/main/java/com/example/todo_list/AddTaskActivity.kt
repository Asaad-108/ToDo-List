package com.example.todo_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddTaskActivity : AppCompatActivity() {
    private lateinit var nameInput:EditText
    private lateinit var descInput:EditText
    private lateinit var saveTaskBtn:Button
    private lateinit var backArrowBtn:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_task)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        nameInput=findViewById(R.id.taskNameInput)
        descInput=findViewById(R.id.taskDescInput)
        saveTaskBtn=findViewById(R.id.saveTaskBtn)
        backArrowBtn=findViewById(R.id.backArrowbtn)
        backArrowBtn.setOnClickListener(View.OnClickListener {
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
            finish()
        })
        saveTaskBtn.setOnClickListener(View.OnClickListener {
            saveTaskFirebase()
        })
    }
    private fun saveTaskFirebase(){
        val name:String=nameInput.text.toString()
        val desc:String=descInput.text.toString()
        if(name.isEmpty()||desc.isEmpty()){
            Toast.makeText(this,"Please filled all fields !",Toast.LENGTH_SHORT).show()
            return
        }
        val auth=FirebaseAuth.getInstance()
        val current=auth.currentUser
        if(current==null){
            Toast.makeText(this,"User not logged In !",Toast.LENGTH_SHORT).show()
            return
        }
        val id=current.uid
        val ref=FirebaseDatabase.getInstance().getReference("Users").child(id).child("Tasks")
        val taskId=ref.push().key!!
        val task=Task(name,desc)
        ref.child(taskId).setValue(task).addOnCompleteListener({ taskResult ->
            if(taskResult.isSuccessful){
                Toast.makeText(this,"Task added Successfully !",Toast.LENGTH_SHORT).show()
                nameInput.setText("")
                descInput.setText("")
            }
            else{
                Toast.makeText(this,"Failed add Task !",Toast.LENGTH_SHORT).show()
            }

        }).addOnFailureListener { e ->
            Toast.makeText(this,"Error ${e.message}!",Toast.LENGTH_SHORT).show()
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}
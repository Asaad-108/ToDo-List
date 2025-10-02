package com.example.todo_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var logoutBtn:Button
    private lateinit var editProfile:Button
    private lateinit var userName:TextView
    private lateinit var userEmail:TextView
    private lateinit var userPhone:TextView
    private lateinit var userBio:TextView
    private lateinit var backArrow:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        userName=findViewById(R.id.userName)
        userEmail=findViewById(R.id.userEmail)
        userPhone=findViewById(R.id.userPhone)
        userBio=findViewById(R.id.userBio)
        editProfile=findViewById(R.id.editProfileBtn)
        logoutBtn=findViewById(R.id.logoutBtn)
        backArrow=findViewById(R.id.backArrow)
        auth=FirebaseAuth.getInstance()
        val current:FirebaseUser?=auth.currentUser
        if(current==null){
            Toast.makeText(this,"Data isn't available !",Toast.LENGTH_SHORT).show()
        }
        else{
            val id = current.uid
            val ref = FirebaseDatabase.getInstance().getReference("Users").child(id)
            ref.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot){
                    if (!snapshot.exists()) {
                        Toast.makeText(this@ProfileActivity,"Data not found !",Toast.LENGTH_SHORT).show()
                        return
                    }
                    val user=snapshot.getValue(User::class.java)
                    if(user!=null){
                        Toast.makeText(this@ProfileActivity,"Data loaded Successfully !",Toast.LENGTH_SHORT).show()
                        userName.setText(user.userName)
                        userEmail.setText(user.userEmail)
                        userPhone.text="\uD83D\uDCDE ${user.userPhone}"
                        userBio.text="\uD83D\uDCA1 ${user.userBio}"
//                        userLocation.text="\uD83D\uDCCD ${user.userLocation}"
                    }
                }
                override fun onCancelled(error: DatabaseError){
                    Toast.makeText(this@ProfileActivity,"Database Error : ${error.message}",Toast.LENGTH_SHORT).show()
                }
            })
        }
        backArrow.setOnClickListener(View.OnClickListener {
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
            finish()
        })
        logoutBtn.setOnClickListener(View.OnClickListener {
            auth.signOut()
            val i =Intent(this,LoginActivity::class.java)
            startActivity(i)
            finish()
        })
    }
}
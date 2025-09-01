package com.example.todo_list

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var userName:EditText
    private lateinit var userEmail:EditText
    private lateinit var userPassword:EditText
    private lateinit var userPhone:EditText
    private lateinit var userBio:EditText
    private lateinit var userLoc:EditText
    private lateinit var signUpBtn:Button
    private lateinit var gotoLogin:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        auth=FirebaseAuth.getInstance();
        userName=findViewById(R.id.username)
        userEmail=findViewById(R.id.useremail)
        FirebaseApp.initializeApp(this)
        userPhone=findViewById(R.id.userphone)
        userBio=findViewById(R.id.userbio)
        userPassword=findViewById(R.id.userpassword)
        signUpBtn=findViewById(R.id.signupbtn)
        gotoLogin=findViewById(R.id.loginText)

        signUpBtn.setOnClickListener {
            val name=userName.text.toString().trim()
            val email=userEmail.text.toString().trim()
            val phone=userPhone.text.toString().trim()
            val bio=userBio.text.toString().trim()
            val password=userPassword.text.toString().trim()

            if(name.isEmpty()||email.isEmpty()||password.isEmpty()||phone.isEmpty()||bio.isEmpty()){
                Toast.makeText(this,"Please enter the required credential !",Toast.LENGTH_SHORT).show()
            }
            else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        val id=auth.currentUser?.uid?:""
                        val ref=FirebaseDatabase.getInstance().getReference("Users").child(id)
                        val u = User(name,email,phone,bio)
                        ref.setValue(u).addOnCompleteListener(OnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this,"Saved Successfully !",Toast.LENGTH_SHORT).show()
                                val i=Intent(this,MainActivity::class.java)
                                startActivity(i)
                                finish()
                            }
                            else{
                                val err:String=task.exception.toString()
                                Toast.makeText(this,"$err !",Toast.LENGTH_SHORT).show()
                            }
                        })
                        Toast.makeText(this,"Account created successfully !",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val err = task.exception?.message ?: "Error creating account"
                        Toast.makeText(this, "$err !", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        gotoLogin.setOnClickListener {
                val i = Intent(this, LoginActivity::class.java);
                startActivity(i);
                finish();
        }
    }
}
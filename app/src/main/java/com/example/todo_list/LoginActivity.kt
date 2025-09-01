package com.example.todo_list

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var goto_signup:TextView
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var login:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        auth=FirebaseAuth.getInstance()
        goto_signup =findViewById(R.id.signupText);
        email=findViewById(R.id.email)
        password=findViewById(R.id.password)
        login=findViewById(R.id.loginbtn)

        login.setOnClickListener(View.OnClickListener {
            val userEmail:String=email.text.toString()
            val userPassword:String=password.text.toString()
            auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(
                OnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Logged In Successfully !",Toast.LENGTH_SHORT).show()
                        val i = Intent(this,MainActivity::class.java)
                        startActivity(i)
                        finish()
                    }
                    else{
                        val err:String=task.exception.toString()
                        Toast.makeText(this,"$err !",Toast.LENGTH_SHORT).show()
                    }

                })
        })

        goto_signup.setOnClickListener {
                val i = Intent(this, SignupActivity::class.java);
                startActivity(i);
                finish();
        }
    }
}
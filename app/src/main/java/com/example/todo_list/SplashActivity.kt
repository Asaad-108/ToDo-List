package com.example.todo_list

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth;
    private lateinit var ref:DatabaseReference;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Users");

        val current:FirebaseUser?=auth.currentUser
        if(current!=null){
            val id=current.uid
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this,MainActivity::class.java);
                startActivity(i);
                finish();
            },500)
        }
        else{
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this,LoginActivity::class.java);
                startActivity(i);
                finish();
            },500)
        }
    }
}
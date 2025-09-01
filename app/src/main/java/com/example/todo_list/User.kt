package com.example.todo_list

import android.provider.ContactsContract.CommonDataKinds.Phone

data class User(
    var userName:String,
    var userEmail:String,
    var userPhone:String,
    var userBio:String
)

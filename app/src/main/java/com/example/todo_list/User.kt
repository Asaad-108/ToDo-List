package com.example.todo_list

import android.provider.ContactsContract.CommonDataKinds.Phone

data class User(
    var userName:String?=null,
    var userEmail:String?=null,
    var userPhone:String?=null,
    var userBio:String?=null
)

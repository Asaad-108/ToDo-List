package com.example.todo_list

object DummyDB {
    fun getAllData():List<Task> {
        val tasks= mutableListOf<Task>()
        for (i in 0 until 10){
            val temp1 = Task("Coding","Making To-do List")
            val temp2 = Task("Gym","Leg day")
            tasks.add(temp1)
            tasks.add(temp2)
        }
        return tasks
    }
}
package com.example.todo_list

object DummyDB {
    fun getAllData():List<Task> {
        val tasks= mutableListOf<Task>()
        for (i in 0 until 10){
            val temp1 = Task("Coding","Making To-do List")
            val temp2 = Task("Gym","Leg day and i also have a lot of work to do tommorrow is also my school and there is alot of homework left")
            tasks.add(temp1)
            tasks.add(temp2)
        }
        return tasks
    }
}
package com.example.myapplication.usuarisrv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.estructuresDades.Task

class TereaRvAdapter (private val task:List<Task>) :RecyclerView.Adapter<TereaViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TereaViewHolder {
        val layoutInflate = LayoutInflater.from(parent.context)
        return TereaViewHolder(layoutInflate.inflate(R.layout.item_task, parent, false))

    }

    override fun getItemCount(): Int {
        return task.size
    }

    override fun onBindViewHolder(holder: TereaViewHolder, position: Int) {
        holder.printTerea(task[position])

    }

}
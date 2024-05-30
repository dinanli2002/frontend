package com.example.myapplication.usuarisrv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.estructuresDades.Task

class TereaRvAdapter (private val task:List<Task>) :RecyclerView.Adapter<TereaViewHolder>(){
    private var clickListener:ClickListener?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TereaViewHolder {
        val layoutInflate = LayoutInflater.from(parent.context)
        return TereaViewHolder(layoutInflate.inflate(R.layout.item_task, parent, false), clickListener)
    }

    override fun getItemCount(): Int {
        return task.size
    }

    override fun onBindViewHolder(holder: TereaViewHolder, position: Int) {
        holder.printTerea(task[position])
        //holder.bind(task)
        val item = task[position]
        val data =task?.get(position)
        data?.let{holder.bindClickBtnMark(item)}
    }
    /*override fun onBindViewHolder1(holder: TereaViewHolder, position: Int) {
        holder.printTerea(task[position])
        val data = task?.get(position)
        data?.let{holder.bind2ClickBtnDelete("delete")}
    }*/

    fun setOnItemClickListener(clickListener:ClickListener){
        this.clickListener=clickListener
    }

interface ClickListener{
    fun onItemClick(v:View,position: Int)
}
}
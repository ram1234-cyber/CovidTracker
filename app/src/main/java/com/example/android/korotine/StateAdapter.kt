package com.example.android.korotine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_list.view.*

class StateAdapter(val list:List<StatewiseItem>):BaseAdapter() {
    override fun getCount()=list.size

    override fun getItem(p0: Int)=list[p0]


    override fun getItemId(p0: Int)=p0.toLong()

    override fun getView(p0: Int, convertview: View?, parent: ViewGroup?): View {
       val view=convertview?:LayoutInflater.from(parent?.context).inflate(R.layout.item_list,parent,false) //baar baar item creat na ho
        val item=list[p0]
        view.cnfdtv.text=item.confirmed
        view.rcvdtv.text=item.recovered
        view.dcsdtv.text=item.deaths
        view.actv.text=item.active
        view.statetv.text=item.state
        return  view
    }
}
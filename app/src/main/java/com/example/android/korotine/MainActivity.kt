package com.example.android.korotine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TimeUtils
import android.view.LayoutInflater
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var stateAdapter: StateAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header,list,false))
        fetchresult()
    }

    private fun fetchresult() {
        GlobalScope.launch {
            val response = withContext(Dispatchers.IO){ Client.api.execute() }
            if (response.isSuccessful){
                var data = Gson().fromJson(response.body?.string(),Response::class.java)
              launch (Dispatchers.Main){
                  bindcombineddate(data.statewise[0])
                  bindstatwwisedata(data.statewise.subList(1,data.statewise.size))
              }

            }

        }
    }

    private fun bindstatwwisedata(subList: List<StatewiseItem>) {
        stateAdapter=StateAdapter(subList)
        list.adapter=stateAdapter

    }

    private fun bindcombineddate(data: StatewiseItem) {
        val lastupdatedtime:String?=data.lastupdatedtime
        val simpledateformat= SimpleDateFormat("dd/MM/yy HH:mm:ss")
        updatedtime.text="Last Updated\n${gettimeago(simpledateformat.parse(lastupdatedtime))}"
        confrmdtv.text=data.confirmed
        activetv.text=data.active
        recvrdtv.text=data.recovered
        deceasedtv.text=data.deaths



    }
    fun gettimeago(past:Date):String{
        val now=Date()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time-past.time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time-past.time)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time-past.time)

        return when{

            seconds<60->{
                "few seconds ago"
            }
            minutes<60->{
                "$minutes minutes ago"
            }

            hours<24->{
                "$hours hour${minutes%60} min ago"
            }


            else -> {
                SimpleDateFormat("dd/MM/yy hh:mm a").format(past).toString()
            }
        }
    }
}
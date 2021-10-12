package com.example.android.korotine


data class Response( var statewise : List<StatewiseItem>)
data class StatewiseItem (

    var active : String,
     var confirmed : String,
    var deaths : String,
     var deltaconfirmed : String,
     var deltadeaths : String,
     var deltarecovered : String,
   var lastupdatedtime : String,
   var migratedother : String,
    var recovered : String,
    var state : String,
     var statecode : String,
     var statenotes : String

)

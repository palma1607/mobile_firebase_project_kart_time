package br.com.up.karttime.model

import com.google.firebase.Timestamp

data class Race(
    val date:Timestamp,
    val numberLaps:Int,
    val numberPilots:Int,
    val totalTime:Double
)

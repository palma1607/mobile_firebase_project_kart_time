package br.com.up.karttime.repository

import br.com.up.karttime.model.Race
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RaceRepository private constructor(){

    private val collectionPath = "Races"

    companion object{

        private var repository:RaceRepository? = null

        fun instance():RaceRepository{

            return if(repository != null){
                repository!!
            }else{
                repository = RaceRepository()
                repository!!
            }
        }
    }

    fun save(race: Race){
        val database = Firebase.firestore
        database.collection(collectionPath).add(race)
    }

    fun delete(id:String){
        val database = Firebase.firestore
        database.collection(collectionPath)
            .document(id).delete()
    }

    fun update(id:String, race: Race){
        val database = Firebase.firestore
        val document = database
            .collection(collectionPath)
            .document(id)

        document.update("numberLaps",race.numberLaps)
    }

    fun get(){

        val database = Firebase.firestore

        database.collection(collectionPath)
            .get().addOnSuccessListener { documents ->

                for(document in documents){

                    val numberLaps =
                        document.data["numberLaps"] as Long
                    val numberPilots =
                        document.data["numberPilots"] as Long
                    val totalTime =
                        document.data["totalTime"] as Double
                    val date = document.data["date"]
                            as Timestamp

                    Race(date,numberLaps.toInt(),numberPilots.toInt(),totalTime)
                }
            }
    }
}
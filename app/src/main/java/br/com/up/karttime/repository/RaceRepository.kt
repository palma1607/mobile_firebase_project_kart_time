package br.com.up.karttime.repository

import br.com.up.karttime.model.Race
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RaceRepository private constructor(){

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
        database.collection("Races").add(race)

    }

}
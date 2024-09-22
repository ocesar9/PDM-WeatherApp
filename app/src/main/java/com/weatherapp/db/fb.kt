package com.weatherapp.db

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.weatherapp.models.City
import com.weatherapp.models.User

// City

class FBCity {
    var name: String? = null
    var lat: Double? = null
    var lng: Double? = null

    fun toCity(): City {
        val latlng = LatLng(lat ?: 0.0, lng ?: 0.0)
        return City(
            name!!,
            location = latlng
        )
    }
}

fun City.toFBCity(): FBCity {
    val fbCity = FBCity()

    fbCity.name = this.name
    fbCity.lat = this.location?.latitude ?: 0.0
    fbCity.lng = this.location?.longitude ?: 0.0

    return fbCity
}

// User

class FBUser {
    var name: String? = null
    var email: String? = null
    fun toUser() = User(name!!, email!!)
}


fun User.toFBUser(): FBUser {
    val fbUser = FBUser()
    fbUser.name = this.name
    fbUser.email = this.email

    return fbUser
}

//Database

class FBDatabase(
    private val listener: Listener? = null
) {
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private var citiesListReg: ListenerRegistration? = null

    interface Listener {
        fun onUserLoaded(user: User)
        fun onCityAdded(city: City)
        fun onCityRemoved(city: City)
    }

    init {
        auth.addAuthStateListener { auth ->
            if (auth.currentUser == null) {
                citiesListReg?.remove()

                return@addAuthStateListener
            }
            val refCurrUser = db.collection("users")
                .document(auth.currentUser!!.uid)
            refCurrUser.get().addOnSuccessListener {
                it.toObject(
                    FBUser::class.java
                )?.let { user -> listener?.onUserLoaded(user.toUser()) }
            }
            citiesListReg = refCurrUser.collection("cities")
                .addSnapshotListener { snapshots, ex ->
                    if (ex != null) return@addSnapshotListener
                    snapshots?.documentChanges?.forEach { change ->
                        val fbCity = change.document.toObject(FBCity::class.java)
                        if (change.type == DocumentChange.Type.ADDED) {
                            listener?.onCityAdded(fbCity.toCity())
                        } else if (change.type == DocumentChange.Type.REMOVED) {
                            listener?.onCityRemoved(fbCity.toCity())
                        }
                    }
                }
        }
    }

    fun register(user: User) {
        if (auth.currentUser == null) throw RuntimeException("User not logged in!")
        val uid = auth.currentUser!!.uid
        db.collection("users").document(uid + "").set(user.toFBUser());
    }

    fun add(city: City) {
        if (auth.currentUser == null) throw RuntimeException("User not logged in!")
        val uid = auth.currentUser!!.uid
        db.collection("users").document(uid).collection("cities").document(city.name)
            .set(city.toFBCity())
    }

    fun remove(city: City) {
        if (auth.currentUser == null) throw RuntimeException("User not logged in!")
        val uid = auth.currentUser!!.uid
        db.collection ("users").document(uid).collection("cities").document(city.name).delete()
    }
}
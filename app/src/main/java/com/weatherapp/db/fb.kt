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
    var monitored: Boolean = false

    fun toCity(): City {
        val latlng = LatLng(lat ?: 0.0, lng ?: 0.0)
        return City(
            name!!,
            location = latlng,
            isMonitored = monitored
        )
    }
}

fun City.toFBCity(): FBCity {
    val fbCity = FBCity()

    fbCity.name = this.name
    fbCity.lat = this.location?.latitude ?: 0.0
    fbCity.lng = this.location?.longitude ?: 0.0
    fbCity.monitored = this.isMonitored

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
        fun onUserSignOut()
        fun onCityUpdated(city: City)


    }

    init {
        auth.addAuthStateListener { auth ->
            if (auth.currentUser == null) {
                citiesListReg?.remove()
                listener?.onUserSignOut()

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
                        when (change.type) {
                            DocumentChange.Type.ADDED -> listener?.onCityAdded(fbCity.toCity())
                            DocumentChange.Type.REMOVED -> listener?.onCityRemoved(fbCity.toCity())
                            DocumentChange.Type.MODIFIED -> listener?.onCityUpdated(fbCity.toCity())
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

    fun update(city: City) {
        if (auth.currentUser == null) throw RuntimeException("Not logged in!")
        val uid = auth.currentUser!!.uid
        val fbCity = city.toFBCity()
        val changes = mapOf( "lat" to fbCity.lat, "lng" to fbCity.lng,
            "monitored" to fbCity.monitored )
        db.collection("users").document(uid)
            .collection("cities").document(fbCity.name!!).update(changes)
    }

    fun remove(city: City) {
        if (auth.currentUser == null) throw RuntimeException("User not logged in!")
        val uid = auth.currentUser!!.uid
        db.collection ("users").document(uid).collection("cities").document(city.name).delete()
    }
}
package com.example.adiblarapp.models

import com.google.firebase.firestore.Exclude
import java.io.Serializable

class AdibType: Serializable {

    @Exclude var id: String? = null
    var adibType: String? = null
    var list: List<Adib>? = null

    constructor(adibType: String?, list: List<Adib>) {
        this.adibType = adibType
        this.list = list
    }

    constructor(id: String?, adibType: String?, list: List<Adib>?) {
        this.id = id
        this.adibType = adibType
        this.list = list
    }

    constructor()


}
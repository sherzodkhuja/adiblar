package com.example.adiblarapp.models

import java.io.Serializable

class Adib : Serializable {
    var name: String? = null
    var familyName: String? = null
    var bornYear: String? = null
    var deathYear: String? = null
    var image: String? = null
    var about: String? = null
    var adibType: String? = null
    var isSaved: Boolean? = null
    var id: String? = null


    constructor()
    constructor(
        name: String?,
        familyName: String?,
        bornYear: String?,
        deathYear: String?,
        image: String?,
        about: String?,
        adibType: String?
    ) {
        this.name = name
        this.familyName = familyName
        this.bornYear = bornYear
        this.deathYear = deathYear
        this.image = image
        this.about = about
        this.adibType = adibType
    }

    constructor(
        name: String?,
        familyName: String?,
        bornYear: String?,
        deathYear: String?,
        image: String?,
        about: String?,
        adibType: String?,
        isSaved: Boolean
    ) {
        this.name = name
        this.familyName = familyName
        this.bornYear = bornYear
        this.deathYear = deathYear
        this.image = image
        this.about = about
        this.adibType = adibType
        this.isSaved = isSaved
    }

    constructor(
        name: String?,
        familyName: String?,
        bornYear: String?,
        deathYear: String?,
        image: String?,
        about: String?,
        adibType: String?,
        isSaved: Boolean,
        id: String
    ) {
        this.name = name
        this.familyName = familyName
        this.bornYear = bornYear
        this.deathYear = deathYear
        this.image = image
        this.about = about
        this.adibType = adibType
        this.isSaved = isSaved
        this.id = id
    }

}
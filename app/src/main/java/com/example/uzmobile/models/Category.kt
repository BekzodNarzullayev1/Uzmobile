package com.example.uzmobile.models

import java.io.Serializable

class Category:Serializable {
    var name:String?=null
    var list: List<Package>?=null

    constructor(name: String?, list: List<Package>?) {
        this.name = name
        this.list = list
    }

    constructor()

}
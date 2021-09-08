package com.example.uzmobile.models

import java.io.Serializable

class Package:Serializable {
    var categoryName:String?=null
    var count:String?=null
    var name:String?=null
    var info:String?=null
    var code:String?=null
    var lang:String?=null


    constructor()

    constructor(categoryName: String?, count: String?, name: String?, info: String?) {
        this.categoryName = categoryName
        this.count = count
        this.name = name
        this.info = info
    }

    constructor(count: String?, name: String?, info: String?) {
        this.count = count
        this.name = name
        this.info = info
    }

    constructor(
        categoryName: String?,
        count: String?,
        name: String?,
        info: String?,
        code: String?
    ) {
        this.categoryName = categoryName
        this.count = count
        this.name = name
        this.info = info
        this.code = code
    }

    constructor(
        categoryName: String?,
        count: String?,
        name: String?,
        info: String?,
        code: String?,
        lang: String?
    ) {
        this.categoryName = categoryName
        this.count = count
        this.name = name
        this.info = info
        this.code = code
        this.lang = lang
    }

}

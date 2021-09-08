package com.example.uzmobile.models

import java.io.Serializable


class Status : Serializable{
    var name: String? = null
    var min: String? = null
    var mb: String? = null
    var sms: String? = null
    var price: String? = null
    var info: String? = null
    var code: String? = null
    var lang:String? =null

    constructor()

    constructor(
        name: String?,
        min: String?,
        mb: String?,
        sms: String?,
        price: String?,
        info: String?,
        code: String?,
        lang: String?
    ) {
        this.name = name
        this.min = min
        this.mb = mb
        this.sms = sms
        this.price = price
        this.info = info
        this.code = code
        this.lang = lang
    }

}
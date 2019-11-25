package com.egco428.egci428_practice.model

class User (val username: String, val password: String, val latitude: Double, val longitude: Double){
    constructor(): this("","", 0.toDouble(), 0.toDouble())
}
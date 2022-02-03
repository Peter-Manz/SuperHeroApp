package com.example.j86881

import java.io.Serializable

class Hero(val image: String ,val id : String, val name : String, val intelligence :String , val strength: String,
           val power: String, val speed: String, val durability: String, val combat: String) : Serializable {
}
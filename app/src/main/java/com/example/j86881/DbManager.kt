package com.example.j86881

import android.content.ContentValues
import android.content.Context

class DbManager(context: Context) {
    private var helper: DbHelper = DbHelper(context)

    fun create(hero : Hero){
        val heroValues = ContentValues()
        heroValues.put("Name",hero.name)
        heroValues.put("Image",hero.image)
        heroValues.put("Id",hero.id)
        heroValues.put("Intelligence",hero.intelligence)
        heroValues.put("Strength",hero.strength)
        heroValues.put("Power",hero.power)
        heroValues.put("Speed",hero.speed)
        heroValues.put("Durability",hero.durability)
        heroValues.put("Combat",hero.combat)

        helper.writableDatabase.insert("HeroTable",null,heroValues)
    }
    fun retrieveAllHeros(): List<Hero>{
        val heros = mutableListOf<Hero>()
        val query = "Select * FROM HeroTable"
        val cursor = helper.readableDatabase.rawQuery(query, null)
        if(cursor.moveToFirst()){
            do {
                val nameColIndex = cursor.getColumnIndex("Name")
                val name = cursor.getString(nameColIndex)
                val imgColIndex = cursor.getColumnIndex("Image")
                val image = cursor.getString(imgColIndex)
                val idColIndex = cursor.getColumnIndex("Id")
                val id = cursor.getString(idColIndex)
                val intColIndex = cursor.getColumnIndex("Intelligence")
                val intelligence = cursor.getString(intColIndex)
                val strColIndex = cursor.getColumnIndex("Strength")
                val strength = cursor.getString(strColIndex)
                val pwrColIndex = cursor.getColumnIndex("Power")
                val power = cursor.getString(pwrColIndex)
                val speedColIndex = cursor.getColumnIndex("Speed")
                val speed = cursor.getString(speedColIndex)
                val durColIndex = cursor.getColumnIndex("Durability")
                val durability = cursor.getString(durColIndex)
                val comColIndex = cursor.getColumnIndex("Combat")
                val combat = cursor.getString(comColIndex)
                val hero =Hero(image,id,name,intelligence,strength,power,speed,durability,combat)
                heros.add(hero)
            }while(cursor.moveToNext())
        }
        cursor.close()
        return heros
    }

    fun delete(hero : Hero){
        if(hero.id!= null){
            helper.writableDatabase.delete("HeroTable","id=?", arrayOf(hero.id.toString()))
        }
    }
    fun closeConnection(){
        helper.close()
    }
}

package com.example.j86881

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context): SQLiteOpenHelper(context, "HerosDataBase", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = """CREATE TABLE 'HeroTable' (
            'Name' TEXT NOT NULL PRIMARY KEY, 
            'Image' TEXT ,
            'Id' TEXT NOT NULL,
            'Intelligence' TEXT , 
            'Strength' TEXT , 
            'Power' TEXT , 
            'Speed' TEXT , 
            'Durability' TEXT,
            'Combat' TEXT)"""
        db?.execSQL(query)

    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // compare versions and make changes to db structures accordingly
    }
}



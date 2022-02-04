package com.example.j86881
import android.os.Bundle
import android.view.View
import com.example.j86881.databinding.ActivityAddHeroBinding
import kotlin.random.Random

class AddHero : SearchActivity() {
    private lateinit var binding: ActivityAddHeroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHeroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
    fun addRandomHero(view: View) {

        //start of adapted from kotlinlang (2022)
        val randomValues = List(10){
            Random.nextInt(1,731)
        }
        val randomInput = randomValues.get(5)
        randomInput.toString()
        //end of adapted Code

        runOnUiThread {
            fetchData(
                "https://superheroapi.com/api/10159421415666211/" + randomInput + "/powerstats",
                "https://superheroapi.com/api/10159421415666211/" + randomInput + "/image"
            )
        }

    }
}
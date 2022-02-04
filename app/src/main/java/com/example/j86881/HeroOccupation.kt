package com.example.j86881
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.j86881.databinding.ActivityHeroOccupationBinding
import org.json.JSONObject
import java.io.IOException

class HeroOccupation : SearchActivity() {
    private lateinit var binding: ActivityHeroOccupationBinding
    var heroID : String ?= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroOccupationBinding.inflate(layoutInflater)
        val view =binding.root
        setContentView(view)

        val bundle : Bundle? = intent.extras
        heroID = bundle!!.getString("heroID")
        binding.idResultTvOcc.text = heroID
        dataResults()
    }
    private fun dataResults(){
        val thread = Thread {

            if(heroID!!.toInt()>0 || heroID!!.toInt()<=731) {
                heroID.toString()
                val urlString = "https://superheroapi.com/api/10159421415666211/" + heroID + "/work"
                val text = scanUrlToString(urlString)
                fetchWorkData(text)
            }
        }
        thread.start()

    }
    private fun fetchWorkData(urlString: String) {
        val thread = Thread {
            try {
                val heroObject = JSONObject(urlString)
                val name = heroObject.getString("name").toString()
                val occupation = heroObject.getString("occupation").toString()
                val base = heroObject.getString("base").toString()
                runOnUiThread {
                    if(occupation.startsWith("-"))
                    {
                        binding.nameResultOcc.text = name
                        binding.occResult.text = "Database Contains no details on this hero"
                    }else{
                        binding.nameResultOcc.text = name
                        binding.occResult.text = occupation
                        binding.locResults.text = base
                    }
                }
            } catch (e: IOException) {
                runOnUiThread {
                    binding.locResults.text = "There was an error $e"
                }
            }
        }
        thread.start()
    }
}
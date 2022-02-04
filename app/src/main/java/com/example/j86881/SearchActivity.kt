package com.example.j86881

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.j86881.databinding.ActivitySearchBinding
import org.json.JSONObject
import java.io.IOException
import java.lang.NumberFormatException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

open class SearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                toggleButtonState( binding.etSearch.text.length<=3 && binding.etSearch.text.length>0)
            }
            override fun afterTextChanged(s: Editable?) { }})
        toggleButtonState(false)

    }
    fun searchHeroID(view: View){
        runOnUiThread {
            var userInput = testUserInput(binding.etSearch.text.toString())
            fetchData(
                "https://superheroapi.com/api/10159421415666211/" + userInput + "/powerstats",
                "https://superheroapi.com/api/10159421415666211/" + userInput + "/image")

        }

    }
    private fun testUserInput(userInput: String): String {
        val userInputInt = userInput.toInt()
        if(!(userInputInt<1||userInputInt>731)){
            return userInput.toString()
        }else{
           return "176"
        }
    }
    private fun toggleButtonState(enabled:Boolean){
        //start of adapted code from Stackoverflow (2013)
        binding.btnSearch.isEnabled = enabled
    }
    fun clearEditTextSearch(view: View){
        binding.etSearch.getText().clear()
    }
    public fun scanUrlToString(urlString : String): String {
        val url = URL(urlString)
        var text = ""
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection;
        connection.connectTimeout = 10000
        connection.readTimeout = 10000
        connection.requestMethod = "GET"
        connection.connect()

        var responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val scanner = Scanner(connection.inputStream).useDelimiter("\\A")
            text = if (scanner.hasNext()) scanner.next() else ""

        }
        return text
    }
    public fun fetchData(urlString: String, urlStringImg : String){
        val thread = Thread{
            try{
                val heroObject = JSONObject(scanUrlToString(urlString))
                val heroObjectImg = JSONObject(scanUrlToString(urlStringImg))
                val urlImage = heroObjectImg.getString("url").toString()
                val id =heroObject.getString("id").toString()
                val name = heroObject.getString("name").toString()
                val intelligence = heroObject.getString("intelligence").toString()
                val strength= heroObject.getString("strength").toString()
                val speed = heroObject.getString("speed").toString()
                val durability = heroObject.getString("durability").toString()
                val power = heroObject.getString("power").toString()
                val combat = heroObject.getString("combat").toString()
                val hero = Hero(urlImage,id,name,intelligence,strength,speed,durability,power,combat)

                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("hero",hero)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } catch(e: IOException){
            }
        }
        thread.start()
    }
}
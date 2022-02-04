package com.example.j86881
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.j86881.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    lateinit var heroAdapter: HeroAdapter
    lateinit var preferenceManager: SharedPreferences
    private lateinit var dbManager : DbManager
    private var heros = mutableListOf<Hero>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        dbManager = DbManager(this)
        loadHeros()

        //start of adapted code from Azhar (2020)
        heroAdapter = HeroAdapter(this,heros)
        //end of adapted code

        binding.recyclerView.adapter = heroAdapter

    }
    val addHeroResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {result ->
        if(result.resultCode == Activity.RESULT_OK){
            val newHeroList = result.data?.getSerializableExtra("hero") as Hero
            dbManager.create(newHeroList)
            heroAdapter.heros.add(0,newHeroList)
            heroAdapter.notifyItemInserted(0)
            refreshRecyclerView()
        }
    }
    private fun loadHeros(){
        //start of adapted code from bezkoder (2021)
        var heroList = listOf<Hero>()
        heroList = dbManager.retrieveAllHeros()
        heros = heroList.toMutableList()
        //end of adapted code
    }
    private fun refreshRecyclerView(){
        loadHeros()
        heroAdapter.notifyDataSetChanged()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.searchName_action){
            addHeroResult.launch(Intent(this,SearchActivity::class.java))
        }
        if(item.itemId==R.id.addHeroAction){
            addHeroResult.launch(Intent(this,AddHero::class.java))
        }
        if(item.itemId==R.id.settingsAction){
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy(){
        dbManager.closeConnection()
        super.onDestroy()
    }
}
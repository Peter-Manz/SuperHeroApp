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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        heroAdapter = HeroAdapter()
        binding.recyclerView.adapter = heroAdapter

        val swipeCallBack = SwipeCallBack()
        val touchHelper = ItemTouchHelper(swipeCallBack)
        touchHelper.attachToRecyclerView(binding.recyclerView)
    }
    val addHeroResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {result ->
        if(result.resultCode == Activity.RESULT_OK){
            val newHeroList = result.data?.getSerializableExtra("hero") as Hero
            heroAdapter.heros.add(0,newHeroList)
            heroAdapter.notifyItemInserted(0)
        }
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
    inner class SwipeCallBack : ItemTouchHelper.Callback(){
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder): Int {
            val deleteFlag = ItemTouchHelper.LEFT
            return makeMovementFlags(0,deleteFlag)
        }
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (direction == ItemTouchHelper.LEFT){
                heroAdapter.heros.removeAt(viewHolder.bindingAdapterPosition)
                heroAdapter.notifyItemRemoved(viewHolder.bindingAdapterPosition)}
        }

    }
}
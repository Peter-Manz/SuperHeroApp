package com.example.j86881

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.j86881.databinding.CardviewHeroBinding

class HeroAdapter : RecyclerView.Adapter<HeroAdapter.HeroViewHolder>(){
    var heros = mutableListOf<Hero>()
    var prefsChangedListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroAdapter.HeroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_hero, parent, false)
        return HeroViewHolder(view)
    }
    override fun onBindViewHolder(holder: HeroAdapter.HeroViewHolder, position: Int) {
        val hero = heros[position]
        val context = holder.binding.superHeroImg.context
        Glide.with(context).load(hero.image).into(holder.binding.superHeroImg)
        holder.binding.idResultTv.text = hero.id
        holder.binding.nameResult.text = hero.name
        holder.binding.intResult.text = hero.intelligence
        holder.binding.strResult.text = hero.strength
        holder.binding.durResult.text = hero.durability
        holder.binding.pwrResult.text = hero.power
        holder.binding.cbtResult.text = hero.combat
        holder.binding.spdResult.text = hero.speed

        holder.binding.infoBtn.setOnClickListener{
            val intent = Intent(context,HeroOccupation::class.java)
            intent.putExtra("heroID",hero.id)
            ContextCompat.startActivity(context, intent, Bundle())
        }

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val boolVal = prefs.getBoolean("disable",false)
        if(boolVal){
            holder.binding.strResult.visibility = View.GONE
            holder.binding.durResult.visibility = View.GONE
            holder.binding.pwrResult.visibility = View.GONE
            holder.binding.cbtResult.visibility = View.GONE
            holder.binding.spdResult.visibility = View.GONE
            holder.binding.intResult.visibility = View.GONE
            holder.binding.idResultTv.visibility = View.GONE
            holder.binding.strengthTv.visibility =View.GONE
            holder.binding.powerTv.visibility =View.GONE
            holder.binding.combatTv.visibility =View.GONE
            holder.binding.speedTv.visibility =View.GONE
            holder.binding.durabilityTv.visibility =View.GONE
            holder.binding.intelligenceTv.visibility =View.GONE
            holder.binding.heroIDtv.visibility = View.GONE
            holder.binding.infoBtn.visibility = View.GONE
        }else{
            holder.binding.idResultTv.visibility = View.VISIBLE
            holder.binding.strResult.visibility = View.VISIBLE
            holder.binding.durResult.visibility = View.VISIBLE
            holder.binding.pwrResult.visibility = View.VISIBLE
            holder.binding.cbtResult.visibility = View.VISIBLE
            holder.binding.spdResult.visibility = View.VISIBLE
            holder.binding.intResult.visibility = View.VISIBLE
            holder.binding.idResultTv.visibility = View.VISIBLE
            holder.binding.strengthTv.visibility =View.VISIBLE
            holder.binding.powerTv.visibility =View.VISIBLE
            holder.binding.combatTv.visibility =View.VISIBLE
            holder.binding.speedTv.visibility =View.VISIBLE
            holder.binding.durabilityTv.visibility =View.VISIBLE
            holder.binding.intelligenceTv.visibility =View.VISIBLE
            holder.binding.heroIDtv.visibility = View.VISIBLE
            holder.binding.infoBtn.visibility = View.VISIBLE
        }


    }
    override fun getItemCount(): Int {
        return heros.size
    }
    inner class HeroViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val binding = CardviewHeroBinding.bind(itemView)

    }
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        PreferenceManager.getDefaultSharedPreferences(recyclerView.context).registerOnSharedPreferenceChangeListener(prefsChangedListener)
    }

}
package fr.isen.bah.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import fr.isen.bah.androiderestaurant.databinding.ActivityHomeBinding
import fr.isen.bah.androiderestaurant.dish.DishesActivity

class HomeActivity : BaseActivity(){
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val buttonEntries = binding.buttonEntries
        val buttonDishes = binding.buttonDishes
        val buttonDeserts = binding.buttonDesserts


        buttonEntries.setOnClickListener {
            changeActivity(getString(R.string.home_entries))
        }
        buttonDishes.setOnClickListener {
            changeActivity(getString(R.string.home_dishes))
        }
        buttonDeserts.setOnClickListener {
            changeActivity(getString(R.string.home_deserts))
        }

    }

    private fun changeActivity(category:String) {
        val changePage: Intent = Intent(this, DishesActivity::class.java)
        changePage.putExtra("category_type",category)
        Log.i("INFO","End of HomeActivity")
        startActivity(changePage)
    }
}
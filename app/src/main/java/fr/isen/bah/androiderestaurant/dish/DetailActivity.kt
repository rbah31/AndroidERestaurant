package fr.isen.bah.androiderestaurant.dish


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.bah.androiderestaurant.HomeActivity
import fr.isen.bah.androiderestaurant.BaseActivity
import fr.isen.bah.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.bah.androiderestaurant.model.BasketData
import fr.isen.bah.androiderestaurant.model.DishBasket
import fr.isen.bah.androiderestaurant.model.DishModel
import java.io.File


class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        val dish = intent.getSerializableExtra("dish") as DishModel
        initDetail(dish)
        var list = ""
        val listIngredients =
            (intent.getSerializableExtra("dish") as DishModel).getFormatedIngredients()
        for (i in listIngredients.indices) {
            list += listIngredients[i].name_fr + ", "
        }
        binding.detail.text = list
        var counter = 1
        val dishName = (intent.getSerializableExtra("dish") as DishModel)
        val price = (intent.getSerializableExtra("dish") as DishModel).prices[0].price
        var totalPrice = price.toFloat() * counter
        val buttonMinus = binding.buttonMinus
        val buttonPlus = binding.buttonPlus
        binding.buttonTot.text = "Total : " + price + "€"

        buttonPlus.setOnClickListener {
            counter++
            totalPrice = price.toFloat() * counter
            binding.counter.text = counter.toString()
            binding.buttonTot.text = "Total : " + totalPrice.toString() + "€"
        }
        buttonMinus.setOnClickListener {
            if (counter != 1) {
                counter--
                binding.counter.text = counter.toString()
                binding.buttonTot.text = "Total : " + totalPrice.toString() + "€"
            }
        }
        val buttonTot = binding.buttonTot

        buttonTot.setOnClickListener {
            var data = ArrayList<BasketData>()
            val filename = "/panier.json"
            if (File(cacheDir.absolutePath + filename).exists()) {
                var basketNumberOfElement: Int
                Snackbar.make(it, "Ajouté au panier", Snackbar.LENGTH_LONG).show()
                if (File(cacheDir.absolutePath + filename).readText().isNotEmpty()) {
                    val recup = File(cacheDir.absolutePath + filename).readText();
                    val result = Gson().fromJson(recup, DishBasket::class.java)
                    basketNumberOfElement = result.quantity
                    for (j in result.dishName.indices) {
                        BasketAdd(
                            BasketData(
                                result.dishName[j].dishName,
                                result.dishName[j].quantity
                            ), data
                        )
                    }

                    BasketAdd(BasketData(dishName, counter), data)
                    basketNumberOfElement += counter
                    val editor = sharedPreferences.edit()
                    editor.putInt(basketCount, basketNumberOfElement)
                    editor.apply()
                    File(cacheDir.absolutePath + filename).writeText(
                        Gson().toJson(
                            DishBasket(
                                data,
                                basketNumberOfElement
                            )
                        )
                    )
                } else {
                    File(cacheDir.absolutePath + filename).writeText(
                        Gson().toJson(
                            DishBasket(
                                mutableListOf(BasketData(dishName, counter)),
                                1
                            )
                        )
                    )
                    val editor = sharedPreferences.edit()
                    editor.putInt(basketCount, 1)
                    editor.apply()
                }
            }
            else{
                File(cacheDir.absolutePath + filename).writeText(
                    Gson().toJson(
                        DishBasket(
                            mutableListOf(BasketData(dishName, counter)),
                            1
                        )
                    )
                )
                val editor = sharedPreferences.edit()
                editor.putInt(basketCount, 1)
                editor.apply()
            }
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun BasketAdd(
        objectToAdd: BasketData,
        data: ArrayList<BasketData>
    ) {
        var bool = false

        for (i in data.indices)
            if (objectToAdd.dishName == data[i].dishName) {
                data[i].quantity += objectToAdd.quantity
                bool = true
            }
        if (bool == false) data.add(
            BasketData(
                objectToAdd.dishName,
                objectToAdd.quantity
            )
        )

    }

    private fun initDetail(dish: DishModel) {
        binding.detailTitle.text = dish.name_fr
        binding.dishPhotoPager.adapter = DishPictureAdapter(this, dish.pictures)
    }

    companion object {
        const val APP_PREFS = "app_prefs"
        const val basketCount = "basket_count"
    }

}
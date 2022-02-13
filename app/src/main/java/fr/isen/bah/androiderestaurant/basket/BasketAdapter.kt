package fr.isen.bah.androiderestaurant.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.bah.androiderestaurant.R
import fr.isen.bah.androiderestaurant.databinding.ViewBasketBinding
import fr.isen.bah.androiderestaurant.model.BasketData


class BasketAdapter(private val dishes: MutableList<BasketData>,val onBinClicked: (BasketData) -> Unit) : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    class BasketViewHolder(private val binding: ViewBasketBinding): RecyclerView.ViewHolder(binding.root){
        val dishName = binding.dishName
        val dishPicture = binding.dishImage
        val dishQuantity = binding.quantity
        val deleteItem = binding.bin
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val binding = ViewBasketBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return BasketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val dish = dishes[position]
        holder.dishName.text = dish.dishName.name_fr

        Picasso.get()
            .load(dishes[position].dishName.getFirstPicture())
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.dishPicture)

        holder.dishQuantity.text = "Ajouter au panier : " +dishes[position].quantity.toString()
        holder.deleteItem.setOnClickListener {
            if(position < dishes.size) {
                val elementToRemove = dishes[position]
                onBinClicked.invoke(elementToRemove)
                dishes.remove(elementToRemove)
                notifyDataSetChanged()
            }
        }
    }
    override fun getItemCount(): Int = dishes.size
}
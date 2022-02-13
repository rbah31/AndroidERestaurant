package fr.isen.bah.androiderestaurant.dish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.isen.bah.androiderestaurant.R
import fr.isen.bah.androiderestaurant.databinding.FragmentDishPictureBinding

class DishPictureFragment : Fragment() {
    private lateinit var binding: FragmentDishPictureBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDishPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("picture_url")?.let { pictureUrl ->
            if(pictureUrl=="") {
                binding.dishPictureFrag.setImageResource(R.drawable.ic_launcher_foreground)
            }
            else {
                Picasso.get()
                    .load(pictureUrl)
                    .error(R.drawable.ic_launcher_foreground)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(binding.dishPictureFrag)

            }
        }

    }

    companion object{
        fun newInstance(pictureUrl: String) =
            DishPictureFragment().apply {
                arguments= Bundle().apply{
                    putString("picture_url",pictureUrl)
                }
            }
    }
}
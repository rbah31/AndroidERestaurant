package fr.isen.bah.androiderestaurant.connection


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import fr.isen.bah.androiderestaurant.R
import fr.isen.bah.androiderestaurant.databinding.ActivityConnectionBinding

class ConnectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConnectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val buttonRegister = binding.buttonRegister
        val buttonLogin = binding.buttonLogin
        val fragmentManager: FragmentManager = supportFragmentManager
        var fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment, LoginFragment()).commit()

        buttonRegister.setOnClickListener {
            fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment, RegisterFragment()).commit()
        }

        buttonLogin.setOnClickListener {
            fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment, LoginFragment()).commit()
        }
    }

}
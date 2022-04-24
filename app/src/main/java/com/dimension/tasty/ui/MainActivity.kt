package com.dimension.tasty.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.dimension.tasty.R
import com.dimension.tasty.databinding.ActivityMainBinding
import com.dimension.tasty.db.RecipeDataBase
import com.dimension.tasty.repository.TastyRepository
import androidx.navigation.ui.setupWithNavController


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: TastyViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.frame_layout) as NavHostFragment
            val navController = navHostFragment.navController
            binding.bottomNavigation.setupWithNavController(navController)


            val newsRepository = TastyRepository(RecipeDataBase(this))
            val viewModelFactory = TastyViewModelProviderFactory(newsRepository)
            viewModel = ViewModelProvider(this, viewModelFactory)[TastyViewModel::class.java]



    }

    fun hideBottomNavigationView(){
        binding.bottomNavigation.clearAnimation()
        binding.bottomNavigation.animate().translationY(binding.bottomNavigation.height.toFloat()).duration = 300
        binding.bottomNavigation.visibility = View.GONE
    }

    fun showBottomNavigationView(){
        binding.bottomNavigation.clearAnimation()
        binding.bottomNavigation.animate().translationY(0f).duration = 300
        binding.bottomNavigation.visibility = View.VISIBLE
    }


}
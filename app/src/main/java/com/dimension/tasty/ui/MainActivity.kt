package com.dimension.tasty.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dimension.tasty.R
import com.dimension.tasty.databinding.ActivityMainBinding
import com.dimension.tasty.db.RecipeDataBase
import com.dimension.tasty.repository.TastyRepository

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: TastyViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frame_layout) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)


        val newsRepository = TastyRepository(RecipeDataBase(this))
        val viewModelFactory = TastyViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TastyViewModel::class.java)
    }
}
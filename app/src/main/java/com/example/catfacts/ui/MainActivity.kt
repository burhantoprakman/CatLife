package com.example.catfacts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.catfacts.R
import com.example.catfacts.repository.CatBreedsRepository
import com.example.catfacts.repository.CatFactRepository
import com.example.catfacts.viewmodel.CatBreedsViewModel
import com.example.catfacts.viewmodel.CatBreedsViewModelFactory
import com.example.catfacts.viewmodel.CatFactViewModel
import com.example.catfacts.viewmodel.CatFactViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //View models
    lateinit var catBreedsViewModel: CatBreedsViewModel
    lateinit var catFactViewModel: CatFactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNagivationAndActionBar()
        //Catbreeds Viewmodel create
        val catBreedsRepository = CatBreedsRepository()
        val catBreedsViewModelFactory = CatBreedsViewModelFactory(catBreedsRepository)
        catBreedsViewModel =
            ViewModelProvider(this, catBreedsViewModelFactory)[CatBreedsViewModel::class.java]

        //CatFact viewmodel create and pass repository
        val catFactRepository = CatFactRepository()
        val catFactViewModelFactory = CatFactViewModelFactory(catFactRepository)
        catFactViewModel =
            ViewModelProvider(this, catFactViewModelFactory)[CatFactViewModel::class.java]
    }

    fun setupNagivationAndActionBar() {
        //Bottom navigation bar and controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        //Created app config from nav controller graph and added it into action var
        val appBarConfiguration = AppBarConfiguration(bottomNavigationView.menu)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

}
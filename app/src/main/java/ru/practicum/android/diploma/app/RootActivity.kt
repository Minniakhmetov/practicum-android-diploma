package ru.practicum.android.diploma.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        setupBottomNavigation()

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.API_ACCESS_TOKEN)
    }

    private fun setupBottomNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val divider = findViewById<View>(R.id.bottom_navigation_divider)

        bottomNavigation.setupWithNavController(navController)

        // На экранах вакансии и фильтра нижней панели нет
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isBottomNavigationVisible = destination.id !in screensWithoutBottomNavigation
            bottomNavigation.isVisible = isBottomNavigationVisible
            divider.isVisible = isBottomNavigationVisible
        }
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

    private companion object {
        val screensWithoutBottomNavigation = setOf(R.id.vacancyFragment, R.id.filterFragment)
    }
}

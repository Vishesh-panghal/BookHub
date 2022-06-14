package com.vishesh.bookhub.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.vishesh.bookhub.R
import com.vishesh.bookhub.fragmentation.AboutAppFragmentation
import com.vishesh.bookhub.fragmentation.DashboardFragmentation
import com.vishesh.bookhub.fragmentation.FavouriteFragment
import com.vishesh.bookhub.fragmentation.ProfileFragment

class MainActivity : AppCompatActivity() {

    //    variable declare
    lateinit var drawer: DrawerLayout
    lateinit var coordinator: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frame: FrameLayout
    lateinit var navigationView: NavigationView

    var previousMenuIItem: MenuItem? = null

    //        onCreate function start
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //      variable assign
        drawer = findViewById(R.id.drawer)
        coordinator = findViewById(R.id.coordinator)
        toolbar = findViewById(R.id.toolbar)
        frame = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)
        setUpToolbar()
        openDashboard()
        // change the icon to back icon
        drawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

//        Click Listener on navigation drawer
        navigationView.setNavigationItemSelectedListener {

            if (previousMenuIItem != null) {
                previousMenuIItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuIItem = it
            when (it.itemId) {
                R.id.dashboard -> {
                    openDashboard()
                    drawer.closeDrawers()
                }
                R.id.favourite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, FavouriteFragment())
                        .commit()
                    supportActionBar?.title = "Favourites"
                    drawer.closeDrawers()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, ProfileFragment())
                        .commit()
                    supportActionBar?.title = "Profile"
                    drawer.closeDrawers()
                }
                R.id.about_app -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, AboutAppFragmentation())
                        .commit()
                    supportActionBar?.title = "About App"
                    drawer.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    //   toolbar setup and give name
    fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
//        Activate  Hamburger icon
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //        Setup Hamburger Icon
    val actionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(
        this@MainActivity,
        drawer,
        R.string.open_drawer,
        R.string.close_drawer
    )
    }

    //To open navigation drawer open from left side with Hamburger icon
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == item.itemId) {
            drawer.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    // This function is used to open dashboard as we open app, otherwise we a saw a blank screen
    private fun openDashboard() {
        val fragmentation = DashboardFragmentation()
        val transition =
            supportFragmentManager.beginTransaction().replace(R.id.frame, DashboardFragmentation())
                .commit()
        supportActionBar?.title = "Dashboard"
        navigationView.setCheckedItem(R.id.dashboard)
    }

    // As we press back button (if) fragmentation is !dashboard than open dashboard otherwise close the app.
    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)
        when (frag) {
            !is DashboardFragmentation -> openDashboard()
            else -> super.onBackPressed()
        }
    }
}
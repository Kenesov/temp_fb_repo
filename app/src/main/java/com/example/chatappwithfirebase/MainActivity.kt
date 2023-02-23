package com.example.chatappwithfirebase

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.chatappwithfirebase.data.local.LocalStorage
import com.example.chatappwithfirebase.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.activity_fragment_container
        ) as NavHostFragment
        navController = navHostFragment.navController


        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_new_group -> {
                }
                R.id.nav_contacts -> {
                }
                R.id.nav_calls -> {
                }
                R.id.nav_people_nearby -> {
                }
                R.id.nav_saved_messages -> {
                }
                R.id.nav_settings -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    navController.navigate(
                        R.id.action_groupScreen_to_editScreen
                    )

                }
            }
            true
        }


        val shared = getSharedPreferences("ChatAppSharedPref", Context.MODE_PRIVATE)

        val name = shared.getString("name", "asdasd")
        shared.edit().putString("name","Rasul0702").apply()





    }


}
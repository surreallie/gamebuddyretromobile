package com.mita.gamebuddymobile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.mita.gamebuddymobile.api.ApiService
import com.mita.gamebuddymobile.api.RetrofitClient
import com.mita.gamebuddymobile.api.UserDataClass
import com.mita.gamebuddymobile.databinding.ActivityHomePageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePage : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var recyclerView : RecyclerView
    private lateinit var userList : ArrayList<UserDataClass>
    private lateinit var userAdapter : UserAdapter
    private lateinit var apiService: ApiService // Your Retrofit API service interface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        // Initialize Retrofit API service
        apiService = RetrofitClient.apiService

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    true
                }

                // Handle other menu items if needed
                else -> false
            }
        }
        bottomNavigationView.selectedItemId = R.id.bottom_home

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        userList = ArrayList()

        // Call the function to fetch user data from the server
        fetchUserData()

        userAdapter = UserAdapter(userList)
        recyclerView.adapter = userAdapter
    }

    private fun fetchUserData() {
        apiService.getUser().enqueue(object : Callback<List<UserDataClass>> {
            override fun onResponse(
                call: Call<List<UserDataClass>>,
                response: Response<List<UserDataClass>>
            ) {
                if (response.isSuccessful) {
                    val users = response.body()
                    users?.let {
                        userList.addAll(it)
                        userAdapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(
                        this@HomePage,
                        "Failed to fetch user data from server",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<UserDataClass>>, t: Throwable) {
                Toast.makeText(
                    this@HomePage,
                    "Error occurred while fetching user data",
                    Toast.LENGTH_SHORT
                ).show()
                t.printStackTrace()
            }
        })
    }
}

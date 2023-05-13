package com.example.applicationasi2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.applicationasi2.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    companion object {
        private const val BASE_URL = "https://dummyjson.com/products"
        private const val TAG = "ProductListActivity"
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val URL="https://dummyjson.com/products/categories"
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

        val requestQueue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, URL, null,
            { response ->
                val tabNames = ArrayList<String>()
                try {
                    for (i in 0 until response.length()) {
                        val tabName = response.getString(i)
                        tabNames.add(tabName)
                    }
                    sectionsPagerAdapter.updateTabs(tabNames)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.message.toString()
            }
        )
        requestQueue.add(jsonArrayRequest)

    }
}
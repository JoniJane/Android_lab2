package com.example.androiddemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.androiddemo.adapter.FiguresAdapter
import com.example.androiddemo.databinding.ActivityMainBinding
import com.example.androiddemo.model.Figures
import com.example.androiddemo.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FiguresAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = FiguresAdapter()
        adapter.updateList(allFiguresList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)



        loadHistoricalFigures()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    loadHistoricalFigures()
                } else {
                    loadHistoricalFigures(newText)
                }
                return true
            }
        })


    }
    private var allFiguresList: List<Figures> = emptyList()


    private fun loadHistoricalFigures(searchQuery: String? = null) {
        val service = ApiClient.instance
        val call = service.getHistoricalFigures(searchQuery ?: "")

        call.enqueue(object : Callback<List<Figures>> {
            override fun onResponse(call: Call<List<Figures>>, response: Response<List<Figures>>) {
                if (response.isSuccessful) {
                    Log.d("PersonListFragment", "Success: ${response.body()}")
                    allFiguresList = response.body() ?: emptyList()
                    adapter = FiguresAdapter()
                    adapter.updateList(allFiguresList)
                    binding.recyclerView.adapter = adapter
                }
                else{
                    Log.e("PersonListFragment", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Figures>>, t: Throwable) {
                Log.e("PersonListFragment", "Error: Couldn't do server lol")

            }
        })
    }

}
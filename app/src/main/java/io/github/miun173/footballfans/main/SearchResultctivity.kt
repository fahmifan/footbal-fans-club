package io.github.miun173.footballfans.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.github.miun173.footballfans.R

class SearchResultctivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_resultctivity)
        val query = intent.getStringExtra("query") ?: ""
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show()
    }
}

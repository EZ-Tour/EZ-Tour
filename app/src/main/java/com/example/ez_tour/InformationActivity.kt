package com.example.ez_tour

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class InformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        val view_inimage = findViewById<ImageView>(R.id.view_Inimage)

        val text_inaddress = findViewById<TextView>(R.id.text_inaddress)
        val text_incall = findViewById<TextView>(R.id.text_incall)
        val btn_star = findViewById<ImageButton>(R.id.btn_star)
        val btn_back = findViewById<ImageButton>(R.id.btn_back)
        val btn_search = findViewById<Button>(R.id.btn_search)
        val btn_map = findViewById<Button>(R.id.btn_map)
        val btn_mypage = findViewById<Button>(R.id.btn_mypage)

        btn_back.setOnClickListener {
            finish()
        }

        btn_search.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        // mypage 버튼 클릭리스너
        btn_map.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        btn_mypage.setOnClickListener {
            val intent = Intent(this, MypageActivity::class.java)
            startActivity(intent)
        }
    }
}
package com.example.ez_tour

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kakao.sdk.user.UserApiClient

class InformationActivity : AppCompatActivity() {


    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebaseDatabase.getReference()
    lateinit var data: RecycleData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        data = intent.getSerializableExtra("item") as RecycleData
        val view_inimage = findViewById<ImageView>(R.id.view_Inimage)
        val text_inaddress = findViewById<TextView>(R.id.text_inaddress)
        val text_incall = findViewById<TextView>(R.id.text_incall)
        val btn_star = findViewById<ImageButton>(R.id.btn_star)
        val btn_back = findViewById<ImageButton>(R.id.btn_back)
        val btn_search = findViewById<Button>(R.id.btn_search)
        val btn_map = findViewById<Button>(R.id.btn_map)
        val btn_mypage = findViewById<Button>(R.id.btn_mypage)
        var star: Int = 0
        text_inaddress.setText(
            "${
                databaseReference.child("${data.imageTag}").child("${data.strName}").child("주소")
                    .get().toString()
            }"
        )
        UserApiClient.instance.me { user, error ->
            if (databaseReference.child("사용자").child("${user!!.id}").child("즐겨찾기")
                    .child("${data.strName}").get().toString()
                != null
            ) {
                btn_star.setImageResource(R.drawable.map_image_0004_heart_2)
                star = 0
            } else {
                btn_star.setImageResource(R.drawable.map_image_0003_heart_1)
                star = 1
            }

        }

        btn_star.setOnClickListener {
            UserApiClient.instance.me { user, error ->
                if (star == 1) {
                    btn_star.setImageResource(R.drawable.map_image_0004_heart_2)
                    star = 0
                    databaseReference.child("사용자").child("${user!!.id}").child("즐겨찾기")
                        .child("${data.strName}").removeValue()
                } else {
                    btn_star.setImageResource(R.drawable.map_image_0003_heart_1)
                    star = 1
                    databaseReference.child("사용자").child("${user!!.id}").child("즐겨찾기")
                        .child("${data.strName}").setValue(".")
                }

            }


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
}
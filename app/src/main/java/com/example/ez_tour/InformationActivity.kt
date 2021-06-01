package com.example.ez_tour

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kakao.sdk.user.UserApiClient

class InformationActivity : AppCompatActivity() {


    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebaseDatabase.getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        var tag:String = intent.getStringExtra("tag").replace("[","").replace("]","")
        var name:String = intent.getStringExtra("name").replace("[","").replace("]","")
       // data = intent.getSerializableExtra("item") as RecycleData
       // val view_inimage = findViewById<ImageView>(R.id.view_Inimage)
        val text_inname = findViewById<TextView>(R.id.text_inname)
        val text_inaddress = findViewById<TextView>(R.id.text_inaddress)
        val text_incall = findViewById<TextView>(R.id.text_incall)
        val btn_star = findViewById<ImageButton>(R.id.btn_star)
        val btn_back = findViewById<ImageButton>(R.id.btn_back)
        val btn_search = findViewById<Button>(R.id.btn_search)
        val btn_map = findViewById<Button>(R.id.btn_map)
        val btn_mypage = findViewById<Button>(R.id.btn_mypage)
        val btn_navi = findViewById<ImageButton>(R.id.btn_navi)
        var star: Int = 0
        Log.d("Datatest","${tag}")
        Log.d("Datatest","${name}")

        text_inname.text = name
        databaseReference.child(tag).child(name).child("주소").get().addOnSuccessListener {
            text_inaddress.text = "${it.value}"
        }.addOnFailureListener {
            Log.d("Failtest","실패")
        }

        Log.d("Datatest","${databaseReference.child(tag).child(name).child("주소").get()}")
                   // .get().toString()}"
       UserApiClient.instance.me { user, error ->
            databaseReference.child("사용자").child("${user!!.id}").child("즐겨찾기")
                    .child(name).child("이름").get().addOnSuccessListener {
                   if (it.value == name) {
                       btn_star.setImageResource(R.drawable.map_image_0003_heart_1)
                       star = 1

                   } else{
                       btn_star.setImageResource(R.drawable.map_image_0004_heart_2)
                       star = 0
                   }
                }
        }

        btn_star.setOnClickListener {
            UserApiClient.instance.me { user, error ->
                if (star == 1) {
                    btn_star.setImageResource(R.drawable.map_image_0004_heart_2)
                    star = 0
                    databaseReference.child("사용자").child("${user!!.id}").child("즐겨찾기")
                        .child(name).removeValue()

                    Toast.makeText(this, "즐겨찾기에서 삭제하였습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    btn_star.setImageResource(R.drawable.map_image_0003_heart_1)
                    star = 1
                    databaseReference.child("사용자").child("${user!!.id}").child("즐겨찾기")
                        .child(name).child("이름").setValue(name)
                    databaseReference.child("사용자").child("${user!!.id}").child("즐겨찾기")
                        .child(name).child("태그").setValue(tag)

                    Toast.makeText(this, "즐겨찾기에 추가하였습니다.", Toast.LENGTH_SHORT).show()
                }

            }


        }

        btn_navi.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
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
package com.example.ez_tour

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kakao.sdk.user.UserApiClient
import java.net.URL

class MypageActivity : AppCompatActivity() {

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebaseDatabase.getReference()

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        val btn_map = findViewById<Button>(R.id.btn_map)
        val btn_search = findViewById<Button>(R.id.btn_search)
        val btn_appinfo = findViewById<Button>(R.id.btn_appinfo) as ImageButton

        var text_nickname = findViewById<TextView>(R.id.text_nickname)
        var view_profile = findViewById<ImageView>(R.id.view_profile)

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Toast.makeText(this, "사용자 정보 요청 실패", Toast.LENGTH_SHORT).show()
            } else if (user != null) {
                text_nickname.setText("${user.kakaoAccount?.profile?.nickname}"+" "+"님")
                var image_task: URLtoBitmapTask = URLtoBitmapTask()
                image_task = URLtoBitmapTask().apply {
                    url = URL("${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                }
                var bitmap: Bitmap = image_task.execute().get()
                view_profile.setImageBitmap(bitmap)
            }
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

        btn_appinfo.setOnClickListener {
            val intent = Intent(this, AppinfoActivity::class.java)
            startActivity(intent)
        }

    }


}


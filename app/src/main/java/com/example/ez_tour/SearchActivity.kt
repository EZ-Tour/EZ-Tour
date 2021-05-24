package com.example.ez_tour

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SearchActivity : AppCompatActivity() {

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebaseDatabase.getReference()
    val list = ArrayList<RecycleData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


       // val mRecyclerView = findViewById<RecyclerView>(R.id.review)


      /*  databaseReference.child("카페").addValueEventListener(object :ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot != null) {
                    dataSnapshot.children.forEach { i ->
                        Log.d("MainActivity", "Single ValueEventListener : " + i.getValue());
                        var image_task: URLtoBitmapTask = URLtoBitmapTask()
                        image_task = URLtoBitmapTask().apply {
                            url = URL("${i.child("이미지 URL").getValue()}")
                        }
                        var bitmap: Bitmap = image_task.execute().get()
                        var count:Int = 0
                        list.add(count,RecycleData(bitmap, i.child("이름").getValue() as String,
                            i.child("태그").getValue() as String))
                        count++
                        Log.d("MainActivity", "Count:" + count);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                }
                val adapter = RecyclerAdapter(list)
                review.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        Log.d("Listtest", list.toString()) */






        val btn_map = findViewById<Button>(R.id.btn_map)
        val btn_search = findViewById<Button>(R.id.btn_search)
        val btn_mypage = findViewById<Button>(R.id.btn_mypage)

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
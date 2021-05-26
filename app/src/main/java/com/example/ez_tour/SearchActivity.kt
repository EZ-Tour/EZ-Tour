package com.example.ez_tour

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*
import kotlin.collections.ArrayList

val nameData = ArrayList<String>()
val tagData = ArrayList<String>()
class SearchActivity : AppCompatActivity() {

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebaseDatabase.getReference()
    val list = ArrayList<RecycleData>()
    var count:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


       val mRecyclerView = findViewById<RecyclerView>(R.id.review)


      databaseReference.child("카페").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot != null) {
                    dataSnapshot.children.forEach { i ->
                        Log.d("MainActivity", "Single ValueEventListener : " + i.getValue());
                        //var image_task: URLtoBitmapTask = URLtoBitmapTask()
                        //image_task = URLtoBitmapTask().apply {
                           // url = URL("${i.child("이미지 URL").getValue()}")
                       // }
                        //var bitmap: Bitmap = image_task.execute().get()
                        nameData.add("${i.child("이름").getValue()}")
                        tagData.add("${i.child("태그").getValue()}")
                        list.add(RecycleData("${i.child("이름").getValue()}", "${i.child("태그").getValue()}"))
                        count++
                        Log.d("MainActivity", "Count:" + count);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                }
                Log.d("Listtest", "${ Arrays.deepToString(arrayOf(list))}")
                val adapter = RecyclerAdapter(list)
                review.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
      databaseReference.child("관광명소").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot != null) {
                    dataSnapshot.children.forEach { i ->
                        Log.d("MainActivity", "Single ValueEventListener : " + i.getValue());
                        //var image_task: URLtoBitmapTask = URLtoBitmapTask()
                        //image_task = URLtoBitmapTask().apply {
                        // url = URL("${i.child("이미지 URL").getValue()}")
                        // }
                        //var bitmap: Bitmap = image_task.execute().get()
                        list.add(count,RecycleData(i.child("이름").getValue() as String,
                            i.child("태그").getValue() as String))
                        count++
                        Log.d("MainActivity", "Count:" + count);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                }
                Log.d("Listtest3", list.toString())
                val adapter = RecyclerAdapter(list)
                review.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
       databaseReference.child("숙소").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot != null) {
                    dataSnapshot.children.forEach { i ->
                        Log.d("MainActivity", "Single ValueEventListener : " + i.getValue());
                        //var image_task: URLtoBitmapTask = URLtoBitmapTask()
                        //image_task = URLtoBitmapTask().apply {
                        // url = URL("${i.child("이미지 URL").getValue()}")
                        // }
                        //var bitmap: Bitmap = image_task.execute().get()
                        list.add(count,RecycleData(i.child("이름").getValue() as String,
                            i.child("태그").getValue() as String))
                        count++
                        Log.d("MainActivity", "Count:" + count);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                }
                Log.d("Listtest", list.toString())
                val adapter = RecyclerAdapter(list)
                review.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        databaseReference.child("음식점").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot != null) {
                    dataSnapshot.children.forEach { i ->
                        Log.d("MainActivity", "Single ValueEventListener : " + i.getValue());
                        //var image_task: URLtoBitmapTask = URLtoBitmapTask()
                        //image_task = URLtoBitmapTask().apply {
                        // url = URL("${i.child("이미지 URL").getValue()}")
                        // }
                        //var bitmap: Bitmap = image_task.execute().get()
                        list.add(RecycleData(i.child("이름").getValue() as String,
                            i.child("태그").getValue() as String))
                        count++
                        Log.d("MainActivity", "Count:" + count);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                }
                Log.d("Listtest", list.toString())
                val adapter = RecyclerAdapter(list)
                review.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        Log.d("Listtest2", list.toString())
        val adapter = RecyclerAdapter(list)
        review.adapter = adapter





        val btn_map = findViewById<Button>(R.id.btn_map)
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
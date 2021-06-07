package com.example.ez_tour

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_search.*
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


class SearchActivity : AppCompatActivity() ,TextWatcher {

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebaseDatabase.getReference()
    val list = ArrayList<RecycleData>()
    var count:Int = 0
    val nameData = ArrayList<String>()
    val tagData = ArrayList<String>()
    val adapter = RecyclerAdapter(list,this@SearchActivity)
    val slist = ArrayList<RecycleData>()
    val spadapter = RecyclerAdapter(slist,this@SearchActivity)
    var page: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val editText = findViewById<EditText>(R.id.searcBar)
        val mRecyclerView = findViewById<RecyclerView>(R.id.review)
        var sResult = String()
        fun calldata(category: String) {
            databaseReference.child(category).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot != null) {
                        dataSnapshot.children.forEach { i ->
                            Log.d("MainActivity", "Single ValueEventListener : " + i.getValue());
                            var image_task: URLtoBitmapTask = URLtoBitmapTask()
                            image_task = URLtoBitmapTask().apply {
                                try {
                                    if(i.child("이미지URL").getValue() != "NULL"){
                                        url = URL("${i.child("이미지URL").getValue()}")
                                    } else {
                                        url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                                    }
                                }catch (e: Exception){
                                    url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                                }

                            }
                            var bitmap: Bitmap = image_task.execute().get()
                            nameData.add("${i.child("이름").getValue()}")
                            tagData.add("${i.child("태그").getValue()}")
                            list.add(RecycleData(count,"${i.child("이름").getValue()}", "${i.child("태그").getValue()}",bitmap))
                            count++
                            Log.d("MainActivity", "Count:" + count)
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                    }
                    Log.d("Listtest1", list.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
            mRecyclerView.adapter = adapter
        }
       /* databaseReference.child("카페").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot != null) {
                    dataSnapshot.children.forEach { i ->
                        Log.d("MainActivity", "Single ValueEventListener : " + i.getValue());
                        var image_task: URLtoBitmapTask = URLtoBitmapTask()
                        image_task = URLtoBitmapTask().apply {
                            try {
                                if(i.child("이미지URL").getValue() != "NULL"){
                                    url = URL("${i.child("이미지URL").getValue()}")
                                } else {
                                    url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                                }
                            }catch (e: Exception){
                                url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                            }

                        }
                        var bitmap: Bitmap = image_task.execute().get()
                        nameData.add("${i.child("이름").getValue()}")
                        tagData.add("${i.child("태그").getValue()}")
                        list.add(RecycleData(count,"${i.child("이름").getValue()}", "${i.child("태그").getValue()}",bitmap))
                        count++
                        Log.d("MainActivity", "Count:" + count)
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                }
                Log.d("Listtest1", list.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }) */
        calldata("카페")
        /*databaseReference.child("관광명소").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot != null) {
                    dataSnapshot.children.forEach { i ->
                        Log.d("MainActivity", "Single ValueEventListener : " + i.getValue());
                        //var image_task: URLtoBitmapTask = URLtoBitmapTask()
                        //image_task = URLtoBitmapTask().apply {
                         //   url = URL("${i.child("이미지URL").getValue()}")
                      //  }
                        //    var bitmap: Bitmap = image_task.execute().get()
                            list.add(RecycleData(count,i.child("이름").getValue() as String,
                                i.child("태그").getValue() as String,null))
                            count++
                            Log.d("MainActivity", "Count:" + count);
                        }
                    } else {
                    Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                }
                Log.d("Listtest2", list.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })*/

       /* databaseReference.child("숙소").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot != null) {
                    dataSnapshot.children.forEach { i ->
                        Log.d("MainActivity", "Single ValueEventListener : " + i.getValue());
                        var image_task: URLtoBitmapTask = URLtoBitmapTask()
                        image_task = URLtoBitmapTask().apply {
                            try {
                                if(i.child("이미지URL").getValue() != "NULL"){
                                    url = URL("${i.child("이미지URL").getValue()}")
                                } else {
                                    url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                                }
                            }catch (e: Exception){
                                url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                            }

                        }
                        var bitmap: Bitmap = image_task.execute().get()
                        list.add(RecycleData(count,i.child("이름").getValue() as String,
                            i.child("태그").getValue() as String,bitmap))
                        count++
                        Log.d("MainActivity", "Count:" + count);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                }
                Log.d("Listtest3", list.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })*/
        calldata("숙소")

       /* databaseReference.child("음식점").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot != null) {
                    dataSnapshot.children.forEach { i ->
                        Log.d("MainActivity", "Single ValueEventListener : " + i.getValue());
                        var image_task: URLtoBitmapTask = URLtoBitmapTask()
                        image_task = URLtoBitmapTask().apply {
                            try {
                                if(i.child("이미지URL").getValue() != "NULL"){
                                    url = URL("${i.child("이미지URL").getValue()}")
                                } else {
                                    url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                                }
                            }catch (e: Exception){
                                url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                            }

                        }
                        var bitmap: Bitmap = image_task.execute().get()
                        list.add(RecycleData(count,i.child("이름").getValue() as String,
                            i.child("태그").getValue() as String,bitmap))
                        count++
                        Log.d("MainActivity", "Count:" + count);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                }
                Log.d("Listtest4", list.toString())
                mRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }) */
        calldata("음식점")
        calldata("상점")
        calldata("일반 충전소")

        Log.d("Listtest2", list.toString())
        mRecyclerView.adapter = adapter
        mRecyclerView.setHasFixedSize(true)

        editText.addTextChangedListener(this)
        var sData = resources.getStringArray(R.array.spinnerdata)
        var sadapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sData)
        spinner.adapter = sadapter
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sResult = sData.get(position).toString()
                if (sResult.equals("전체")){
                    try {
                        mRecyclerView.adapter = adapter
                        page = 0
                    } catch (e: Exception) {
                    }
                }else if (sResult.equals("일반 충전소")) {
                    databaseReference.child("일반 충전소").addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if(dataSnapshot != null) {
                                dataSnapshot.children.forEach { i ->
                                    Log.d("MainActivity", "Single ValueEventListener : " + i.getValue());
                                    var image_task: URLtoBitmapTask = URLtoBitmapTask()
                                    image_task = URLtoBitmapTask().apply {
                                        try {
                                            if(i.child("이미지URL").getValue() != "NULL"){
                                                url = URL("${i.child("이미지URL").getValue()}")
                                            } else {
                                                url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                                            }
                                        }catch (e: Exception){
                                            url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                                        }

                                    }
                                    var bitmap: Bitmap = image_task.execute().get()
                                    nameData.add("${i.child("이름").getValue()}")
                                    tagData.add("${i.child("태그").getValue()}")
                                    slist.add(RecycleData(count,"${i.child("이름").getValue()}", "${i.child("태그").getValue()}",bitmap))
                                    count++
                                    Log.d("MainActivity", "Count:" + count)
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                            }
                            Log.d("Listtest1", "${ Arrays.deepToString(arrayOf(list))}")
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
                    try {
                        mRecyclerView.adapter = spadapter
                        page = 1
                    } catch (e: Exception) {
                    }
                }else if (sResult.equals("상점")){
                    databaseReference.child("상점").addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if(dataSnapshot != null) {
                                dataSnapshot.children.forEach { i ->
                                    Log.d("MainActivity", "Single ValueEventListener : " + i.getValue());
                                    var image_task: URLtoBitmapTask = URLtoBitmapTask()
                                    image_task = URLtoBitmapTask().apply {
                                        try {
                                            if(i.child("이미지URL").getValue() != "NULL"){
                                                url = URL("${i.child("이미지URL").getValue()}")
                                            } else {
                                                url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                                            }
                                        }catch (e: Exception){
                                            url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                                        }

                                    }
                                    var bitmap: Bitmap = image_task.execute().get()
                                    nameData.add("${i.child("이름").getValue()}")
                                    tagData.add("${i.child("태그").getValue()}")
                                    slist.add(RecycleData(count,"${i.child("이름").getValue()}", "${i.child("태그").getValue()}",bitmap))
                                    count++
                                    Log.d("MainActivity", "Count:" + count)
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                            }
                            Log.d("Listtest1", "${ Arrays.deepToString(arrayOf(list))}")
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
                    try {
                        mRecyclerView.adapter = spadapter
                        page = 1
                    } catch (e: Exception) {
                    }
                }else if (sResult.equals("음식점")){
                    databaseReference.child("음식점").addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if(dataSnapshot != null) {
                                dataSnapshot.children.forEach { i ->
                                    Log.d("MainActivity", "Single ValueEventListener : " + i.getValue());
                                    var image_task: URLtoBitmapTask = URLtoBitmapTask()
                                    image_task = URLtoBitmapTask().apply {
                                        try {
                                            if(i.child("이미지URL").getValue() != "NULL"){
                                                url = URL("${i.child("이미지URL").getValue()}")
                                            } else {
                                                url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                                            }
                                        }catch (e: Exception){
                                            url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                                        }

                                    }
                                    var bitmap: Bitmap = image_task.execute().get()
                                    nameData.add("${i.child("이름").getValue()}")
                                    tagData.add("${i.child("태그").getValue()}")
                                    slist.add(RecycleData(count,"${i.child("이름").getValue()}", "${i.child("태그").getValue()}",bitmap))
                                    count++
                                    Log.d("MainActivity", "Count:" + count)
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                            }
                            Log.d("Listtest1", "${ Arrays.deepToString(arrayOf(list))}")
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
                    try {
                        mRecyclerView.adapter = spadapter
                        page = 1
                    } catch (e: Exception) {
                    }
                }else if (sResult.equals("숙소")){
                    databaseReference.child("숙소").addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if(dataSnapshot != null) {
                                dataSnapshot.children.forEach { i ->
                                    Log.d("MainActivity", "Single ValueEventListener : " + i.getValue());
                                    var image_task: URLtoBitmapTask = URLtoBitmapTask()
                                    image_task = URLtoBitmapTask().apply {
                                        try {
                                            if(i.child("이미지URL").getValue() != "NULL"){
                                                url = URL("${i.child("이미지URL").getValue()}")
                                            } else {
                                                url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                                            }
                                        }catch (e: Exception){
                                            url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                                        }

                                    }
                                    var bitmap: Bitmap = image_task.execute().get()
                                    nameData.add("${i.child("이름").getValue()}")
                                    tagData.add("${i.child("태그").getValue()}")
                                    slist.add(RecycleData(count,"${i.child("이름").getValue()}", "${i.child("태그").getValue()}",bitmap))
                                    count++
                                    Log.d("MainActivity", "Count:" + count)
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                            }
                            Log.d("Listtest1", "${ Arrays.deepToString(arrayOf(list))}")
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
                    try {
                        mRecyclerView.adapter = spadapter
                        page = 1
                    } catch (e: Exception) {
                    }

                }else if (sResult.equals("카페")){
                    databaseReference.child("카페").addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if(dataSnapshot != null) {
                                dataSnapshot.children.forEach { i ->
                                    Log.d("MainActivity", "Single ValueEventListener : " + i.getValue());
                                    var image_task: URLtoBitmapTask = URLtoBitmapTask()
                                    image_task = URLtoBitmapTask().apply {
                                        try {
                                            if(i.child("이미지URL").getValue() != "NULL"){
                                                url = URL("${i.child("이미지URL").getValue()}")
                                            } else {
                                                url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                                            }
                                        }catch (e: Exception){
                                            url = URL("https://artsmidnorthcoast.com/wp-content/uploads/2014/05/no-image-available-icon-6.png")
                                        }

                                    }
                                    var bitmap: Bitmap = image_task.execute().get()
                                    nameData.add("${i.child("이름").getValue()}")
                                    tagData.add("${i.child("태그").getValue()}")
                                    slist.add(RecycleData(count,"${i.child("이름").getValue()}", "${i.child("태그").getValue()}",bitmap))
                                    count++
                                    Log.d("MainActivity", "Count:" + count)
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                            }
                            Log.d("Listtest1", "${ Arrays.deepToString(arrayOf(list))}")
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
                    try {
                        mRecyclerView.adapter = spadapter
                        page = 1
                    } catch (e: Exception) {
                    }
                }else {

                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

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

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if(page == 0) {
        adapter.getFilter().filter(s.toString())
    }else{
        spadapter.getFilter().filter(s.toString())
        }
    }

    override fun afterTextChanged(s: Editable?) {}

}
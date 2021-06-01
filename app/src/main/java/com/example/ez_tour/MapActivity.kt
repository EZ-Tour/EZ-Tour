package com.example.ez_tour

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.database.*
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.lang.Math.*
import kotlin.math.pow

val NameData = ArrayList<String>()
val LongitudeData = ArrayList<Double>()
val LatitudeData = ArrayList<Double>()
val AddressData = ArrayList<String>()

class MapActivity : AppCompatActivity(), MapView.POIItemEventListener {

    val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION)
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebaseDatabase.getReference()
    var uLatitude:Double = 0.0
    var uLongitude:Double = 0.0
    private val Q = 6372.8 * 1000
    var n:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        //kakao map 띄우기
        val mapView = MapView(this)
        val map_view = findViewById<View>(R.id.map_View) as RelativeLayout
        val mapViewContainer = map_view
        mapViewContainer.addView(mapView)
        //커스텀 말풍선 어댑터
        mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater,MapPOIItem()))
        mapView.setPOIItemEventListener(this)
        val permissionCheck = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val lm: LocationManager =
                getSystemService(Context.LOCATION_SERVICE) as LocationManager
            try {
                val userNowLocation: Location =
                    lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
                uLatitude = userNowLocation.latitude
                uLongitude = userNowLocation.longitude
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude), true)

            } catch (e: NullPointerException) {
                Log.e("LOCATION_ERROR", e.toString())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.finishAffinity(this)
                } else {
                    ActivityCompat.finishAffinity(this)
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                System.exit(0)
            }

        } else {
            Toast.makeText(this, "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                PERMISSIONS_REQUEST_CODE
            )
        }

        //Help 이미지 초기화
        val img_hlep = findViewById<View>(R.id.img_hlep) as ImageView
        img_hlep.visibility = View.INVISIBLE
        //Help 버튼 초기화
        val btn_help = findViewById<Button>(R.id.btn_help) as ImageButton
        var counter = 0
        // GPS 마커 초기화.
        val gpsmarker = MapPOIItem()
        val btn_gps = findViewById<Button>(R.id.btn_gps) as ImageButton
        // 하단 버튼 초기화
        val btn_search = findViewById<Button>(R.id.btn_search)
        val btn_mypage = findViewById<Button>(R.id.btn_mypage)

        // search 버튼 클릭리스너
        btn_search.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        // mypage 버튼 클릭리스너
        btn_mypage.setOnClickListener {
            val intent = Intent(this, MypageActivity::class.java)
            startActivity(intent)
        }

        //Help 버튼 클릭리스너
        btn_help.setOnClickListener {
            if (counter == 0) {
                counter++
                btn_help.setImageResource(R.drawable.kakao_login_large_narrow)
                   img_hlep.visibility = View.VISIBLE
            } else {
                counter--
                btn_help.setImageResource(R.drawable.common_google_signin_btn_icon_dark)
                img_hlep.visibility = View.INVISIBLE
            }
        }
        // GPS 버튼 클릭리스너  (현재위치 가져오기)
        btn_gps.setOnClickListener {
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                val lm: LocationManager =
                    getSystemService(Context.LOCATION_SERVICE) as LocationManager
                try {
                    val userNowLocation: Location =
                        lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
                    uLatitude = userNowLocation.latitude
                    uLongitude = userNowLocation.longitude


                } catch (e: NullPointerException) {
                    Log.e("LOCATION_ERROR", e.toString())
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ActivityCompat.finishAffinity(this)
                    } else {
                        ActivityCompat.finishAffinity(this)
                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    System.exit(0)
                }

            } else {
                Toast.makeText(this, "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            }
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude), true)
            gpsmarker.mapPoint = MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude)
            gpsmarker.itemName= "현재위치"
            gpsmarker.tag = 0
            gpsmarker.markerType = MapPOIItem.MarkerType.BluePin
            gpsmarker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
            mapView.addPOIItem(gpsmarker)
        }
        // 관광 명소 마커 찍기
        databaseReference.child("관광명소").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot != null) {
                    dataSnapshot.children.forEach { i ->
                        NameData.add("${i.child("이름").getValue()}")
                        AddressData.add("${i.child("주소").getValue()}")
                        LongitudeData.add("${i.child("좌표").child("경도").getValue()}".toDouble())
                        LatitudeData.add("${i.child("좌표").child("위도").getValue()}".toDouble())
                        Log.d("MainActivity", "Single Value: " + LongitudeData[n] )
                        var marker = MapPOIItem()
                        marker.mapPoint= MapPoint.mapPointWithGeoCoord(LongitudeData[n], LatitudeData[n])
                        marker.itemName= NameData[n]
                        marker.tag = n
                        marker.markerType = MapPOIItem.MarkerType.BluePin
                        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
                        mapView.addPOIItem(marker)
                        n++
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }

    class CustomBalloonAdapter(inflater: LayoutInflater,poiItem: MapPOIItem?): CalloutBalloonAdapter {
        val mCalloutBalloon: View = inflater.inflate(R.layout.customballoon, null)
        val name: TextView = mCalloutBalloon.findViewById(R.id.text_name)
        val address: TextView = mCalloutBalloon.findViewById(R.id.text_address)
        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            name.text= NameData[poiItem!!.tag]
            address.text = AddressData[poiItem!!.tag]

            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
            // 말풍선 클릭 시
            return mCalloutBalloon
        }
    }

    override fun onPOIItemSelected(mapView: MapView, mapPOIItem: MapPOIItem) {
        val markerName:String = NameData[mapPOIItem!!.tag]
        var latA:Double = LatitudeData[mapPOIItem!!.tag]
        var lonA:Double = LongitudeData[mapPOIItem!!.tag]
        Log.d("markerName",markerName)
        databaseReference.child("카페").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot != null) {
                    dataSnapshot.children.forEach { i ->
                        Log.d("MainActivity", "Single Value: " + i.child("이름").getValue())
                        NameData.add("${i.child("이름").getValue()}")
                        LongitudeData.add("${i.child("좌표").child("경도").getValue()}".toDouble())
                        LatitudeData.add("${i.child("좌표").child("위도").getValue()}".toDouble())
                        Log.d("MainActivity", "Single Value: " + LongitudeData[n] )

                        if (getDistance(latA,lonA, LatitudeData[n],LongitudeData[n]) <= 500000){
                            var marker = MapPOIItem()
                            marker.mapPoint= MapPoint.mapPointWithGeoCoord(LongitudeData[n], LatitudeData[n])
                            marker.itemName= NameData[n]
                            marker.tag = n
                            marker.markerType = MapPOIItem.MarkerType.BluePin
                            marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
                            mapView.addPOIItem(marker)
                            Log.d("MainActivity", "if문 확인" + getDistance(latA,lonA, LatitudeData[n],LongitudeData[n]) )
                        }else{
                            Log.d("MainActivity", "거리안에 없음"  )
                        }
                        n++
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }





        })

    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {

    }

    override fun onCalloutBalloonOfPOIItemTouched(
        mapView: MapView?,
        poiItem: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {
        val builder = AlertDialog.Builder(this)
        val itemList = arrayOf("확인", "취소")
        builder.setTitle("인터넷에서 검색하시겠습니까?")
        builder.setItems(itemList) { dialog, which ->
            when(which) {
                0 -> {
                    val address = "https://search.daum.net/search?w=tot&DA=YZR&t__nil_searchbox=btn&sug=&sugo=&sq=&o=&q="+"${poiItem?.itemName}"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(address))
                    startActivity(intent)
                }  // 토스트
                1 -> dialog.dismiss()    // 마커 삭제
            }
        }
        builder.show()
    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
        TODO("Not yet implemented")
    }

    fun getDistance(lat1:Double,lon1:Double,lat2:Double,lon2:Double): Int {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2.0) + sin(dLon / 2).pow(2.0) * cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2))
        val c = 2 * asin(sqrt(a))
        return (Q *c).toInt()
    }
}


package com.example.wsmodule2

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wsmodule2.CallBacks.OnBankomatGetListener
import com.example.wsmodule2.POJO.Bankomat
import com.example.wsmodule2.tasks.GetBankomatsTask
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mapbox.mapboxsdk.Mapbox
import com.setContentView
import kotlinx.android.synthetic.main.activity_bankomats.*
import kotlinx.android.synthetic.main.bankomat_layout.*
import java.text.SimpleDateFormat
import java.util.*

class BankomatsActivity : AppCompatActivity(), OnBankomatGetListener {
    var bankomats: MutableList<Bankomat> = mutableListOf<Bankomat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bankomats)
        mapView.onCreate(savedInstanceState)

        Mapbox.getInstance(applicationContext, )

        GetBankomatsTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onBankomatGet(bankomats: MutableList<Bankomat>) {
        for (bankomat in bankomats) {
            val params = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 120
            )
            params.topMargin = 30

            val relativeLayout: RelativeLayout = RelativeLayout(applicationContext)
            relativeLayout.setContentView(R.layout.bankomat_layout)
            relativeLayout.layoutParams = params

            bankomat_place.text = bankomat.place

            bankomat_type.text = bankomat.type

            if (bankomat.is_working) {
                bankomat_is_working.text = "Работает"
                bankomat_is_working.setTextColor(resources.getColor(R.color.colorOpen))
            } else {
                bankomat_is_working.text = "Закрыто"
                bankomat_is_working.setTextColor(resources.getColor(R.color.colorClosed))
            }

            val dateFormat = SimpleDateFormat("hh:mm")
            val dateFrom = dateFormat.format(bankomat.work_from)
            val dateTo = dateFormat.format(bankomat.work_to)

            bankomat_work_time.text = "$dateFrom-$dateTo"
            bankomats_container.addView(relativeLayout)
            bankomats.add(bankomat)
        }

    }

}
package com.example.wsmodule2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wsmodule2.CallBacks.OnBankomatGetListener;
import com.example.wsmodule2.POJO.Bankomat;
import com.example.wsmodule2.tasks.GetBankomatsTask;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class BankomatsActivity extends AppCompatActivity implements OnMapReadyCallback , OnBankomatGetListener {

    GoogleMap googleMap;
    List<Bankomat> bankomats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankomats);

        new GetBankomatsTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,this);

    }

    @Override
    public void onBankomatGet(List<Bankomat> bankomats) {
        LinearLayout linearLayout = findViewById(R.id.bankomats_container);
        LayoutInflater inflater = this.getLayoutInflater();

        for (Bankomat bankomat : bankomats) {

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 120
            );

            params.topMargin = 30;

            RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.bankomat_layout, null, false);
            relativeLayout.setLayoutParams(params);

            TextView place = relativeLayout.findViewById(R.id.bankomat_place);
            place.setText(bankomat.place);

            TextView type = relativeLayout.findViewById(R.id.bankomat_type);
            type.setText(bankomat.type);

            TextView isWorking = relativeLayout.findViewById(R.id.bankomat_is_working);
            if (bankomat.is_working) {
                isWorking.setText("Работает");
                isWorking.setTextColor(getResources().getColor(R.color.colorOpen));
            } else {
                isWorking.setText("Закрыто");
                isWorking.setTextColor(getResources().getColor(R.color.colorClosed));
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
            String dateFrom = dateFormat.format(bankomat.work_from);
            String dateTo = dateFormat.format(bankomat.work_to);

            TextView hoursView = relativeLayout.findViewById(R.id.bankomat_work_time);
            hoursView.setText(dateFrom + "-" + dateTo);

            linearLayout.addView(relativeLayout);

            bankomats.add(bankomat);
        }

        try {
            SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView));
            mapFragment.getMapAsync(this);
        } catch (Exception exception){
            Log.e("ACTIVITY", exception.toString());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        for(Bankomat bankomat : bankomats){
            LatLng latLng = new LatLng(bankomat.latitude, bankomat.longtitude);

            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Адрес: " + bankomat.place
                            + "\nТип: " + bankomat.type
                            + "\nВремя работы: " + dateFormat.format(bankomat.work_from)+"-"+dateFormat.format(bankomat.work_to)
                            + "\nСостояние: " + (bankomat.is_working? "Работает": "Закрыто")));

        }

    }
}

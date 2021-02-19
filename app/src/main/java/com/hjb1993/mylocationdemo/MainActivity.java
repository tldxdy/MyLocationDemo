package com.hjb1993.mylocationdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.hjb1993.location.BaiDuLocation;
import com.hjb1993.location.GaoDeLocation;
import com.hjb1993.location.LocationCallBackListener;
import com.hjb1993.location.LocationHelper;
import com.hjb1993.location.LocationModel;

public class MainActivity extends AppCompatActivity {


    TextView tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvName = findViewById(R.id.tv_name);

        mLocationHelperr.startLocation();
    }

    private LocationModel locationModel;
    LocationHelper mLocationHelperr = new LocationHelper(this, new GaoDeLocation(), new LocationCallBackListener() {
        @Override
        public void locationSuccessful(LocationModel location) {
            locationModel = location;
            tvName.setText(locationModel.getCity());

        }

        @Override
        public void locationFailure(String msg) {
            Log.e("aaaaaa",msg);
        }

        @Override
        public void noPermissions() {
            Log.e("aaaaaa","没有定位权限");
        }
    });





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mLocationHelperr != null) {
            mLocationHelperr.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

package com.hjb1993.location;


import android.util.Log;

import androidx.annotation.NonNull;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class BaiDuLocation implements LocationInterface {
    public LocationClient mLocationClient = null;

    @Override
    public void init(final LocationCallBackListener listener) {
        mLocationClient = new LocationClient(AppUtils.getApp().getApplicationContext());
        mLocationClient.setLocOption(getLocationClientOption());
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        //声明LocationClient类
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {

                if (location == null) {
                    return;
                }
                int errorCode = location.getLocType();
                //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
                Log.i("location", errorCode + "");
                if (errorCode == 61 || errorCode == 161) {
                    listener.locationSuccessful(transitionModel(location));
                } else {
                    listener.locationFailure("定位失败");
                }
                stopLocation();
            }
        });
    }

    @NonNull
    private LocationClientOption getLocationClientOption() {
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，设置定位模式，默认高精度
//LocationMode.Hight_Accuracy：高精度；
//LocationMode. Battery_Saving：低功耗；
//LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
//可选，设置返回经纬度坐标类型，默认gcj02
//gcj02：国测局坐标；
//bd09ll：百度经纬度坐标；
//bd09：百度墨卡托坐标；
//海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(5000);
//可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
//可选，定位SDK内部是一个service，并放到了独立进程。
//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setWifiCacheTimeOut(5 * 60 * 1000);
//可选，7.2版本新增能力
//如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);

        option.setIsNeedAddress(true);

//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        return option;
    }

    @Override
    public void startLocation() {
        mLocationClient.start();
    }

    @Override
    public void stopLocation() {
        mLocationClient.stop();
    }

    @Override
    public void destroyLocation() {
        mLocationClient.stop();
        mLocationClient = null;
    }

    /**
     * 把百度定位model转换为 自用model
     *
     * @return
     */
    private LocationModel transitionModel(BDLocation bdLocation) {
        LocationModel locationModel = new LocationModel();
        locationModel.setCity(bdLocation.getCity());
        locationModel.setProvince(bdLocation.getProvince());
        locationModel.setDistrict(bdLocation.getDistrict());
        locationModel.setLatitude(bdLocation.getLatitude() + "");
        locationModel.setLongitude(bdLocation.getLongitude() + "");
        return locationModel;
    }
}
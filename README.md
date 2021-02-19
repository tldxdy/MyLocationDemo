# MyLocationDemo
地图工具类
引入：
implementation 'com.github.tldxdy:MyLocationDemo:1.0-tool'

高德需要：
implementation 'com.amap.api:location:5.2.0'

在application标签中加入：
"<meta-data android:name="com.amap.api.v2.apikey" android:value="key">//开发者申请的key

</meta-data>"


使用方法：

    百度定位：new BaiDuLocation()
    高德定位：new GaoDeLocation()

    LocationHelper mLocationHelperr = new LocationHelper(this, new GaoDeLocation(), new LocationCallBackListener() {
        @Override
        public void locationSuccessful(LocationModel location) {
           //获取定位
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



    启用方法：
     mLocationHelperr.startLocation();

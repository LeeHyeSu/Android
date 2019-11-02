// 과제03
// 작성일: 2018. 11. 14
// 작성자: 01분반 20160989 이혜수

package lecture.mobile.map.test.report03_01_20160989;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public final static String TAG = "POITest";
    public final static int DEFAULT_ZOOM_LEVEL = 17;

    private String urlString;
    private EditText etKeyword;

    private FakeParser parser;
    private Geocoder geoCoder;
    private GoogleMap googleMap;

    private MarkerOptions centerMarkerOptions;
    private Marker centerMarker;

    private MarkerOptions poiMarkerOptions;
    private ArrayList<Marker> markerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etKeyword = (EditText)findViewById(R.id.etKeyword);

        geoCoder = new Geocoder(this);
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(mapReadyCallBack);

        /*OpenAPI Fake Parser*/
        parser = new FakeParser();
    }

    /* 버튼을 누르면 가상의 위치 정보를 지도에 마커를 사용하여 표시하고 기준 위치로 이동*/
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSearch:
                String url = "fake url";
                new NetworkAsyncTask().execute(url);
                break;
        }
    }


    OnMapReadyCallback mapReadyCallBack = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap map) {
            googleMap = map;

            /* 마커 클릭 시 마커 윈도우 표시 */
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    marker.showInfoWindow();
                    return false;
                }
            });

            /* 마커 윈도우 클릭 시 주소를 Toast로 출력 */
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Toast.makeText(MainActivity.this, marker.getTitle(), Toast.LENGTH_LONG).show();
                }
            });

            /* 지도를 롱클릭할 경우 새로운 마커 추가 */
            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    List<Address> addressList = null;
                    try {
                        addressList = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 5);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(addressList != null) {
                        poiMarkerOptions = new MarkerOptions();
                        poiMarkerOptions.position(new LatLng(latLng.latitude, latLng.longitude))
                                .title(addressList.get(0).getAddressLine(0))
                                .snippet(addressList.get(0).getPostalCode())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                        googleMap.addMarker(poiMarkerOptions);
                    }
                }
            });
        }
    };




    /*실제 앱을 구현할 때는 네트워크 AsyncTask로 구현할 것*/
    class NetworkAsyncTask extends AsyncTask<String, Integer, String> {

        public final static String TAG = "NetworkAsyncTask";
        private ProgressDialog apiProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            apiProgressDialog = ProgressDialog.show(MainActivity.this, "Wait", "Downloading...");
        }

        @Override
        protected String doInBackground(String... strings) {

            /*NetworkAsyncTask 는 네트워크 작업을 실제 실행하지는 않으며 잠시 시간 대기만 수행*/
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Open API search is completed";
        }

        @Override
        protected void onPostExecute(String result) {

            /*작업 수행 후 가상 parsing 수행*/
            ArrayList<POI> poiList = parser.parse(result);
            apiProgressDialog.dismiss();

            /* poiList에 있는 가상의 위치 정보를 가져와서 마커 생성 */
            if(poiList != null) {
                List<Address> addressList = null;
                double latitude;
                double longitude;
                String postalCode;

                for (int i = 0; i < poiList.size(); i++) {

                    try {
                        addressList = geoCoder.getFromLocationName(poiList.get(i).getAddress(), 5);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    /* 위도, 경도, 우편번호를 얻어옴 */
                    latitude = addressList.get(0).getLatitude();
                    longitude = addressList.get(0).getLongitude();
                    postalCode = addressList.get(0).getPostalCode();

                    poiList.get(i).setLatitude(latitude);
                    poiList.get(i).setLongitude(longitude);

                    /* 기준위치(poiList의 0번째 인덱스) 는 centerMarkerOptions로 표시
                     * 그 밖의 위치(poiList의 1~4번째 인덱스)는 poiMakerOptions로 표시
                     */
                    if(i == 0) {
                        centerMarkerOptions = new MarkerOptions();
                        centerMarkerOptions.position(new LatLng(poiList.get(i).getLatitude(), poiList.get(i).getLongitude()))
                                .title(poiList.get(i).getAddress())
                                .snippet(postalCode)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                        googleMap.addMarker(centerMarkerOptions);
                    }
                    else {
                        poiMarkerOptions = new MarkerOptions();
                        poiMarkerOptions.position(new LatLng(poiList.get(i).getLatitude(), poiList.get(i).getLongitude()))
                                .title(poiList.get(i).getAddress())
                                .snippet(postalCode)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                        googleMap.addMarker(poiMarkerOptions);
                    }
                    /* 기준 위치로 이동 */
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centerMarkerOptions.getPosition(), DEFAULT_ZOOM_LEVEL));
                }
            }

        }
    }

}




/* Permisson 관련 코드

// permission 요청 코드
private final static int PERMISSION_REQ_CODE = 100;

//  permission 요청
    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION }, PERMISSION_REQ_CODE);
        return;
    }

//  permission 사용자 확인 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case PERMISSION_REQ_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission was granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission was denied!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
*/


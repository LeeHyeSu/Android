// 과제05
// 작성일: 2018. 12. 12
// 작성자: 01분반 20160989 이혜수

package ddwucom.mobile.report.report05_01_20160989;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ddwucom.mobile.report.report05_01_20160989.model.NaverSearchItems;
import ddwucom.mobile.report.report05_01_20160989.model.NaverSearchResult;
import ddwucom.mobile.report.report05_01_20160989.remote.NaverAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";

    EditText editText;
    TextView tvResult;

    String apiUrl;
    String naverId;
    String naverSecret;

    // Retrofit 서비스 객체

    private Retrofit retrofit = null;

    private NaverAPIService mRetrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.etLocal);
        tvResult = (TextView)findViewById(R.id.tvResult);

        apiUrl = getResources().getString(R.string.api_url);
        naverId = getResources().getString(R.string.client_id);
        naverSecret = getResources().getString(R.string.client_secret);

        // Retrofit 객체 생성
        if (retrofit == null) {
            try {
                retrofit = new Retrofit.Builder()
                        .baseUrl(apiUrl)        // apiUrl 은 반드시 / 로 끝나는 부분만으로 구성 (domain 또는 path 로만 구성)
                        .addConverterFactory( GsonConverterFactory.create() )
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Retrofit 객체를 사용하여 사용자가 지정한 인터페이스를 구현한 서비스 생성
        mRetrofitService = retrofit.create(NaverAPIService.class);
    }

    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.button:
                String query = editText.getText().toString() + " 베이커리";

                // Retrofit 서비스로 구현한 인터페이스 객체를 통해 서버호출객체(Call) 생성
                Call<NaverSearchResult> apiCall = mRetrofitService.getNaverSearchItemsResult("json", naverId, naverSecret, query);

                // 서버호출객체를 사용하여 서버 비동기(enqueue)로 요청, 결과를 수신하면 apiCallback 의 메소드 수행
                apiCall.enqueue(apiCallback);

                break;
        }
    }

    // API의 요청 결과 처리 인터페이스 객체
    Callback<NaverSearchResult> apiCallback = new Callback<NaverSearchResult>() {
        @Override
        public void onResponse(Call<NaverSearchResult> call, Response<NaverSearchResult> response) {
            if (response.isSuccessful()) {
                StringBuffer result  = new StringBuffer();

                ArrayList<NaverSearchItems> list = (ArrayList<NaverSearchItems>) response.body().getItems();

                for (NaverSearchItems item : list) {
                    result.append(item);    // item의 toString() 호출
                    result.append("\n");
                }
                tvResult.setText(result.toString());
            }
        }

        // 서버의 응답에 이상이 있을 경우 수행
        @Override
        public void onFailure(Call<NaverSearchResult> call, Throwable t) {
            Log.e(TAG, "failure");
            Log.e(TAG, t.getMessage());
        }
    };
}

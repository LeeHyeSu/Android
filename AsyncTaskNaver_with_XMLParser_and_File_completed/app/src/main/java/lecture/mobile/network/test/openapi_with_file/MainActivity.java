package lecture.mobile.network.test.openapi_with_file;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    EditText etTarget;
    ListView lvList;
    String apiAddress;

    String query;

    MyBookAdapter adapter;
    ArrayList<NaverBookDto> resultList;
    NaverBookXmlParser parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTarget = (EditText)findViewById(R.id.etTarget);
        lvList = (ListView)findViewById(R.id.lvList);

        resultList = new ArrayList();
        adapter = new MyBookAdapter(this, R.layout.book_item, resultList);
        lvList.setAdapter(adapter);

        apiAddress = getResources().getString(R.string.api_url);
        parser = new NaverBookXmlParser();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageFileManager imgManager = new ImageFileManager(this, ImageFileManager.CACHE_IMAGE);
        imgManager.removeAllImages();
    }


    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSearch:
                query = etTarget.getText().toString();
                new NaverAsyncTask().execute();
                break;
        }
    }


    class NaverAsyncTask extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDlg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDlg = ProgressDialog.show(MainActivity.this, "Wait", "Downloading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuffer response = new StringBuffer();

            // 클라이언트 아이디 및 시크릿 그리고 요청 URL 선언
            String clientId = getResources().getString(R.string.client_id);
            String clientSecret = getResources().getString(R.string.client_secret);

            try {
                String apiURL = apiAddress + URLEncoder.encode(query, "UTF-8");
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                // response 수신
                int responseCode = con.getResponseCode();
                if (responseCode==200) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                } else {
                    Log.e(TAG, "API 호출 에러 발생 : 에러코드=" + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response.toString();
        }


        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, result);

            resultList = parser.parse(result);
            adapter.setList(resultList);
            adapter.notifyDataSetChanged();

            progressDlg.dismiss();
        }
    }

}

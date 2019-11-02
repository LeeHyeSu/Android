package lecture.mobile.network.test.asynctaskdownhtml;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etTargetDate;
    ListView lvList;
    String address;

    ArrayAdapter<DailyBoxOffice> adapter;
    ArrayList<DailyBoxOffice> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTargetDate = (EditText)findViewById(R.id.etTargetDate);
        lvList = (ListView)findViewById(R.id.lvList);

        resultList = new ArrayList<DailyBoxOffice>();
        adapter = new ArrayAdapter<DailyBoxOffice>(this, android.R.layout.simple_list_item_1, resultList);

        lvList.setAdapter(adapter);

//        address = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml?key=e67d9c6f2a38b88f466a71f2df8acdc6&targetDt=";
//        res/values/strings.xml 의 server_url 값을 읽어옴
        address = getResources().getString(R.string.server_url);
    }


    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnDownHtml:
                String targetDate = etTargetDate.getText().toString();
                if (targetDate.equals("")) targetDate = etTargetDate.getHint().toString();  // 입력값이 없을 경우 hint 속성의 값을 기본 값으로 설정
                new NetworkAsyncTask().execute("" + targetDate);    // server_url 에 입력한 날짜를 결합한 후 AsyncTask 실행
                break;
        }
    }


    class NetworkAsyncTask extends AsyncTask<String, Integer, String> {

        public final static String TAG = "NetworkAsyncTask";
        public final static int TIME_OUT = 10000;

        ProgressDialog progressDlg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDlg = ProgressDialog.show(MainActivity.this, "Wait", "Downloading...");     // 진행상황 다이얼로그 출력
        }

        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            StringBuilder result = new StringBuilder();
            BufferedReader br = null;
            HttpURLConnection conn = null;

            try {
                URL url = new URL(address);
                conn = (HttpURLConnection)url.openConnection();

                if (conn != null) {
                    conn.setConnectTimeout(TIME_OUT);
                    conn.setUseCaches(false);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                        for (String line = br.readLine(); line != null; line = br.readLine()) {
                            result.append(line + '\n');
                        }
                    }
                }

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                cancel(false);
            } catch (Exception ex) {
                ex.printStackTrace();
                cancel(false);
            } finally {
                try {
                    if (br != null)  br.close();
                    if (conn != null) conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
//          parser 생성 및 parsing 수행
            MyXmlParser parser = new MyXmlParser();
            resultList = parser.parse(result);

//            어댑터에 이전에 보여준 데이터가 있을 경우 클리어
            if (!resultList.isEmpty()) adapter.clear();

//            리스트뷰에 연결되어 있는 어댑터에 parsing 결과 ArrayList 를 추가
            adapter.addAll(resultList);

//            진행상황 다이얼로그 종료
            progressDlg.dismiss();
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(MainActivity.this, "Error!!!", Toast.LENGTH_SHORT).show();
            progressDlg.dismiss();
        }
    }


}

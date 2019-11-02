package mobile.example.downloadhtml;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	EditText etUrl;
	TextView tvResult;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        etUrl = (EditText)findViewById(R.id.etUrl);
        tvResult = (TextView)findViewById(R.id.tvResult);

		StrictMode.ThreadPolicy pol
				= new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
		StrictMode.setThreadPolicy(pol);

	}

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btnDownload:
			String html;
			if (!etUrl.equals("")) {
				html = downloadHtml(etUrl.getText().toString());
				tvResult.setText(html);
			}
			break;
			
		}
	}


	String downloadHtml(String addr) {
		StringBuilder html = new StringBuilder(); 
		try {
			URL url = new URL(addr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			if (conn != null) {
				conn.setConnectTimeout(10000);
				conn.setUseCaches(false);
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(conn.getInputStream()));
					for (String line = br.readLine(); line != null; line = br.readLine()) {
						html.append(line + '\n');
					}
					br.close();
				}
				conn.disconnect();
			}
		} catch (NetworkOnMainThreadException e) {
			return "Error : 메인 스레드 네트워크 작업 에러 - " + e.getMessage();
		} catch (Exception e) {
			return "Error : " + e.getMessage();
		}
		return html.toString();
	}
	
}

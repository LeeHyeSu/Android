package mobile.example.networktest01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ProgressDialog progressDlg;
    String targetAddress;
    TextView tvResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        tvResult = (TextView)findViewById(R.id.tvResult);

        targetAddress = getResources().getString(R.string.target_url);
	}
	
	
	public void onClick(View v) {
		progressDlg = ProgressDialog.show(this, "Wait", "Downloading...");
        MyNetworkThread networkThread = new MyNetworkThread(targetAddress, netoworkResultHandler);
		networkThread.start();
	}


    Handler netoworkResultHandler = new Handler() {
        public void handleMessage(Message msg) {
            String result = (String)msg.obj;
            tvResult.setText(result);

            progressDlg.dismiss();
        }
    };




	class MyNetworkThread extends Thread {

        public final static int TIME_OUT = 10000;
        public final static int NETWORK_OK = 1;
        public final static int NETWORK_CANCEL = 0;

        String address;
        Handler handler;

		MyNetworkThread(String address, Handler handler) {
			this.address = address;
            this.handler = handler;
		}

		public void run() {
            Message msg = handler.obtainMessage();
            msg.what = NETWORK_CANCEL;

			StringBuilder resultBuilder = new StringBuilder();

			try {
				URL url = new URL(address);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();

				if (conn != null) {
					conn.setConnectTimeout(TIME_OUT);
					conn.setUseCaches(false);
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                        for (String line = br.readLine(); line != null; line = br.readLine()) {
							resultBuilder.append(line + '\n');
						}

						br.close();
					}
					conn.disconnect();

                    msg.what = NETWORK_OK;
                    msg.obj = resultBuilder.toString();
				}

			} catch (MalformedURLException ex) {
                ex.printStackTrace();
                Toast.makeText(MainActivity.this, "Malformed URL", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
				ex.printStackTrace();
			}

            handler.sendMessage(msg);
		}
	}

	
}

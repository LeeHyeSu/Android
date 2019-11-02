package lecture.mobile.network.test.asynctaskdownhtml.sample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import lecture.mobile.network.test.asynctaskdownhtml.R;

public class TestActivity extends AppCompatActivity {

    TextView tvResult;
    TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tvResult = (TextView)findViewById(R.id.tvResult);
        tvProgress = (TextView)findViewById(R.id.tvProgress);
    }

    public void onClick(View v) {
        MyAsyncTaskClass task = new MyAsyncTaskClass();
        switch (v.getId()) {
            case R.id.btnExecute:
                task.execute("url", "id", "pwd");
                // new MyAsyncTaskClass().execute("url", "id", "pwd");
                break;
            case R.id.btnCheck:
                AsyncTask.Status status = task.getStatus();
                // PENDING, RUNNING, FINISHED
                break;
        }
    }

    class MyAsyncTaskClass extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            Toast.makeText(TestActivity.this, "Start!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... inputParams) {
            String url = inputParams[0];
            String id = inputParams[1];
            String pwd = inputParams[2];
            for (int i = 0; i < 100; i++) {
                publishProgress(i);
            }
            return  url + "/" + id + "/" + pwd;
        }

        @Override
        protected void onPostExecute(String result) {
            tvResult.setText(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            tvResult.setText("Progress: " + values[0]);
        }
    }



}

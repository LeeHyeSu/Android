package lecture.mobile.network.test.asynctaskdownhtml.sample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import lecture.mobile.network.test.asynctaskdownhtml.MainActivity;
import lecture.mobile.network.test.asynctaskdownhtml.R;

import static java.lang.Thread.sleep;

public class TestActivity extends AppCompatActivity {

    TextView tvResult;
    TextView tvProgress;

    MyAsyncTaskClass task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tvResult = (TextView)findViewById(R.id.tvResult);
        tvProgress = (TextView)findViewById(R.id.tvProgress);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnExecute:
                task = new MyAsyncTaskClass();
                task.execute("value1", "value2", "valu3");
                // new MyAsyncTaskClass().execute("url", "id", "pwd");
                break;
            case R.id.btnCheck:
                AsyncTask.Status status = task.getStatus();
                // PENDING, RUNNING, FINISHED
                Toast.makeText(this, "Status: " + status, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCancel:
                task.cancel(false);

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
            String value1 = inputParams[0];
            String value2 = inputParams[1];
            String value3 = inputParams[2];
            for (int i = 0; i < 100; i++) {
                publishProgress(i);
                try {  sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
                if (task.isCancelled()) return "canceled";
            }
            return  value1 + "/" + value2 + "/" + value3 ;
        }

        @Override
        protected void onPostExecute(String result) {
            tvResult.setText(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) { tvProgress.setText(values[0].toString()); }

        @Override
        protected void onCancelled(String result) {
            tvResult.setText("Task is canceled. current result is " + result);
        }
    }



}

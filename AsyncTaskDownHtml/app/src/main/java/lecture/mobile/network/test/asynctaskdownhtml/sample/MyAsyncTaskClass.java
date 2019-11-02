package lecture.mobile.network.test.asynctaskdownhtml.sample;

import android.os.AsyncTask;


public class MyAsyncTaskClass extends AsyncTask<String, Integer, String> {
    @Override // 선택 - 작업 수행 전 초기화 작업
    protected void onPreExecute() {
    }

    @Override // 필수 - 비동기 방식으로 수행하여야 할 작업 지정 (예: 네트워크 등) params: 전달받은 값의 배열
    protected String doInBackground(String... inputParams) {
        return null;
    }

    @Override // 선택 - 작업 완료 후 수행하여야 할 작업
    protected void onPostExecute(String result) {
    }

    @Override // 선택 - 작업 진행 중 진행 상태를 표시 (doInBackground에서 publishProgress(...) 호출 시 실행)
    protected void onProgressUpdate(Integer... values) {
    }

    @Override // 선택 - UI 측에서 cancel() 실행 시 호출되어 작업 중단
    protected void onCancelled() {
    }

    @Override // 선택 - UI 측에서 cancel() 실행 시 호출되어 작업 중단
    protected void onCancelled(String result) {
    }
}




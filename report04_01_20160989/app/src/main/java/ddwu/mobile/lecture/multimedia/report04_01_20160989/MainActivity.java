// 과제04
// 작성일: 2018. 11. 25
// 작성자: 01분반 20160989 이혜수

package ddwu.mobile.lecture.multimedia.report04_01_20160989;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    SimpleCursorAdapter memoAdapter;
    Cursor cursor;
    MemoDBHelper helper;
    ListView lvMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new MemoDBHelper(this);

//        어댑터에 SimpleCursorAdapter 연결 (메모에 포함한 사진의 경로를 표시)
        memoAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] {"path"}, new int[] {android.R.id.text1});


        lvMemo = (ListView)findViewById(R.id.lv_memo);
        lvMemo.setAdapter(memoAdapter);

        lvMemo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long _id) {
//                ShowMemoActivity 호출
                Intent intent = new Intent(MainActivity.this, ShowMemoActivity.class);
                intent.putExtra("id", _id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        DB 에서 모든 레코드를 가져와 Adapter에 설정
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + MemoDBHelper.TABLE_NAME, null);

        memoAdapter.changeCursor(cursor);
        helper.close();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
//                AddMemoActivity 호출
                Intent intent = new Intent(this, AddMemoActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) cursor.close();
    }
}

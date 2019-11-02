package ddwucom.mobile.report01.veganbakery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;

public class AllBakeriesActivity extends Activity {

    ListView lvBakeries = null;
    BakeryDBHelper helper;
    Cursor cursor;
    MyCursorAdapter myAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bakeries);
        lvBakeries = (ListView)findViewById(R.id.lvBakeries);

        helper = new BakeryDBHelper(this);
        myAdapter = new MyCursorAdapter(this, R.layout.listview_layout, null);
        lvBakeries.setAdapter(myAdapter);

        lvBakeries.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                final long position = id;

                AlertDialog.Builder builder = new AlertDialog.Builder(AllBakeriesActivity.this);
                builder.setTitle("삭제");
                builder.setMessage("정말로 삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = helper.getWritableDatabase();
                        db.execSQL("DELETE FROM " + BakeryDBHelper.TABLE_NAME + " WHERE _id = " + position + ";");
                        helper.close();

                        onResume();
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
                return true;
            }
        });

        lvBakeries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                SQLiteDatabase db = helper.getReadableDatabase();
                cursor = db.rawQuery("SELECT * FROM " + BakeryDBHelper.TABLE_NAME + " WHERE _id = " + id + ";", null);
                cursor.moveToFirst();
                BakeryDto item = new BakeryDto();
                item.set_id(cursor.getInt(cursor.getColumnIndex(BakeryDBHelper.COL_ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_NAME)));
                item.setMenu(cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_MENU)));
                item.setForm(cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_FORM)));
                item.setDescription(cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_DESCRIPTION)));
                item.setAddress(cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_ADDRESS)));

                Intent intent = new Intent(AllBakeriesActivity.this, ViewDetailsActivity.class);
                intent.putExtra("bakeryDto", (Serializable) item);

                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        super.onResume();
//        DB에서 데이터를 읽어와 Adapter에 설정
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from " + BakeryDBHelper.TABLE_NAME, null);

        myAdapter.changeCursor(cursor);
        helper.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        cursor 사용 종료
        if (cursor != null) cursor.close();
    }

}

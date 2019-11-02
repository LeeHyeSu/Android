package ddwucom.mobile.report01.veganbakery;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BakeryDBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "bakery_db";
    public final static String TABLE_NAME = "bakery_table";
    public final static String COL_ID = "_id";
    public final static String COL_NAME = "name";
    public final static String COL_MENU = "menu";
    public final static String COL_FORM = "form";
    public final static String COL_DESCRIPTION =  "description";
    public final static String COL_ADDRESS = "address";

    public BakeryDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +
                " ( _id integer primary key autoincrement, "
                + COL_NAME + " TEXT, " + COL_MENU + " TEXT, " + COL_FORM + " TEXT, "
                + COL_DESCRIPTION + " TEXT, " + COL_ADDRESS  + " TEXT);");

        db.execSQL("INSERT INTO " + TABLE_NAME +
                " VALUES (null, '머드스콘', '통밀스콘, 카카오스콘, 코코넛망고스콘', 'Online'," +
                "'영양가 있고 죄책감 없는 완벽한 스콘', null);");
        db.execSQL("INSERT INTO " + TABLE_NAME +
                " VALUES (null, '망넛이네', '찹싸루니, 현미백, 망구마', 'Online'," +
                "'먹는 즐거움, 삶을 사랑하는 당신을 위한 건강한 선택!', null);");
        db.execSQL("INSERT INTO " + TABLE_NAME +
                " VALUES (null, '카페어니스타', '두부케이크, 파운드, 머핀, 스콘', 'Offline'," +
                "'NO밀가루,NO유제품,NO달걀,NO설탕', '연남동 240-34');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table " + TABLE_NAME);
        onCreate(db);
    }
}

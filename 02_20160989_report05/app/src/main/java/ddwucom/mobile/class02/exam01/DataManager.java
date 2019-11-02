package ddwucom.mobile.class02.exam01;

import java.util.ArrayList;

/**
 * Created by DWU on 2018-05-10.
 */

public class DataManager {
    private ArrayList<MyData> myDataList;

    public DataManager() {
        myDataList = new ArrayList<MyData>();
        myDataList.add( new MyData(1, "하월곡동", "서울시 성북구", "좋음"));
        myDataList.add( new MyData(2, "정발산동", "경기도 고양시", "비"));
        myDataList.add( new MyData(3, "목동동", "경기도 파주시", "맑음"));
        myDataList.add( new MyData(4, "운정2동", "경기도 파주시", "흐림"));
    }

    public  ArrayList<MyData> getMyDataList() {
        return myDataList;
    }

    public void addData(long _id, String name, String detail, String condition) {
        myDataList.add( new MyData(_id, name, detail, condition));
    }

    public void removeData(int idx) {
        myDataList.remove(idx);
    }
}

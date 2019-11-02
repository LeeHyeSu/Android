package ddwucom.mobile.class02.exam01;

import android.content.Context;

/**
 * Created by DWU on 2018-05-10.
 */

public class MyData {
    private long _id;
    private String name;
    private String detail;
    private String condition;

    public MyData(long _id, String name, String detail, String condition) {
        this._id = _id;
        this.name = name;
        this.detail = detail;
        this.condition = condition;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}

package ddwucom.mobile.report01.veganbakery;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MyCursorAdapter extends CursorAdapter {

    LayoutInflater inflater;
    Cursor cursor;
    int layout;

    public MyCursorAdapter(Context context, int layout, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
        cursor = c;
    }

    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvBakeryName = (TextView)view.findViewById(R.id.tvBakeryName);
        TextView tvBakeryMenu = (TextView)view.findViewById(R.id.tvBakeryMenu);
        TextView tvBakeryForm = (TextView)view.findViewById(R.id.tvBakeryForm);
        tvBakeryName.setText(cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_NAME)));
        tvBakeryMenu.setText(cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_MENU)));
        tvBakeryForm.setText(cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_FORM)));
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View listItemLayout = inflater.inflate(layout, parent, false);
        return listItemLayout;
    }
}

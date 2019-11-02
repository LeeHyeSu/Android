package ddwucom.mobile.class02.exam01;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by DWU on 2018-05-10.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<MyData> myDataList;
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, int layout, ArrayList<MyData> myDataList) {
        this.context = context;
        this.layout = layout;
        this.myDataList = (new DataManager()).getMyDataList();
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myDataList.size();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final int position = i;
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(layout, viewGroup, false);

            holder = new ViewHolder();
            holder.textNo = (TextView)convertView.findViewById(R.id.textViewNo);
            holder.textName = (TextView)convertView.findViewById(R.id.textViewName);
            holder.textDet = (TextView)convertView.findViewById(R.id.textViewDet);
            holder.textCon = (TextView)convertView.findViewById(R.id.textViewCon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textNo.setText(String.valueOf(myDataList.get(position).get_id()));
        holder.textName.setText(myDataList.get(position).getName());
        holder.textDet.setText(myDataList.get(position).getDetail());
        holder.textCon.setText(myDataList.get(position).getCondition());

        holder.textNo.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, String.valueOf(myDataList.get(position).get_id()), Toast.LENGTH_SHORT).show();
            }
        });
        holder.textName.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, myDataList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.textDet.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, myDataList.get(position).getDetail(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.textCon.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, myDataList.get(position).getCondition(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        TextView textNo;
        TextView textName;
        TextView textDet;
        TextView textCon;
    }
}

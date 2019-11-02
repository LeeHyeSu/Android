package ddwucom.mobile.class02.exam01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DataManager dataManager;
    private ArrayList<MyData>  myDataList;
    private MyAdapter myAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataManager = new DataManager();
        myDataList = dataManager.getMyDataList();

        myAdapter = new MyAdapter(this, R.layout.custom_adapter_view, myDataList);

        listView = (ListView)findViewById(R.id.customListView);

        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(mItemClickListener);
        listView.setOnItemLongClickListener(mItemLongClickListener);

    }

    AdapterView.OnItemClickListener mItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(MainActivity.this, myDataList.get(i).getDetail() + " " + myDataList.get(i).getName() +
                            "의 날씨는 " + myDataList.get(i).getCondition(), Toast.LENGTH_SHORT).show();
                }
            };

    AdapterView.OnItemLongClickListener mItemLongClickListener =
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    dataManager.removeData(i);
                    myAdapter.notifyDataSetChanged();
                    return true;
                }
            };

}

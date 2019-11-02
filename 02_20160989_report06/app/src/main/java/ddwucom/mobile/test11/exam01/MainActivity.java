package ddwucom.mobile.test11.exam01;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DataManager dataManager;
    ListView listView;
    ArrayAdapter adapter;
    ArrayList<Food> foodList;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        dataManager = new DataManager();
        foodList = dataManager.getFoodList();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, foodList);

        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(onItemLongClickListener);

        button = (Button)findViewById(R.id.button);
    }

    AdapterView.OnItemLongClickListener onItemLongClickListener =
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("음식 삭제");
                    builder.setMessage(foodList.get(position).getFood() + " 을(를) 삭제하시겠습니까?");
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setCancelable(false);
                    builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int whichButton) {
                           dataManager.removeData(position);
                           adapter.notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("취소", null);

                    Dialog dlg = builder.create();
                    dlg.show();
                    return true;
                }
            };

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                final LinearLayout addLayout = (LinearLayout)View.inflate(this, R.layout.add_layout, null);

                builder.setTitle("음식 추가");

                builder.setView(addLayout);

                builder.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText etFood = (EditText)addLayout.findViewById(R.id.etFood);
                        EditText etNation = (EditText)addLayout.findViewById(R.id.etNation);

                        dataManager.addData(new Food(etFood.getText().toString(), etNation.getText().toString()));
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("취소", null);

                Dialog dlg = builder.create();
                dlg.show();
        }
    }
}

package ddwucom.mobile.report01.veganbakery;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class InsertBakeryActivity extends Activity {

    BakeryDBHelper helper;
    EditText etName;
    EditText etMenu;
    EditText etDescription;
    EditText etAddress;
    RadioGroup rg;
    RadioButton rdForm;

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_bakery);

        helper = new BakeryDBHelper(this);

        etName = (EditText)findViewById(R.id.etName);
        etMenu = (EditText)findViewById(R.id.etMenu);
        etDescription = (EditText)findViewById(R.id.etDescription);
        etAddress = (EditText)findViewById(R.id.etAddress);
        rg = (RadioGroup)findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = rg.getCheckedRadioButtonId();
                rdForm = (RadioButton)rg.findViewById(selectedId);
            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddNewBakery:
                SQLiteDatabase db = helper.getWritableDatabase();

                String name = etName.getText().toString();
                String menu = etMenu.getText().toString();
                String form = rdForm.getText().toString();
                String description = etDescription.getText().toString();
                String address = etAddress.getText().toString();

                ContentValues row = new ContentValues();
                row.put(BakeryDBHelper.COL_NAME, name);
                row.put(BakeryDBHelper.COL_MENU, menu);
                row.put(BakeryDBHelper.COL_FORM, form);
                row.put(BakeryDBHelper.COL_DESCRIPTION, description);
                row.put(BakeryDBHelper.COL_ADDRESS, address);

                db.insert(BakeryDBHelper.TABLE_NAME, null, row);

                helper.close();
                finish();
                break;
            case R.id.btnAddNewBakeryClose:
                finish();
                break;
        }
    }
}

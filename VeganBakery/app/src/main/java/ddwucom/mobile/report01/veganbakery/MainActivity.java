package ddwucom.mobile.report01.veganbakery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btnOpenAllBakeries:
                intent = new Intent(this, AllBakeriesActivity.class);
                break;
            case R.id.btnAddNewBakery:
                intent = new Intent(this, InsertBakeryActivity.class);
                break;
        }

        if (intent != null) startActivity(intent);
    }
}

package ddwucom.mobile.report01.veganbakery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ViewDetailsActivity extends AppCompatActivity {

    TextView tvName;
    TextView tvMenu;
    TextView tvForm;
    TextView tvDescription;
    TextView tvAddress;

    BakeryDBHelper dbHelper;
    BakeryDto bakeryDto;

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        dbHelper = new BakeryDBHelper(this);

        tvName = findViewById(R.id.tvName);
        tvMenu = findViewById(R.id.tvMenu);
        tvForm = findViewById(R.id.tvForm);
        tvDescription = findViewById(R.id.tvDescription);
        tvAddress = findViewById(R.id.tvAddress);

        Intent intent = getIntent();
        bakeryDto = (BakeryDto) intent.getSerializableExtra("bakeryDto");

        tvName.setText(bakeryDto.getName());
        tvMenu.setText(bakeryDto.getMenu());
        tvForm.setText(bakeryDto.getForm());
        tvDescription.setText(bakeryDto.getDescription());
        tvAddress.setText(bakeryDto.getAddress());
    }

}

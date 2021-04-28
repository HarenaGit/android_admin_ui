package mg.ny.adminui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditplaneActivity extends AppCompatActivity {

    private PlaneDataModel data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editplane);
        data = (PlaneDataModel) getIntent().getParcelableExtra("data");

    }
}
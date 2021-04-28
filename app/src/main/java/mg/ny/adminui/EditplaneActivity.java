package mg.ny.adminui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditplaneActivity extends AppCompatActivity {

    private PlaneDataModel data;
    private ImageButton backButton;
    private EditText id;
    private EditText name;
    private EditText placeCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editplane);
        data = (PlaneDataModel) getIntent().getParcelableExtra("data");
        backButton = findViewById(R.id.backButtonEdit);
        id = findViewById(R.id.editPlaneNumber);
        name = findViewById(R.id.editPlaneName);
        placeCount = findViewById(R.id.editPlacePlane);

        id.setText(data.getId());
        id.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                id.setText(data.getId());
                return false;
            }
        });
        name.setText(data.getName());
        name.requestFocus();
        placeCount.setText(data.getPlaceCount());
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                onBackPressed();
            }
        });
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
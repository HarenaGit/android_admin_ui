package mg.ny.adminui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddplaneActivity extends AppCompatActivity {

    private ImageButton backButton;
    private EditText id;
    private EditText name;
    private EditText place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplane);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        backButton = findViewById(R.id.backButtonAdd);
        id = findViewById(R.id.addPlaneNumber);
        name = findViewById(R.id.addPlaneName);
        place = findViewById(R.id.addPlacePlane);
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
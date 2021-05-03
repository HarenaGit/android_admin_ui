package mg.ny.adminui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;

public class EditplaneActivity extends AppCompatActivity {

    private PlaneDataModel data;
    private ImageButton backButton;
    private EditText id;
    private EditText name;
    private EditText placeCount;
    private MaterialButton save;
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
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });
        save = findViewById(R.id.saveEditPlane);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                Intent intent = new Intent();
                intent.putExtra("data", new PlaneDataModel(id.getText().toString(), name.getText().toString(), placeCount.getText().toString()));
                setResult(RequestCode.REQUEST_CODE_EDIT_PLANE, intent);
                finish();
            }
        });
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
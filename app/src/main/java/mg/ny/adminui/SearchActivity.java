package mg.ny.adminui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private ImageButton backButton;
    private ArrayList<PlaneDataModel> data;
    private PlaneListAdapter adapter;
    private SwipeMenuListView listView;
    private InputMethodManager imm;
    private int p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = findViewById(R.id.searchListItem);
        listView.setVisibility(View.GONE);
        searchView = findViewById(R.id.searchItem);
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = (TextView) searchView.findViewById(id); backButton = findViewById(R.id.backButton);
        Typeface ft = ResourcesCompat.getFont(this, R.font.segeo_ui);
        searchText.setTypeface(ft);
         imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });



        data = getIntent().getParcelableArrayListExtra("data");

        adapter = new PlaneListAdapter(this, data);
        listView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());

                editItem.setBackground(new ColorDrawable(getResources().getColor(R.color.backgroundColor)));
                editItem.setWidth(130);
                editItem.setIcon(R.drawable.ic_edit);
                menu.addMenuItem(editItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(getResources().getColor(R.color.backgroundColor)));
                deleteItem.setWidth(100);
                deleteItem.setIcon(R.drawable.ic_delete_swipe);

                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.remove_dialog);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        Button yes =  dialog.findViewById(R.id.acceptRemove);
        Button no = dialog.findViewById(R.id.declineRemove);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                PlaneDataModel currentPlaneData = data.get(p);
                intent.putExtra("data", currentPlaneData);
                setResult(RequestCode.REQUEST_CODE_REMOVE_PLANE, intent);
                finish();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                position = p;
                switch (index) {
                    case 0:
                        Intent editActivity = new Intent(getApplicationContext(), EditplaneActivity.class);
                        PlaneDataModel currentPlaneData = data.get(position);
                        editActivity.putExtra("data", currentPlaneData);
                        startActivityForResult(editActivity, RequestCode.REQUEST_CODE_EDIT_PLANE);
                        break;
                    case 1:
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                        TextView textDialog = dialog.findViewById(R.id.planeRemoveId);
                        textDialog.setText("Avion numéro : " +  data.get(position).getId());
                        dialog.show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.equals("")) listView.setVisibility(View.VISIBLE);
                else listView.setVisibility(View.GONE);
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_CANCELED){
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
            return;
        }
        if(resultCode == RequestCode.REQUEST_CODE_EDIT_PLANE)
        {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            Intent intent = new Intent();
            PlaneDataModel currentData = (PlaneDataModel) data.getParcelableExtra("data");
            intent.putExtra("data", currentData);
            setResult(RequestCode.REQUEST_CODE_EDIT_PLANE, intent);
            finish();
        }
    }
}
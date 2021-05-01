package mg.ny.adminui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private ImageButton backButton;
    private ArrayList<PlaneDataModel> data;
    private PlaneListAdapter adapter;
    private SwipeMenuListView listView;
    private InputMethodManager imm;
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
                onBackPressed();
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

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Intent editActivity = new Intent(getApplicationContext(), EditplaneActivity.class);
                        PlaneDataModel currentPlaneData = data.get(position);
                        editActivity.putExtra("data", currentPlaneData);
                        startActivityForResult(editActivity, RequestCode.REQUEST_CODE_EDIT_PLANE);
                        break;
                    case 1:
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                        Intent intent = new Intent();
                        setResult(RequestCode.REQUEST_CODE_EDIT_PLANE, intent);
                        finish();
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
        if(requestCode == RequestCode.REQUEST_CODE_EDIT_PLANE)
        {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            Intent intent = new Intent();
            setResult(RequestCode.REQUEST_CODE_EDIT_PLANE, intent);
            finish();
        }
    }
}
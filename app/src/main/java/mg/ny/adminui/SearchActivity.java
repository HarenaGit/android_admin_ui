package mg.ny.adminui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private ImageButton backButton;
    private ArrayList<PlaneDataModel> data;
    private PlaneListAdapter adapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.searchItem);
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = (TextView) searchView.findViewById(id); backButton = findViewById(R.id.backButton);
        Typeface ft = ResourcesCompat.getFont(this, R.font.segeo_ui);
        searchText.setTypeface(ft);
        InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                onBackPressed();
            }
        });

        listView = findViewById(R.id.searchListItem);

        data = new ArrayList<>();
        data.add(new PlaneDataModel("AV-0001", "Jet Privée", "56"));
        data.add(new PlaneDataModel("AV-0002", "AIR261-45", "23"));
        data.add(new PlaneDataModel("AV-0003", "Bus2", "14"));
        data.add(new PlaneDataModel("AV-0004", "AIR265-85", "67"));
        data.add(new PlaneDataModel("AV-0005", "AIR234-78", "45"));
        data.add(new PlaneDataModel("AV-0006", "Jet Privée xoxo", "23"));

        adapter = new PlaneListAdapter(this, data);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
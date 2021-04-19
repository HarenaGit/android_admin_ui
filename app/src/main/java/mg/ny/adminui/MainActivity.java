package mg.ny.adminui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ChipNavigationBar bottomMenu;
    private FragmentManager  fragmentManager;
    private TextView activityTitle;
    private RecyclerView recyclerView;
    private StaticHorizentalListAdapter horizentalListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        setContentView(R.layout.activity_main);
        this.activityTitle = findViewById(R.id.activity_title);
        this.bottomMenu = findViewById(R.id.bottom_menu);
        if(savedInstanceState == null){
            this.bottomMenu.setItemSelected(R.id.Dashboard, true);
            fragmentManager = getSupportFragmentManager();
            DashboardFragment dashboardFragment = new DashboardFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, dashboardFragment).commit();
        }
        this.bottomMenu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                changeCurrentBottomMenuItemSelected(id);
            }
        });



    }

    private void changeCurrentBottomMenuItemSelected(int id){
        Fragment fragment = null;
        switch (id){
            case R.id.Dashboard:
                fragment = new DashboardFragment();
                this.activityTitle.setText("Tableau de bord");
                break;
            case R.id.Plane:
                fragment = new PlaneFragment();
                this.activityTitle.setText("Avion");
                break;
            case R.id.Flight:
                fragment = new FlightFragment();
                this.activityTitle.setText("Vol");
                break;
            case R.id.Reservation:
                fragment = new ReservationFragment();
                this.activityTitle.setText("Reservation");
                break;
            case R.id.Visualization:
                fragment = new VisualisationFragment();
                this.activityTitle.setText("Visualisation");
                break;
        }
        if(fragment != null){
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
        else{
            Log.e(TAG, "Error in creating fragment");
        }
    }
}
 package mg.ny.adminui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ChipNavigationBar bottomMenu;
    private FragmentManager  fragmentManager;
    private TextView activityTitle;
    private RecyclerView recyclerView;
    private StaticHorizentalListAdapter horizentalListAdapter;
    private Fragment fragment;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        setContentView(R.layout.activity_main);
        this.progressBar = findViewById(R.id.spin_kit);
        this.activityTitle = findViewById(R.id.activity_title);
        this.bottomMenu = findViewById(R.id.bottom_menu);
        if(savedInstanceState == null){
            this.bottomMenu.setItemSelected(R.id.Dashboard, true);
            fragmentManager = getSupportFragmentManager();
            fragment = new DashboardFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            progressBar.setVisibility(View.GONE);
        }
        this.bottomMenu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                changeCurrentBottomMenuItemSelected(id);
            }
        });

    }

    public void waiting(int ms){
        try {
            Thread.sleep(ms);

        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
    private void changeCurrentBottomMenuItemSelected(int id){

        progressBar.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
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
        
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
                            @Override
                            public void run() {
                                fragmentManager = getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();


                            }
                        });
                        try {
                            future.get();
                            progressBar.setVisibility(View.GONE);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }, 2000);



    }




}
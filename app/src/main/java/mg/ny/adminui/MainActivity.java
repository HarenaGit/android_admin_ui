 package mg.ny.adminui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import mg.ny.adminui.data_model.PlaneDataModel;
import mg.ny.adminui.view_logics.flight_view.fragment.FlightFragment;
import mg.ny.adminui.view_logics.public_component_view.horizentalList.StaticHorizentalListModel;
import mg.ny.adminui.view_logics.public_component_view.interfaces.RemoveItemCallBack;
import mg.ny.adminui.view_logics.plane_view.activity.SearchActivity;
import mg.ny.adminui.view_logics.plane_view.fragment.PlaneFragment;

 public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
    private ChipNavigationBar bottomMenu;
    private FragmentManager  fragmentManager;
    private TextView activityTitle;
    private Fragment fragment;
    private Fragment planeFragment;
    private ProgressBar progressBar;
    private ImageButton searchButton;
    private ArrayList<PlaneDataModel> planeData;
    private ArrayList<StaticHorizentalListModel> planeItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        setContentView(R.layout.activity_main);
        this.progressBar = findViewById(R.id.spin_kit);
        this.activityTitle = findViewById(R.id.activity_title);
        this.bottomMenu = findViewById(R.id.bottom_menu);
        this.searchButton = findViewById(R.id.searchButton);
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
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityTitle.getText().equals("Avion")){
                    Intent searchActivity = new Intent(getApplicationContext(), SearchActivity.class);
                    searchActivity.putParcelableArrayListExtra("data", planeData);
                    startActivityForResult(searchActivity, RequestCode.REQUEST_CODE_SEARCH_PLANE);
                }

            }
        });

    }
    private void changeCurrentBottomMenuItemSelected(final int id){
        this.searchButton.setVisibility(View.GONE);
        final ImageButton searchBtn = this.searchButton;
        progressBar.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        switch (id){
            case R.id.Dashboard:
                fragment = new DashboardFragment();
                this.activityTitle.setText("Tableau de bord");
                break;
            case R.id.Plane:
                if(planeData == null || planeItem == null){
                    planeData = planeData();
                    planeItem = planeItem();
                }
                RemoveItemCallBack removeItemCallBack = (int p) -> {
                   planeData.remove(p);
                   planeItem.remove(p);
                   Log.d("test", "ca devrait marcher alors");
                };
                fragment = new PlaneFragment(planeItem, planeData, removeItemCallBack);
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
                           synchronized (this){
                               future.get();
                               progressBar.setVisibility(View.GONE);
                               if(id != R.id.Dashboard) searchBtn.setVisibility(View.VISIBLE);
                               else searchBtn.setVisibility(View.GONE);
                           }

                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("test", "result code : " + resultCode);
        if(resultCode == Activity.RESULT_CANCELED) return;
        switch (resultCode){
            case RequestCode.REQUEST_CODE_ADD_PLANE:
                PlaneDataModel currentPlaneData = (PlaneDataModel) data.getParcelableExtra("data");
                addPlaneData(currentPlaneData);
                addPlaneItem(currentPlaneData.getName());

                break;
            case RequestCode.REQUEST_CODE_EDIT_PLANE:
                PlaneDataModel currentData = (PlaneDataModel) data.getParcelableExtra("data");
                int currentPosition = getPlaneDataPosition(currentData.getId());
                if(currentPosition>=0){
                    setPlaneData(currentPosition, currentData);
                    setPlaneItem(currentPosition, new StaticHorizentalListModel(currentData.getName()));
                }
                break;
            default:
        }

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        fragment.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addPlaneData(PlaneDataModel o){
        planeData.add(0, o);
    }
    private void addPlaneItem(String text){
        planeItem.add(0, new StaticHorizentalListModel(text));
    }
    private void setPlaneData(int position, PlaneDataModel o){
        planeData.set(position, o);
    }
    private void setPlaneItem(int position, StaticHorizentalListModel o){
        planeItem.set(position, o);
    }
    private int getPlaneDataPosition(String id){
        for(int i=0; i<planeData.size();i++){
            if(planeData.get(i).getId().equals(id)) return i;
        }
        return -1;
    }
    private ArrayList<PlaneDataModel>  planeData(){

        ArrayList<PlaneDataModel> data = new ArrayList<>();
        data.add(new PlaneDataModel("AV-0001", "Jet Privée", "56"));
        data.add(new PlaneDataModel("AV-0002", "AIR261-45", "23"));
        data.add(new PlaneDataModel("AV-0003", "Bus2", "14"));
        data.add(new PlaneDataModel("AV-0004", "AIR265-85", "67"));
        data.add(new PlaneDataModel("AV-0005", "AIR234-78", "45"));
        data.add(new PlaneDataModel("AV-0006", "Jet Privée xoxo", "23"));

        return data;
    }
    private ArrayList<StaticHorizentalListModel> planeItem(){
        ArrayList<StaticHorizentalListModel> item = new ArrayList<>();
        item.add(new StaticHorizentalListModel("Jet Privée"));
        item.add(new StaticHorizentalListModel("AIR261-45"));
        item.add(new StaticHorizentalListModel("Bus2"));
        item.add(new StaticHorizentalListModel("AIR265-85"));
        item.add(new StaticHorizentalListModel("AIR234-78"));
        item.add(new StaticHorizentalListModel("Jet Privée xoxo"));



        return item;
    }




}
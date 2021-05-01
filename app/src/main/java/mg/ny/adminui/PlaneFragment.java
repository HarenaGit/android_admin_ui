package mg.ny.adminui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class  PlaneFragment extends Fragment {

   private ArrayList<StaticHorizentalListModel> item ;
   private ArrayList<PlaneDataModel> data;
    public PlaneFragment(ArrayList<StaticHorizentalListModel> item, ArrayList<PlaneDataModel> data){
        this.item = item;
        this.data = data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private Context context;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.context = activity;
    }



    private RecyclerView recyclerView;
    private StaticHorizentalListAdapter horizentalListAdapter;
    private LayoutInflater inflater;
    private ViewGroup container;
    private PlaneDataModel currentPlaneData;
    private View planeDetail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        return inflater.inflate(R.layout.fragment_plane, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

                RelativeLayout planeFragement = view.findViewById(R.id.planeFragement);

                View planePage = inflater.inflate(R.layout.plane_page, container, false);
                planePage.findViewById(R.id.addPlaneButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent addActivity = new Intent(context, AddplaneActivity.class);
                        startActivityForResult(addActivity, RequestCode.REQUEST_CODE_ADD_PLANE);
                    }
                });
                planeFragement.addView(planePage);
                RelativeLayout planeList = view.findViewById(R.id.planeHList);
                View planeHorizentalList = inflater.inflate(R.layout.plane_horizental_list, container, false);
                planeList.addView(planeHorizentalList);
                RelativeLayout planeContent = view.findViewById(R.id.planeContent);
                View selectionIcon = inflater.inflate(R.layout.selection_icon, container, false);
                planeContent.addView(selectionIcon);
                final View  planed = inflater.inflate(R.layout.plane_content, container, false);
                planed.findViewById(R.id.editCurrentPlane).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Intent editActivity = new Intent(context, EditplaneActivity.class);
                       editActivity.putExtra("data", currentPlaneData);
                       startActivityForResult(editActivity, RequestCode.REQUEST_CODE_EDIT_PLANE);
                    }
                });
                recyclerView = view.findViewById(R.id.rv_plane);
                HorizentalListCallBack<StaticHorizentalListAdapter.StaticHorizentalListViewHolder, Integer, Boolean, Integer> callbackHorizentalList = (StaticHorizentalListAdapter.StaticHorizentalListViewHolder holder, Integer position, Boolean isFirstClicked) -> {
                    if(isFirstClicked) {
                        planeContent.removeView(selectionIcon);
                        planeContent.addView(planed);
                    }
                    currentPlaneData = data.get(position);
                    TextView id = view.findViewById(R.id.planeId);
                    TextView name = view.findViewById(R.id.planeName);
                    TextView placeCount = view.findViewById(R.id.planePlaceCount);
                    id.setText(currentPlaneData.getId());
                    name.setText(currentPlaneData.getName());
                    placeCount.setText(currentPlaneData.getPlaceCount());

                    return 0;
                };
                horizentalListAdapter = new StaticHorizentalListAdapter(item, callbackHorizentalList);
                recyclerView.setLayoutManager(new LinearLayoutManager( view.getContext(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(horizentalListAdapter);
                TextView planeNumber = view.findViewById(R.id.planeNumber);
                planeNumber.setText(String.valueOf(data.size()));




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RequestCode.REQUEST_CODE_ADD_PLANE:
                Toast.makeText(context, "add new plane data", 2000).show();
                break;
            case RequestCode.REQUEST_CODE_EDIT_PLANE:
                Toast.makeText(context, "edited plane data", 2000).show();
                break;
            case RequestCode.REQUEST_CODE_REMOVE_PLANE:
                Toast.makeText(context, "remove plane data", 2000).show();
                break;
            default:

        }

    }

}
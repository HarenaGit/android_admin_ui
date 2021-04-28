package mg.ny.adminui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
                planeFragement.addView(planePage);
                RelativeLayout planeList = view.findViewById(R.id.planeHList);
                View planeHorizentalList = inflater.inflate(R.layout.plane_horizental_list, container, false);
                planeList.addView(planeHorizentalList);
                RelativeLayout planeContent = view.findViewById(R.id.planeContent);
                View selectionIcon = inflater.inflate(R.layout.selection_icon, container, false);
                planeContent.addView(selectionIcon);
                View planeDetail  = inflater.inflate(R.layout.plane_content, container, false);
                recyclerView = view.findViewById(R.id.rv_plane);
                HorizentalListCallBack<StaticHorizentalListAdapter.StaticHorizentalListViewHolder, Integer, Boolean, Integer> callbackHorizentalList = (StaticHorizentalListAdapter.StaticHorizentalListViewHolder holder, Integer position, Boolean isFirstClicked) -> {
                    if(isFirstClicked) {
                        planeContent.removeView(selectionIcon);
                        planeContent.addView(planeDetail);
                    }
                    PlaneDataModel currentPlaneData = data.get(position);
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



}
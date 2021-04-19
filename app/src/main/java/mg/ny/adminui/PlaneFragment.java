package mg.ny.adminui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaneFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public PlaneFragment() {
        // Required empty public constructor

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaneFragment newInstance(String param1, String param2) {
        PlaneFragment fragment = new PlaneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private Context context;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.context = activity;
    }
    private RecyclerView recyclerView;
    private StaticHorizentalListAdapter horizentalListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList<StaticHorizentalListModel> item = new ArrayList<>();
        item.add(new StaticHorizentalListModel("Jet Privée"));
        item.add(new StaticHorizentalListModel("AIR261-45"));
        item.add(new StaticHorizentalListModel("Bus2"));
        item.add(new StaticHorizentalListModel("AIR265-85"));
        item.add(new StaticHorizentalListModel("AIR234-78"));
        item.add(new StaticHorizentalListModel("Jet Privée xoxo"));

        View view = inflater.inflate(R.layout.fragment_plane, container, false);
        recyclerView = view.findViewById(R.id.rv_plane);
        horizentalListAdapter = new StaticHorizentalListAdapter(item);

        recyclerView.setLayoutManager(new LinearLayoutManager( view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(horizentalListAdapter);

        return view;
    }
}
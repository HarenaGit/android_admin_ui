package mg.ny.adminui;

import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StaticHorizentalListAdapter extends RecyclerView.Adapter<StaticHorizentalListAdapter.StaticHorizentalListViewHolder> {

    private ArrayList<StaticHorizentalListModel> items;
    private HorizentalListCallBack<StaticHorizentalListViewHolder, Integer, Integer> onClickCallback;
    int row_index = -1;
    public StaticHorizentalListAdapter(ArrayList<StaticHorizentalListModel> items, HorizentalListCallBack<StaticHorizentalListViewHolder, Integer, Integer> onClickCallback){
        this.items = items;
        this.onClickCallback = onClickCallback;
    }

    @NonNull
    @Override
    public StaticHorizentalListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizental_list_item, parent, false);
        StaticHorizentalListViewHolder horizentaViewHolder = new StaticHorizentalListViewHolder(view);
        return horizentaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StaticHorizentalListViewHolder holder, int position) {
        StaticHorizentalListModel currentItem = items.get(position);
        holder.text.setText(currentItem.getText());

        holder.horizentalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                notifyDataSetChanged();
                onClickCallback.apply(holder, position);
            }
        });
        if(row_index == position) {
            holder.text.setBackgroundResource(R.drawable.horizental_selected_list_bg);
            holder.text.setTextColor(holder.text.getResources().getColor(R.color.white));
        }
        else{
            holder.text.setBackgroundResource(R.drawable.horizental_list_bg);
            holder.text.setTextColor(holder.text.getResources().getColor(R.color.dark_grey));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class StaticHorizentalListViewHolder extends RecyclerView.ViewHolder{

        TextView text;
        RelativeLayout horizentalLayout;
        public StaticHorizentalListViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.horizentalTextItem);
            horizentalLayout = itemView.findViewById(R.id.horizentalLayout);
        }
    }
}

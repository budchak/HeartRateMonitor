package com.java.yaroshevich.heartRateMonitor.UI.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java.yaroshevich.heartRateMonitor.R;
import com.java.yaroshevich.heartRateMonitor.model.Measurement;

import java.util.List;

public class viewResultRecyclerViewAdapter extends RecyclerView.Adapter<viewResultRecyclerViewAdapter.ViewHolder> {

    private List<Measurement> strings;
    private Context context;




    public viewResultRecyclerViewAdapter(Context context, List<Measurement> data) {
        strings = data;
        this.context = context;
    }

    public void setStrings(List<Measurement> measurements){
        this.strings = measurements;
    }



    @NonNull
    @Override
    public viewResultRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.result_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewResultRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(strings.get(i));
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pulse;
        TextView time;
        TextView name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.status_textview);
            pulse = itemView.findViewById(R.id.ticrate_textview);
            time = itemView.findViewById(R.id.time_measurement_textView);

        }

        public void bind(Measurement measurement){
            int ticrate = measurement.getTicrate();

            if(ticrate < 80){
                pulse.setTextColor(itemView.getResources().getColor(R.color.green));
            }else if (ticrate < 130){
                pulse.setTextColor(itemView.getResources().getColor(R.color.yellow));
            }else if (ticrate >= 130){
                pulse.setTextColor(itemView.getResources().getColor(R.color.red));
            }

            pulse.setText(Integer.toString(ticrate));
            time.setText(measurement.getDate());
            name.setText(measurement.getName());

        }
    }
}

package com.java.yaroshevich.heartRateMonitor.UI.measurementSchedule;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.java.yaroshevich.heartRateMonitor.model.Measurement;
import com.java.yaroshevich.heartRateMonitor.R;
import com.java.yaroshevich.heartRateMonitor.UI.measurementSchedule.Interface.NewMeasurmentCreateListener;
import com.java.yaroshevich.heartRateMonitor.UI.measurementSchedule.Interface.ViewHolderEventListener;

import java.util.List;

public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Measurement> measurements;

    ViewHolderEventListener eventListener;
    NewMeasurmentCreateListener createListener;


    public static final int VIEW_TYPE_ADD = 1;
    public static final int VIEW_TYPE_MEASUREMENT = 2;



    public ScheduleRecyclerViewAdapter(List<Measurement> measurements) {
        this.measurements = measurements;

    }

    public void setData(List<Measurement> measurements){
        this.measurements = measurements;
    }

    public void setCreateListener(NewMeasurmentCreateListener createListener) {
        this.createListener = createListener;
    }

    public void setEventListener(ViewHolderEventListener listener){
        this.eventListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i){
            case 1:
                Log.e("tag", "1");
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.view_shedule_element, viewGroup, false);
                return new ScheduleRecyclerViewAdapter.ViewHolder(view);
            case 2:  Log.e("tag", "2");
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.view_add_new_measurment_type, viewGroup, false);
                return new addNewElementViewHolder(view);
            default:  Log.e("tag", "3"); view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_shedule_element, viewGroup, false); break;
        }

        return new ScheduleRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof addNewElementViewHolder){
            ((addNewElementViewHolder) viewHolder).bind();
        }
        else ((ViewHolder) viewHolder).bind(measurements.get(i).getName());
    }

    @Override
    public int getItemViewType(int position) {
        return (position == measurements.size()) ? VIEW_TYPE_MEASUREMENT : VIEW_TYPE_ADD;
    }


    @Override
    public int getItemCount() {
        return measurements.size() + 1;
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView elementName;
        private TextView typeTextView;

        private Button deliteButton;
        private Button updateButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            elementName = itemView.findViewById(R.id.name_shedule_element_text_view);
            typeTextView = itemView.findViewById(R.id.type_measurement_text_view);
        }

        void bind(String name){
            elementName.setText(name);
        }
    }


    class addNewElementViewHolder extends ViewHolder {

        ImageButton button;

        public addNewElementViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.add_new_measurement_type_image_batton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createListener.addNewMeasurementType();
                }
            });
        }

        void bind(){

        }

    }
}

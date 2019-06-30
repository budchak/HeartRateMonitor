package com.java.yaroshevich.heartRateMonitor.UI.measurmentType;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java.yaroshevich.heartRateMonitor.R;

import java.util.ArrayList;
import java.util.List;

public class MeasurementTypeRecyclerViewAdapter extends RecyclerView.Adapter<MeasurementTypeRecyclerViewAdapter.TypeViewHolder> {

    List<String> list;
    private RecyclerViewItemClickListener listener;

    public MeasurementTypeRecyclerViewAdapter(RecyclerViewItemClickListener listener) {

        this.listener = listener;
        list = new ArrayList<>();
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TypeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_measurement_type, viewGroup, false);
        return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeViewHolder viewHolder, int i) {
        viewHolder.bind(list.get(i), i);
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    class TypeViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private View view;

        public TypeViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.type_text_view);
            view = itemView.findViewById(R.id.measurement_type_view);
        }

        void bind(String string, final int item) {
            textView.setText(string);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


}

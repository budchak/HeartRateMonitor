package com.java.yaroshevich.heartRateMonitor.UI.measurementSchedule;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.java.yaroshevich.heartRateMonitor.R;
import com.java.yaroshevich.heartRateMonitor.model.Measurement;

public class NewMeasurementFragment extends DialogFragment {

    private CreateDialogCallback dialogCallback;
    private Spinner spinner;
    private EditText editText;
    private int positionSelector;

    private String[] strings = {"простое", "непрерывное"};


    public void setDialogCallback(CreateDialogCallback createDialogCallback) {

        this.dialogCallback = createDialogCallback;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.view_new_measurement_element, null);
        spinner = view.findViewById(R.id.type_measurement_spiner);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, strings);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionSelector = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                positionSelector = 0;
            }
        });

        editText = view.findViewById(R.id.name_type_text_view);

        builder.setView(view)
                .setTitle("Добавить новое измерение")
                .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Measurement measurement = new Measurement("",0,"");
                        measurement.setName(editText.getText().toString());
                        measurement.setTaskID(positionSelector);

                        dialogCallback.onSuccess(measurement);
                    }
                })
                .setNegativeButton("отменить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dialogCallback = (CreateDialogCallback)context;
    }
}

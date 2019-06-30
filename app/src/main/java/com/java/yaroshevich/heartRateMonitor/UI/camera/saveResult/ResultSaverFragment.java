package com.java.yaroshevich.heartRateMonitor.UI.camera.saveResult;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.java.yaroshevich.heartRateMonitor.R;
import com.java.yaroshevich.heartRateMonitor.model.Measurement;

public class ResultSaverFragment extends DialogFragment {

    private TextView ticrateTextView;

    private String ticrate;
    private ResultSaverCallback callback;
    private int type = 0;
    View view;

    public static ResultSaverFragment newInstance(String ticrate) {
        ResultSaverFragment fragment = new ResultSaverFragment();
        Bundle args = new Bundle();
        args.putString("someInt", ticrate);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get back arguments
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        ticrate = getArguments().getString("someInt", "11");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.view_save_result, null);
        ticrateTextView = view.findViewById(R.id.SaveTicrateTextView);
        ticrateTextView.setText(ticrate);
        radioGroupLister(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Сохранить результат")
                .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Measurement measurement = new Measurement("sdfs", Integer.parseInt(ticrate), "sdfsd");
                        callback.onSuccess(new Measurement("sdfs", Integer.parseInt(ticrate), "sdfsd"));
                    }
                })
                .setNegativeButton("отменить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (ResultSaverCallback) context;
    }


    private void radioGroupLister(View view){
        final TextView textView = view.findViewById(R.id.typeSelfTextView);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:
                        break;
                    case R.id.veryBadRadioButton:
                        type = checkedId;
                        textView.setText("Самочувствие: " + "очень плохое");
                        break;
                    case R.id.badRadioButton:
                        type = checkedId;
                        textView.setText("Самочувствие: " + "плохое");
                        break;
                    case R.id.normalRadioButton:
                        type = checkedId;
                        textView.setText("Самочувствие: " + "номальное");
                        break;
                    case R.id.goodRadioButton:
                        type = checkedId;
                        textView.setText("Самочувствие: " + "хорошее");
                        break;
                    case R.id.bestRadioButton:
                        type = checkedId;
                        textView.setText("Самочувствие: " + "отличное");
                        break;

                    default:
                        break;
                }
            }
        });
    }
}

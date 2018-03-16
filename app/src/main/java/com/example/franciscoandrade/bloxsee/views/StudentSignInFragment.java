package com.example.franciscoandrade.bloxsee.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.franciscoandrade.bloxsee.R;

import java.util.ArrayList;
import java.util.List;

public class StudentSignInFragment extends Fragment {

    private View v;
    private Spinner spinner;
    private ArrayList <String> listStudents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_student_sign_in, container, false);
        spinner = v.findViewById(R.id.spinnerNames);

        getStudentsList();
        setSpinner();
        return v;
    }

    private void setSpinner() {
        ArrayAdapter <String> adapter = new ArrayAdapter <String>(this.getActivity(), R.layout.spinner_item, listStudents);
        // adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    private void getStudentsList() {
        listStudents = new ArrayList <>();
        listStudents.add("Francisco");
        listStudents.add("Francisco");
        listStudents.add("Francisco");
        listStudents.add("Francisco");
        listStudents.add("Francisco");
        listStudents.add("Francisco");
        listStudents.add("Francisco");
        listStudents.add("Francisco");
        listStudents.add("Francisco");
    }
}

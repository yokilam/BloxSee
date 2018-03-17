package com.example.franciscoandrade.bloxsee.views.teacher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RosterFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText studentName;
    private Spinner passwordSpinner;
    private Button addStudent;
    private ArrayList<String> listPassword;
    String nameStudent, selectedPassword;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private FirebaseDatabase database;


    public RosterFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_roster, container, false);
        recyclerView = view.findViewById(R.id.recycler_container);
        studentName = view.findViewById(R.id.studentName);
        passwordSpinner = view.findViewById(R.id.passwordSpinner);
        addStudent = view.findViewById(R.id.addStudent);
        listPassword();
        setSpinner();
        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        return view;
    }

    private void setSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this.getActivity(), R.layout.spinner_item, listPassword);
        // adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        passwordSpinner.setAdapter(adapter);

    }

    private void listPassword() {
        listPassword= new ArrayList<>();
        listPassword.add("Select password");
        listPassword.add("Bird");
        listPassword.add("Monkey");
        listPassword.add("Dog");
        listPassword.add("Cat");
    }

    public void addStudent() {
        nameStudent=studentName.getText().toString();
        selectedPassword=passwordSpinner.getSelectedItem().toString();
        if(!selectedPassword.equals(listPassword.get(0)) && !TextUtils.isEmpty(nameStudent)){
            Log.d("ADDED==", "addStudent: "+nameStudent);
            Log.d("ADDED==", "addStudent: "+selectedPassword);
            Student student= new Student(nameStudent, selectedPassword);
            ref.child("students").child(nameStudent).setValue(student);
            studentName.setText("");
        }
        else {
            Toast.makeText(getActivity(), "Cant add Student!!", Toast.LENGTH_SHORT).show();
            studentName.setText("");
        }
    }
}

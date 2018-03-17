package com.example.franciscoandrade.bloxsee.views.teacher;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Student;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RosterFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText studentName;
    private Spinner passwordSpinner;
    private Button addStudent;
    private ArrayList <String> listPassword;
    private String nameStudent, selectedPassword;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    public RelativeLayout buttonLayout;
    public ExpandableLinearLayout expandableLayout;
    private List <Student> studentList = new ArrayList <>();

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roster, container, false);
        setUpViews(view);

        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new TeacherRosterAdapter(studentList));

        runExpandableLayoutLogic(view);
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

    private void runExpandableLayoutLogic(View view) {
        //set up the background color for expandable layout
        expandableLayout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.material_red_300));
        //set up the animation when the layout expands
        expandableLayout.setInterpolator(Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR));
        //set Listener when the expandable layout expands
        expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            //Before the expandable layout opens, the arrow button do the animation of spinning
            public void onPreOpen() {
                createRotateAnimator(buttonLayout, 0f, 180f).start();
                expandState.put(0, true);
            }

            @Override
            //Before the expandable layout closes, the arrow button do the revert animation spinning
            public void onPreClose() {
                createRotateAnimator(buttonLayout, 180f, 0f).start();
                expandState.put(0, false);
            }
        });

        buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(expandableLayout);
            }
        });
    }

    private void setUpViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_container);
        studentName = view.findViewById(R.id.studentName);
        passwordSpinner = view.findViewById(R.id.passwordSpinner);
        addStudent = view.findViewById(R.id.addStudent);

        //Set Up Expandable Layout
        buttonLayout = (RelativeLayout) view.findViewById(R.id.button);
        expandableLayout = (ExpandableLinearLayout) view.findViewById(R.id.expandableLayout);
    }

    private void setSpinner() {
        ArrayAdapter <String> adapter = new ArrayAdapter <String>(this.getActivity(), R.layout.spinner_item, listPassword);
        // adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        passwordSpinner.setAdapter(adapter);
    }

    private void listPassword() {
        listPassword = new ArrayList <>();
        listPassword.add("Select password");
        listPassword.add("Penguin");
        listPassword.add("Ghost");
        listPassword.add("Dog");
        listPassword.add("Cat");
        listPassword.add("Dragon");
        listPassword.add("Octopus");
    }

    public void addStudent() {
        nameStudent = studentName.getText().toString();
        selectedPassword = passwordSpinner.getSelectedItem().toString();
        if (!selectedPassword.equals(listPassword.get(0)) && !TextUtils.isEmpty(nameStudent)) {
            Log.d("ADDED==", "addStudent: " + nameStudent);
            Log.d("ADDED==", "addStudent: " + selectedPassword);
            Student student = new Student(nameStudent, selectedPassword);
            ref.child("students").child(nameStudent).setValue(student);
            studentName.setText("");
        } else {
            Toast.makeText(getActivity(), "Cant add Student!!", Toast.LENGTH_SHORT).show();
            studentName.setText("");
        }
    }

    //Starts animation to expand the layout
    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    //Animation
    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}


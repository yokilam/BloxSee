package com.example.franciscoandrade.bloxsee.views.teacher;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.Toast;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Progress;
import com.example.franciscoandrade.bloxsee.model.Student;
import com.example.franciscoandrade.bloxsee.model.StudentQuestions;
import com.example.franciscoandrade.bloxsee.util.ExpandableLayoutAnimation;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
    private List <Student> studentList ;
    public Student student;
    ExpandableLayoutAnimation expandableLayoutAnimation;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private FirebaseDatabase database;
    private ChildEventListener childEventListener;
    List<StudentQuestions> lesson1;
    List<StudentQuestions> lesson2;
    List<Progress> progressList;
    TeacherProgressAdapter teacherProgressAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roster, container, false);
        setUpViews(view);
        expandableLayoutAnimation= new ExpandableLayoutAnimation();

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
        student= new Student();
        studentList = new ArrayList <>();


        ChildEventListener childEventListener= new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                student=dataSnapshot.getValue(Student.class);
                Log.d("CHILD", "onChildAdded: "+ student.getName());
                studentList.add(student);
                recyclerView.setAdapter(new TeacherRosterAdapter(studentList));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.child("students").addChildEventListener(childEventListener);

        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        return view;
    }

    private void runExpandableLayoutLogic(View view) {
        expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation(view, expandableLayout,R.color.material_red_300, expandState );
        //set Listener when the expandable layout expands
        expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            //Before the expandable layout opens, the arrow button do the animation of spinning
            public void onPreOpen() {
                expandableLayoutAnimation.createRotateAnimator(buttonLayout, 0f, 180f).start();
                expandState.put(0, true);
            }

            @Override
            //Before the expandable layout closes, the arrow button do the revert animation spinning
            public void onPreClose() {
                expandableLayoutAnimation.createRotateAnimator(buttonLayout, 180f, 0f).start();
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
//        StudentQuestions studentQuestions= new StudentQuestions();
//        studentQuestions.setQ1(false);
//        studentQuestions.setQ2(false);
//        studentQuestions.setQ3(false);
//        studentQuestions.setQ4(false);
//        studentQuestions.setQ5(false);

        if (!selectedPassword.equals(listPassword.get(0)) && !TextUtils.isEmpty(nameStudent)) {
            Log.d("ADDED==", "addStudent: " + nameStudent);
            Log.d("ADDED==", "addStudent: " + selectedPassword);
            Student student = new Student(nameStudent, selectedPassword);
            setQuestions();
            ref.child("students").child(nameStudent).setValue(student);
            studentName.setText("");
        } else {
            Toast.makeText(getActivity(), "Cant add Student!!", Toast.LENGTH_SHORT).show();
            studentName.setText("");
        }
    }

    private void setQuestions() {
        progressList= new ArrayList<>();

        childEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Progress progress= new Progress();
                StudentQuestions studentQuestions= new StudentQuestions();
                progress.setName(dataSnapshot.getKey());
                lesson1= new ArrayList<>();
                lesson2= new ArrayList<>();

                for (int i = 1; i < 3; i++) {
                    for (int j = 1; j < 6; j++) {
                        if (i==1 && dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue()!= null){
                            String state= dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue().toString();
                            //Log.d("State==", "onChildAdded: "+state);
//                            if(i==1){
//                                studentQuestions.setQuestion("JOANNES q's");
//                            }
                            studentQuestions.setState(state);
                            lesson1.add(studentQuestions);

                        }
                        if(i==2 && dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue()!= null){
                            String state= dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue().toString();
                            studentQuestions.setState(state);
                            lesson2.add(studentQuestions);
                        }
//                            Log.d("ADDEEED", "onChildAdded: "+dataSnapshot.getKey()+" - "+dataSnapshot.child("lesson"+i).getKey()+" - "+dataSnapshot.child("lesson"+i).child(j+"").getKey()+" - "+dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue());
                    }
                }

                progress.setLesson1(lesson1);
                progress.setLesson2(lesson2);
                progressList.add(progress);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.child("students").addChildEventListener(childEventListener);


    }

    //Starts animation to expand the layout
    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }


}


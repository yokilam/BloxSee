package com.example.franciscoandrade.bloxsee.views.teacher;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Progress;
import com.example.franciscoandrade.bloxsee.model.Student;
import com.example.franciscoandrade.bloxsee.model.StudentQuestions;
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
public class AddStudentFragment extends Fragment {

    View v;

    TextInputEditText studentName;
    Spinner passwordSpinner;
    Button addStudent;
    private ArrayList<String> listPassword;
    private String nameStudent, selectedPassword;
    private DatabaseReference ref;
    private FirebaseDatabase database;
    List<Progress> progressList;
    private ChildEventListener childEventListener;
    List<StudentQuestions> lesson1;
    List<StudentQuestions> lesson2;
    public Student student;
    private List <Student> studentList ;

    ListenerProgress listenerProgress;

    FloatingActionButton closeBtn;






    public AddStudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_add_student, container, false);
        studentName=v.findViewById(R.id.studentName);
        passwordSpinner=v.findViewById(R.id.passwordSpinner);
        addStudent=v.findViewById(R.id.addStudent);
        closeBtn=v.findViewById(R.id.closeBtn);

        studentName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        listPassword();
        setSpinner();

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        studentList = new ArrayList <>();


        closeView();

        return v;
    }

    private void closeView() {

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerProgress.closeView();

            }
        });

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


    private void setSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this.getActivity(), R.layout.spinner_item, listPassword);
        // adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        passwordSpinner.setAdapter(adapter);

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
            ref.child("students").child(nameStudent).setValue(student);
            setQuestions();
            studentName.setText("");
            listenerProgress.closeFragment();
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

                            studentQuestions.setState(state);
                            studentQuestions.setAvailable(true);
                            lesson1.add(studentQuestions);


                        }
                        if(i==2 && dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue()!= null){
                            String state= dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue().toString();
                            studentQuestions.setState(state);
                            lesson2.add(studentQuestions);
                        }
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listenerProgress=(ListenerProgress)context;
    }
}

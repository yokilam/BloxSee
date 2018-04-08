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
import android.widget.Spinner;
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

public class AddStudentFragment extends Fragment {

    private View v;
    private TextInputEditText studentName;
    private Spinner passwordSpinner;
    private Button addStudent;
    private ArrayList<String> listPassword;
    private String nameStudent, selectedPassword;
    private DatabaseReference ref;
    private FirebaseDatabase database;
    private List<Progress> progressList;
    private ChildEventListener childEventListener;
    private List<StudentQuestions> lesson1;
    private List<StudentQuestions> lesson2;

    public Student student;
    private List <Student> studentList ;

    ListenerProgress listenerProgress;

    private FloatingActionButton closeBtn;

    Progress progress;
    StudentQuestions lesson1q1, lesson1q2, lesson1q3, lesson1q4, lesson1q5;
    StudentQuestions lesson2q1, lesson2q2, lesson2q3, lesson2q4, lesson2q5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_add_student, container, false);

        setUpViews();
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

        progressListFill();

        return v;
    }

    private void progressListFill() {

        progress= new Progress();
        lesson1= new ArrayList<>();
        lesson2= new ArrayList<>();

        fillLessonOne();
        fillLessonTwo();

    }

    private void fillLessonTwo() {
        lesson2q1= new StudentQuestions();
        lesson2q2= new StudentQuestions();
        lesson2q3= new StudentQuestions();
        lesson2q4= new StudentQuestions();
        lesson2q5= new StudentQuestions();

        lesson2q1.setQuestion("1. Add a red color to the street light.");
        lesson2q1.setAvailable(false);
        lesson2q1.setState("null");
        lesson2q1.setAnswer("");

        lesson2q2.setQuestion("2. Add a yellow color to the street light.");
        lesson2q2.setAvailable(false);
        lesson2q2.setState("null");
        lesson2q2.setAnswer("");


        lesson2q3.setQuestion("3. Add a green color to the street light.");
        lesson2q3.setAvailable(false);
        lesson2q3.setState("null");
        lesson2q3.setAnswer("");

        lesson2q4.setQuestion("4. Flash the yellow light 5 times.");
        lesson2q4.setAvailable(false);
        lesson2q4.setState("null");
        lesson2q4.setAnswer("");

        lesson2q5.setQuestion("5. Flash the yellow light 3 times. Then flash the green light 3 times");
        lesson2q5.setAvailable(false);
        lesson2q5.setState("null");
        lesson2q5.setAnswer("");

        lesson2.add(lesson2q1);
        lesson2.add(lesson2q2);
        lesson2.add(lesson2q3);
        lesson2.add(lesson2q4);
        lesson2.add(lesson2q5);
        progress.setLesson2(lesson2);

    }
    private void fillLessonOne() {

        lesson1q1= new StudentQuestions();
        lesson1q2= new StudentQuestions();
        lesson1q3= new StudentQuestions();
        lesson1q4= new StudentQuestions();
        lesson1q5= new StudentQuestions();

        lesson1q1.setQuestion("1. Move the sprite to the right");
        lesson1q1.setAvailable(false);
        lesson1q1.setState("null");
        lesson1q1.setAnswer("start\\n” + “moveright\\n");

        lesson1q2.setQuestion("2. Move the sprite to the center ");
        lesson1q2.setAvailable(false);
        lesson1q2.setState("null");
        lesson1q2.setAnswer("start\n” + “moveright\n” + “movedown\n");

        lesson1q3.setQuestion("3. Make your sprite move from one edge to the other edge. Repeat this motion twice.");
        lesson1q3.setAvailable(false);
        lesson1q3.setState("null");
        lesson1q3.setAnswer(" start\\n” + “moveright\\n”+ “moveleft\\n");

        lesson1q4.setQuestion("4. Change the backdrop");
        lesson1q4.setAvailable(false);
        lesson1q4.setState("null");
        lesson1q4.setAnswer("");

        lesson1q5.setQuestion("5. Delete the penguin sprite. Create a new sprite.");
        lesson1q5.setAvailable(false);
        lesson1q5.setState("null");
        lesson1q5.setAnswer("");

        lesson1.add(lesson1q1);
        lesson1.add(lesson1q2);
        lesson1.add(lesson1q3);
        lesson1.add(lesson1q4);
        lesson1.add(lesson1q5);
        progress.setLesson1(lesson1);

    }

    private void setUpViews() {
        studentName=v.findViewById(R.id.studentName);
        passwordSpinner=v.findViewById(R.id.passwordSpinner);
        addStudent=v.findViewById(R.id.addStudent);
        closeBtn=v.findViewById(R.id.closeBtn);
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
        listPassword.add("Pig");
        listPassword.add("Dog");
        listPassword.add("Duck");
        listPassword.add("Monkey");
        listPassword.add("Seal");
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

        if (!selectedPassword.equals(listPassword.get(0)) && !TextUtils.isEmpty(nameStudent)) {
            Log.d("ADDED==", "addStudent: " + nameStudent);
            Log.d("ADDED==", "addStudent: " + selectedPassword);
            Student student = new Student(nameStudent, selectedPassword);
            //ref.child("students").child(nameStudent).setValue(student);
            //setQuestions();
            setQuestionsAndANswers(nameStudent,student);

            studentName.setText("");
            listenerProgress.closeFragment();
        } else {
            Toast.makeText(getActivity(), "Cant add Student!!", Toast.LENGTH_SHORT).show();
            studentName.setText("");
        }
    }

    private void setQuestionsAndANswers(String student, Student studentObject) {

//                ref.child("students").child(student).child("lesson1").child("1").child("question").setValue( "1. Move the sprite to the right");
//                ref.child("students").child(student).child("lesson1").child("1").child("answer").setValue("start\\n” + “moveright\\n” + “movedown\\n");
//                ref.child("students").child(student).child("lesson2").child("1").child("question").setValue("1. Add a red color to the street light. ");

//                ref.child("students").child(student).child("lesson1").child("2").child("question").setValue("2. Move the sprite to the center ");
//                ref.child("students").child(student).child("lesson2").child("2").child("question").setValue( "2. Add a yellow color to the street light. ");

//                ref.child("students").child(student).child("lesson1").child("3").child("question").setValue("3. Make your sprite move from one edge to the other edge. Repeat this motion twice.");
//                ref.child("students").child(student).child("lesson2").child("3").child("question").setValue("3. Add a green color to the street light. ");

//                ref.child("students").child(student).child("lesson1").child("4").child("question").setValue("4. Change the backdrop");
//                ref.child("students").child(student).child("lesson2").child( "4").child("question").setValue( "4. Flash the yellow light 5 times.");

//                ref.child("students").child(student).child("lesson1").child( "5").child("question").setValue("5. Delete the penguin sprite. Create a new sprite.");
//                ref.child("students").child(student).child("lesson2").child( "5").child("question").setValue("5. Flash the yellow light 3 times. Then flash the green light 3 times");

//        for (int i = 1; i < 6; i++) {
//            //setQuestions(i, dataSnapshot);
//            //
//            ref.child("students").child(student).child("lesson1").child((i) + "").child("available").setValue(false);
//            //ref.child("students").child(student).child("lesson1").child((i)+"").child("question").setValue("Question: "+i);
//
//            ref.child("students").child(student).child("lesson2").child((i) + "").child("available").setValue(false);
//            //ref.child("students").child(student).child("lesson2").child((i)+"").child("question").setValue("Question: "+i);
//
//            ref.child("students").child(student).child("lesson1").child(i + "").child("state").setValue("null");
//            ref.child("students").child(student).child("lesson2").child(i + "").child("state").setValue("null");
//
////            if (dataSnapshot.child("lesson1").child(i + "").child("state").getValue() == null) {
////                ref.child("students").child(student).child("lesson1").child(i + "").child("state").setValue("null");
////            }
////            if (dataSnapshot.child("lesson2").child(i + "").child("state").getValue() == null) {
////                ref.child("students").child(student).child("lesson2").child(i + "").child("state").setValue("null");
////            }
//
//        }

        progress.setName(student);
        progress.setPassword(studentObject.getPassword());

        ref.child("students").child(student).setValue(progress);
    }

//    private void setQuestions() {
//        progressList= new ArrayList<>();
//
//        childEventListener= new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                Progress progress= new Progress();
//                StudentQuestions studentQuestions= new StudentQuestions();
//                progress.setName(dataSnapshot.getKey());
//                lesson1= new ArrayList<>();
//                lesson2= new ArrayList<>();
//
//                for (int i = 1; i < 3; i++) {
//                    for (int j = 1; j < 6; j++) {
//                        if (i==1 && dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue()!= null){
//                            String state= dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue().toString();
//
//                            studentQuestions.setState(state);
//                            studentQuestions.setAvailable(true);
//                            lesson1.add(studentQuestions);
//
//
//                        }
//                        if(i==2 && dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue()!= null){
//                            String state= dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue().toString();
//                            studentQuestions.setState(state);
//                            lesson2.add(studentQuestions);
//                        }
//                    }
//                }
//
//                progress.setLesson1(lesson1);
//                progress.setLesson2(lesson2);
//                progressList.add(progress);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//
//        ref.child("students").addChildEventListener(childEventListener);
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listenerProgress=(ListenerProgress)context;
    }
}

package com.example.franciscoandrade.bloxsee.views;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Student;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentSignInFragment extends Fragment implements View.OnClickListener {

    private View v;
    private Spinner spinner;
    private TeacherSignInFragment teacherSignInFragment;
    android.support.v4.app.FragmentManager fragmentManager;
    private TextView toTeacherFragment;
    private ArrayList <String> listStudents;
    private ImageView penguin, duck, dog, monkey, pig, seal, bloxseelogo;
    private Button studentLogInBtn;
    //Firebase Setup
    private DatabaseReference ref;
    private FirebaseDatabase database;
    private DatabaseReference messageRef;
    private Student student;
    private String animalPicked;
    private String pass;
    private LinearLayout animalRowOne, animalRowTwo, edittext;
    private Animation fromTop, fromBottom, edittext_fromBottom, edittext_fromTop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_student_sign_in, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        teacherSignInFragment = new TeacherSignInFragment();

        setUpViews();
        fromTop = AnimationUtils.loadAnimation(v.getContext(), R.anim.fromtop);
        fromBottom= AnimationUtils.loadAnimation(v.getContext(), R.anim.frombottom);
        edittext_fromBottom= AnimationUtils.loadAnimation(v.getContext(), R.anim.edittext_frombottom);
        edittext_fromTop= AnimationUtils.loadAnimation(v.getContext(), R.anim.edittext_fromtop);
        edittext.setAnimation(edittext_fromTop);
        bloxseelogo.setAnimation(fromTop);

        imageSetClicks();
        listStudents = new ArrayList <>();
        listStudents.add("Choose Your Name: ");

        new AsyncClass().execute();

        hideCharacters();

        return v;
    }

    private void hideCharacters() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
                Log.d("SPINNER", "onCreateView: "+spinner.getSelectedItem()+ position);
                if(position==0){
                    animalRowOne.setVisibility(View.GONE);
                    animalRowTwo.setVisibility(View.GONE);
//                    edittext.setAnimation(edittext_fromBottom);
                }
                else {
                    edittext.setAnimation(edittext_fromBottom);
                    animalRowOne.setVisibility(View.VISIBLE);
                    animalRowTwo.setVisibility(View.VISIBLE);
                    animalRowOne.setAnimation(fromBottom);
                    animalRowTwo.setAnimation(fromBottom);
                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {}
        });
    }

    private void setUpViews() {
        toTeacherFragment = v.findViewById(R.id.not_a_student);
        spinner = v.findViewById(R.id.spinnerNames);
        penguin = v.findViewById(R.id.penguin);
        duck = v.findViewById(R.id.duck);
        dog = v.findViewById(R.id.dog);
        monkey = v.findViewById(R.id.monkey);
        pig = v.findViewById(R.id.pig);
        seal = v.findViewById(R.id.seal);
        studentLogInBtn = v.findViewById(R.id.studentLogInBtn);
        animalRowOne= v.findViewById(R.id.animal_row_one);
        animalRowTwo= v.findViewById(R.id.animal_row_two);
        edittext= v.findViewById(R.id.edit_text_layout);
        bloxseelogo= v.findViewById(R.id.bloxseelogo);
    }

    private void imageSetClicks() {
        toTeacherFragment.setOnClickListener(this);
        penguin.setOnClickListener(this);
        duck.setOnClickListener(this);
        dog.setOnClickListener(this);
        monkey.setOnClickListener(this);
        pig.setOnClickListener(this);
        seal.setOnClickListener(this);
        studentLogInBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        clearBackground();
        switch (v.getId()) {
            case R.id.not_a_student:
                fragmentManager.beginTransaction().add(R.id.loginContainer, teacherSignInFragment).addToBackStack("teachersignin").addToBackStack("teachersignup").commit();
                break;
            case R.id.penguin:
                //Toast.makeText(getActivity(), "Penguin", Toast.LENGTH_SHORT).show();
                animalPicked = "Penguin";
                penguin.setBackgroundResource(R.drawable.penguin_color);
                break;
            case R.id.duck:
                duck.setBackgroundResource(R.drawable.duck_color);
                animalPicked = "Duck";
                //Toast.makeText(getActivity(), "duck", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dog:
                dog.setBackgroundResource(R.drawable.dog_color);
                animalPicked = "Dog";
                //Toast.makeText(getActivity(), "dog", Toast.LENGTH_SHORT).show();
                break;
            case R.id.monkey:
                monkey.setBackgroundResource(R.drawable.monkey_color);
                animalPicked = "Monkey";
                //Toast.makeText(getActivity(), "Monkey", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pig:
                pig.setBackgroundResource(R.drawable.pig_color);
                animalPicked = "Pig";
                //Toast.makeText(getActivity(), "pig", Toast.LENGTH_SHORT).show();
                break;
            case R.id.seal:
                seal.setBackgroundResource(R.drawable.seal_color);
                animalPicked = "Seal";
                //Toast.makeText(getActivity(), "Seal", Toast.LENGTH_SHORT).show();
                break;
            case R.id.studentLogInBtn:
                loginStudent();
                //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void loginStudent() {
        String nameStudent = spinner.getSelectedItem().toString();

        if (!TextUtils.isEmpty(animalPicked) && !nameStudent.equals(listStudents.get(0)) && spinner.getSelectedItem().toString() != null) {
            Log.d("SPINNER==", "loginStudent: Animal: READY TO LOG IN");
            String result = getPasswordOfUser(nameStudent, animalPicked);
            Log.d("LOGIN====", "loginStudent: " + result);
            Log.d("LOGIN====", "loginStudent: " + animalPicked);
            Log.d("LOGIIN==", "loginStudent: " + ref.child("students").child(nameStudent).getKey());
        }
    }

    private String getPasswordOfUser(String student, final String password) {

        messageRef = ref.child("students").child(student).child("password");
        ref = database.getReference();
        pass = "";
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("LOGIIN==", "loginStudent: 111" + dataSnapshot.getValue());
                pass = dataSnapshot.getValue().toString();
                Log.d("LOGIIN==", "loginStudent: 2222 " + dataSnapshot.getValue());

                checkLoginCredentials(pass, password);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        Log.d("LOGIIN==", "loginStudent: 2222111 " + pass);

        return pass;
    }

    private void checkLoginCredentials(String pass, String password) {
        Log.d("CHECKING", "checkLoginCredentials: " + pass);
        Log.d("CHECKING", "checkLoginCredentials: " + password);

        if (pass.equals(password)) {
            String studentName = spinner.getSelectedItem().toString();
            Intent intent = new Intent(getActivity(), StudentActivity.class);
            intent.putExtra("studentName", studentName);
            startActivity(intent);
            getActivity().finish();
            Log.d("CHECKING", "checkLoginCredentials: PASSEED");
            Toast.makeText(getActivity(), "PASSED", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("STUDENT", "loginStudent: password Doesnt match");
            Toast.makeText(getActivity(), "loginStudent: password Doesnt match", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearBackground() {
        penguin.setBackgroundResource(R.drawable.penguin_outline);
        duck.setBackgroundResource(R.drawable.duck_outline);
        dog.setBackgroundResource(R.drawable.dog_outline);
        monkey.setBackgroundResource(R.drawable.monkey_outline);
        pig.setBackgroundResource(R.drawable.pig_outline);
        seal.setBackgroundResource(R.drawable.seal_outline);
    }

    private class AsyncClass extends AsyncTask <Void, Void, Void> {
        ArrayAdapter <String> adapter;

        @Override
        protected Void doInBackground(Void... voids) {
            database = FirebaseDatabase.getInstance();
            ref = database.getReference();
            student = new Student();

            ChildEventListener childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    student = dataSnapshot.getValue(Student.class);
                    listStudents.add(dataSnapshot.getKey());
                    Log.d("CHILDren==", "onChildAdded: " + student.getName());
                    Log.d("CHILD==", "onChildAdded: " + student.getName());
                    // adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };

            ref.child("students").addChildEventListener(childEventListener);
            adapter = new ArrayAdapter <>(getActivity(), R.layout.spinner_item, listStudents);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }
}



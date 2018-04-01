package com.example.franciscoandrade.bloxsee.views.teacher;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.ScreenShotFragment;
import com.example.franciscoandrade.bloxsee.model.Progress;
import com.example.franciscoandrade.bloxsee.model.StudentQuestions;
import com.example.franciscoandrade.bloxsee.views.student.UrlListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class ProgressFragment extends Fragment  {

    private RecyclerView recyclerView;
//    private List<Progress> progressList= new ArrayList<>();


    private DatabaseReference ref;
    private FirebaseDatabase database;
    private ChildEventListener childEventListener;

    List<StudentQuestions> lesson1;
    List<StudentQuestions> lesson2;
    //List<Progress> progressList;
    Set<Progress> progressList ;

    TeacherProgressAdapter teacherProgressAdapter;

    FrameLayout containerScreenShot;

    FragmentManager manager;
    ScreenShotFragment screenShotFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_progress, container, false);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        recyclerView= view.findViewById(R.id.progress_recyclerview);
        containerScreenShot= view.findViewById(R.id.containerScreenShot);
        //recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        teacherProgressAdapter= new TeacherProgressAdapter(getActivity());
        recyclerView.setAdapter(teacherProgressAdapter);
        Log.d("VIEW", "onCreateView: ####");
        getData();

        manager= getFragmentManager();
        screenShotFragment= new ScreenShotFragment();
        return view;
    }



    private void getData() {

        Log.d("VIEW", "onCreateView: GETDATTAAA!!");

        //progressList= new ArrayList<>();
        progressList = new HashSet<Progress>();;

        childEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Progress progress= new Progress();
                StudentQuestions studentQuestions;

                progress.setName(dataSnapshot.getKey());
                lesson1= new ArrayList<>();
                lesson2= new ArrayList<>();
                Log.d("NAME=", "onChildAdded: "+dataSnapshot);
                for (int i = 1; i < 3; i++) {
                    for (int j = 1; j < 6; j++) {
                        studentQuestions= new StudentQuestions();

                        if (i==1 && dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue()!= null){

                            String state= dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue().toString();
                            Log.d("STATE", "onChildAdded: "+dataSnapshot.child("lesson"+i).child(j+"").child("question").getValue().toString());

                            studentQuestions.setState(state);
                            studentQuestions.setQuestion(dataSnapshot.child("lesson"+i).child(j+"").child("question").getValue().toString());
                            lesson1.add(studentQuestions);
                        }

                        if(i==2 && dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue()!= null){
                            String state= dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue().toString();
                            Log.d("STATE", "onChildAdded: "+state);
                            studentQuestions.setQuestion(dataSnapshot.child("lesson"+i).child(j+"").child("question").getValue().toString());
                            studentQuestions.setState(state);
                            lesson2.add(studentQuestions);                        }
                    }

                Log.d("QS==", "onChildAdded: "+lesson1.get(0).getQuestion());
                Log.d("QS==", "onChildAdded: "+lesson1.get(1).getQuestion());
                Log.d("QS==", "onChildAdded: "+lesson1.get(2).getQuestion());
                Log.d("QS==", "onChildAdded: "+lesson1.get(3).getQuestion());
                Log.d("QS==", "onChildAdded: "+lesson1.get(4).getQuestion());
                Log.d("QS==", "onChildAdded: "+lesson1.get(0).getState());
                Log.d("QS==", "onChildAdded: "+lesson1.get(1).getState());
                Log.d("QS==", "onChildAdded: "+lesson1.get(2).getState());
                Log.d("QS==", "onChildAdded: "+lesson1.get(3).getState());
                Log.d("QS==", "onChildAdded: "+lesson1.get(4).getState());
                progress.setLesson1(lesson1);
                progress.setLesson2(lesson2);
                progressList.add(progress);
                }



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
        runTimer();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                for (int i = 0; i < progressList.size()-1; i++) {
//                    Log.d("ListaaaProgress", "onStart: "+ progressList.get(i).getName()+" lesson1: "+ progressList.get(i).getLesson1().get(i)+"lesson2: "+progressList.get(i).getLesson2().get(i));
//                }
                teacherProgressAdapter.notifyDataSetChanged();
                teacherProgressAdapter.addProgress(progressList);
            }
        }, 2000);
    }

    @Override
    public void onStart() {
        super.onStart();

//        Log.d("VIEW", "onCreateView: !!!");
//
//        progressList= new ArrayList<>();
//
//        childEventListener= new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Progress progress= new Progress();
//                StudentQuestions studentQuestions= new StudentQuestions();
//                progress.setName(dataSnapshot.getKey());
//                lesson1= new ArrayList<>();
//                lesson2= new ArrayList<>();
//                for (int i = 1; i < 3; i++) {
//                    for (int j = 1; j < 6; j++) {
//                        if (i==1 && dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue()!= null){
//                            String state= dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue().toString();
//                            studentQuestions.setState(state);
//                            lesson1.add(studentQuestions);
//                        }
//
//                        if(i==2 && dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue()!= null){
//                            String state= dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue().toString();
//                            studentQuestions.setState(state);
//                            lesson2.add(studentQuestions);                        }
//                    }
//                }
//
//                progress.setLesson1(lesson1);
//                progress.setLesson2(lesson2);
//                progressList.add(progress);
//
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
//
//        ref.child("students").addChildEventListener(childEventListener);
//
//        runTimer();
//
//         Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                for (int i = 0; i < progressList.size()-1; i++) {
////                    Log.d("ListaaaProgress", "onStart: "+ progressList.get(i).getName()+" lesson1: "+ progressList.get(i).getLesson1().get(i)+"lesson2: "+progressList.get(i).getLesson2().get(i));
////                }
//                teacherProgressAdapter.addProgress(progressList);
//
//            }
//        }, 2000);

    }

    private void runTimer() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                teacherProgressAdapter.addProgress(progressList);
            }
        }, 2000);
    }

    @Override
    public void setEnterTransition(Object transition) {
        super.setEnterTransition(transition);
        Log.d("ENTEERR", "setEnterTransition: ");
    }

    public void openScreenShotFragment(String url){
        Log.d("Screenshot=", "openScreenShotFragment: ");
        manager.beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.containerScreenShot, screenShotFragment).addToBackStack("backToActivity").addToBackStack(null).commit();

        screenShotFragment.show("ISCOO=");

    }


    public void closeScreenShootFragment(){
        manager.beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit)
                .remove(screenShotFragment).commit();
    }
}

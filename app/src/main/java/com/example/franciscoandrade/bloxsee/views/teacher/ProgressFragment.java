package com.example.franciscoandrade.bloxsee.views.teacher;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Progress;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProgressFragment extends Fragment {

    private RecyclerView recyclerView;
   // private List<Progress> progressList= new ArrayList<>();


    private DatabaseReference ref;
    private FirebaseDatabase database;
    private ChildEventListener childEventListener;

    List<String> lesson1;
    List<String> lesson2;
    List<Progress> progressList;
    TeacherProgressAdapter teacherProgressAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_progress, container, false);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();





        recyclerView= view.findViewById(R.id.progress_recyclerview);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        teacherProgressAdapter= new TeacherProgressAdapter(getActivity());
        recyclerView.setAdapter(teacherProgressAdapter);

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();

        progressList= new ArrayList<>();

        childEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Progress progress= new Progress();
                progress.setName(dataSnapshot.getKey());
                lesson1= new ArrayList<>();
                lesson2= new ArrayList<>();
                for (int i = 1; i < 3; i++) {
                    for (int j = 1; j < 6; j++) {
                        if (i==1 && dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue()!= null){
                            String state= dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue().toString();
                            //Log.d("State==", "onChildAdded: "+state);
                            lesson1.add(state);
                        }
                        if(i==2 && dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue()!= null){
                            lesson2.add(dataSnapshot.child("lesson"+i).child(j+"").child("state").getValue().toString());
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

         Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                for (int i = 0; i < progressList.size()-1; i++) {
//                    Log.d("ListaaaProgress", "onStart: "+ progressList.get(i).getName()+" lesson1: "+ progressList.get(i).getLesson1().get(i)+"lesson2: "+progressList.get(i).getLesson2().get(i));
//                }
                teacherProgressAdapter.addProgress(progressList);

            }
        }, 2000);



    }
}

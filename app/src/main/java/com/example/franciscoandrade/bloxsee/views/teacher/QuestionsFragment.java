package com.example.franciscoandrade.bloxsee.views.teacher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Level;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class QuestionsFragment extends Fragment {

    private List<Level> levelList= new ArrayList <>();
    private RecyclerView recyclerView;
    private Button send;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_questions, container, false);
        Log.d(TAG, "onCreateView: I am on question fragment");

        setUpViews(view);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        levelList.add(new Level("Level One", "one", "two", "three", "four", "five"));
        levelList.add(new Level("Level Two", "one", "two", "three", "four", "five"));
        levelList.add(new Level("Level Three", "one", "two", "three", "four", "five"));

        recyclerView.setAdapter(new TeacherQuestionAdapter(levelList));

        return view;
    }

    private void setUpViews(View view) {
        send = view.findViewById(R.id.send_button);
        recyclerView= view.findViewById(R.id.question_recyclerview);
    }
}

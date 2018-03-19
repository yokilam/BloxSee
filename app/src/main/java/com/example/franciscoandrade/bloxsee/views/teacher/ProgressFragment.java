package com.example.franciscoandrade.bloxsee.views.teacher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Progress;

import java.util.ArrayList;
import java.util.List;

public class ProgressFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Progress> progressList= new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_progress, container, false);

        recyclerView= view.findViewById(R.id.progress_recyclerview);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        progressList.add(new Progress("Francisco", "right", "wrong", "right", "wrong", "right"));
        progressList.add(new Progress("Joanne", "right", "right", "wrong", "wrong", "right"));
        progressList.add(new Progress("Kevin", "right", "wrong", "wrong", "right", "wrong"));
        progressList.add(new Progress("Yoki", "wrong", "right", "wrong", "right", "wrong"));

        recyclerView.setAdapter(new TeacherProgressAdapter(progressList));

        return view;
    }



}

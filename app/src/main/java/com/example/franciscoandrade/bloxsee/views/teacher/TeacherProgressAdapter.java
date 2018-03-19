package com.example.franciscoandrade.bloxsee.views.teacher;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Progress;

import java.util.ArrayList;
import java.util.List;

public class TeacherProgressAdapter extends RecyclerView.Adapter<TeacherProgressViewHolder> {

    private List<Progress> progressList= new ArrayList <>();

    public TeacherProgressAdapter(List <Progress> progressList) {
        this.progressList = progressList;
    }

    @Override
    public TeacherProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_progress_itemview, parent, false);
        return new TeacherProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeacherProgressViewHolder holder, int position) {
        holder.onBind(getProgressList().get(position));
    }

    @Override
    public int getItemCount() {
        return getProgressList().size();
    }

    public List <Progress> getProgressList() {
        return progressList;
    }
}

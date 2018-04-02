package com.example.franciscoandrade.bloxsee.views.teacher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Progress;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TeacherProgressAdapter extends RecyclerView.Adapter<TeacherProgressViewHolder> {

    private List<Progress> progressLis;
    Context context;

    public TeacherProgressAdapter(Context context) {
        this.context= context;
        progressLis= new ArrayList<>();
    }

    @Override
    public TeacherProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_progress_itemview, parent, false);
        return new TeacherProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeacherProgressViewHolder holder, int position) {
        holder.onBind(progressLis.get(position), context);
    }

    @Override
    public int getItemCount() {
        return progressLis.size();
    }

    public void addProgress(Set<Progress> progressList) {
        this.progressLis.clear();
        notifyDataSetChanged();
        this.progressLis.addAll(progressList);
        notifyDataSetChanged();
    }
}

package com.example.franciscoandrade.bloxsee.views.teacher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Progress;
import com.example.franciscoandrade.bloxsee.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TeacherRosterAdapter extends RecyclerView.Adapter<TeacherRosterViewHolder> {

    private List<Student> studentList;
    private Context context;

    public TeacherRosterAdapter(Context context) {
        studentList= new ArrayList <>();
        this.context= context;
    }

    @Override
    public TeacherRosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_roster_itemview, parent, false);
        return new TeacherRosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeacherRosterViewHolder holder, int position) {
        holder.onBind(studentList.get(position), position, studentList.size());

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void addStudents(List<Student> studentList) {
        this.studentList.clear();
        notifyDataSetChanged();
        this.studentList.addAll(studentList);
        notifyDataSetChanged();
    }

}

package com.example.franciscoandrade.bloxsee.views.teacher;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Student;

import java.util.ArrayList;
import java.util.List;

public class TeacherRosterAdapter extends RecyclerView.Adapter<TeacherRosterViewHolder> {

    private List<Student> studentList= new ArrayList <>();

    public TeacherRosterAdapter(List <Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public TeacherRosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_roster_itemview, parent, false);
        return new TeacherRosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeacherRosterViewHolder holder, int position) {
        holder.onBind(getStudentList().get(position), position, studentList.size());
    }

    @Override
    public int getItemCount() {
        return getStudentList().size();
    }

    public List <Student> getStudentList() {
        return studentList;
    }
}

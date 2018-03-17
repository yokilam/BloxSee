package com.example.franciscoandrade.bloxsee.views.teacher;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Student;

/**
 * Created by joannesong on 3/15/18.
 */

public class TeacherRosterViewHolder extends RecyclerView.ViewHolder {
    private TextView studentName;
    private TextView studentPassword;

    public TeacherRosterViewHolder(View itemView) {
        super(itemView);

        studentName = itemView.findViewById(R.id.student_name);
        studentPassword = itemView.findViewById(R.id.student_password);
    }

    public void onBind(Student student) {
        studentName.setText(student.getName());
        studentPassword.setText(student.getPassword());
    }
}

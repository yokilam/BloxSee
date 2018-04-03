package com.example.franciscoandrade.bloxsee.views.teacher;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Student;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherRosterViewHolder extends RecyclerView.ViewHolder {
    private TextView studentName;
    private TextView studentPassword;
    private TextView bottomLine;
    private ImageView dot_item;
    CircleImageView profile_image;

    public TeacherRosterViewHolder(View itemView) {
        super(itemView);

        studentName = itemView.findViewById(R.id.student_name);
        studentPassword = itemView.findViewById(R.id.student_password);
        dot_item = itemView.findViewById(R.id.dot_item);
        profile_image = itemView.findViewById(R.id.profile_image);
        bottomLine = itemView.findViewById(R.id.bottomLine);
    }

    public void onBind(Student student, int position, int size) {
        studentName.setText(student.getName());
        studentPassword.setText(student.getPassword());
//        listPassword.add("Penguin");
//        listPassword.add("Ghost");
//        listPassword.add("Dog");
//        listPassword.add("Cat");
//        listPassword.add("Dragon");
//        listPassword.add("Octopus");
        if (position==size-1){
            bottomLine.setVisibility(View.INVISIBLE);
        }

        switch (student.getPassword()) {
            case "Penguin":
                profile_image.setImageResource(R.drawable.penguin_color);
                break;
            case "Duck":
                profile_image.setImageResource(R.drawable.duck_color);
                break;
            case "Dog":
                profile_image.setImageResource(R.drawable.dog_color);
                break;
            case "Monkey":
                profile_image.setImageResource(R.drawable.monkey_color);
                break;
            case "Pig":
                profile_image.setImageResource(R.drawable.pig_color);
                break;
            case "Seal":
                profile_image.setImageResource(R.drawable.seal_color);
                break;
            default:
                break;
        }
    }
}

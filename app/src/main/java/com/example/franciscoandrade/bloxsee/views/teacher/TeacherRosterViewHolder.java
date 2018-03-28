package com.example.franciscoandrade.bloxsee.views.teacher;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Student;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by joannesong on 3/15/18.
 */

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
                profile_image.setImageResource(R.drawable.penguin);

                break;

            case "Ghost":
                profile_image.setImageResource(R.drawable.ghost);

                break;


            case "Dog":
                profile_image.setImageResource(R.drawable.dog);

                break;


            case "Cat":
                profile_image.setImageResource(R.drawable.cat);

                break;

            case "Dragon":
                profile_image.setImageResource(R.drawable.dragon);

                break;

            case "Octopus":
                profile_image.setImageResource(R.drawable.octopus);

                break;
            default:


                break;


        }


    }
}

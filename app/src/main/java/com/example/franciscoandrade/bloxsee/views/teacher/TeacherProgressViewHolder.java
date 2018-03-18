package com.example.franciscoandrade.bloxsee.views.teacher;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.franciscoandrade.bloxsee.R;

/**
 * Created by joannesong on 3/15/18.
 */

public class TeacherProgressViewHolder extends RecyclerView.ViewHolder{
    private TextView name;

    public TeacherProgressViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name_progress);
    }
}

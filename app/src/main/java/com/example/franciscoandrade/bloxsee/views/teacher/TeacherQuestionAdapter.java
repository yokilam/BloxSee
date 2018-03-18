package com.example.franciscoandrade.bloxsee.views.teacher;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yokilam on 3/18/18.
 */

public class TeacherQuestionAdapter extends RecyclerView.Adapter<TeacherQuestionViewHolder> {

    private List<Level> levelList= new ArrayList <>();

    public TeacherQuestionAdapter(List <Level> levelList) {
        this.levelList = levelList;
    }

    @Override
    public TeacherQuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_question_itemview, parent, false);
        return new TeacherQuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeacherQuestionViewHolder holder, int position) {
        holder.onBind(getLevelList().get(position));
    }

    @Override
    public int getItemCount() {
        return getLevelList().size();
    }

    public List <Level> getLevelList() {
        return levelList;
    }
}

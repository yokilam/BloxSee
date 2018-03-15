package com.example.franciscoandrade.bloxsee.views;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.views.teacher.TeacherMainPageActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherSignInFragment extends Fragment {
    private Button signInButton;

    public TeacherSignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_teacher_sign_in, container, false);

        signInButton = view.findViewById(R.id.SignIn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TeacherMainPageActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}

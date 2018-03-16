package com.example.franciscoandrade.bloxsee.views;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.views.teacher.TeacherMainPageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Transaction;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherSignInFragment extends Fragment {
    private View view;
    private Button signInButton, signUp_Btn;

    private EditText email_ET, name_ET, password_ET;
    private Button signUp;
    private LinearLayout signUp_container,signIn_container ;

    private String nameText, emailText, passwordText;

    //1.Firebase
    private FirebaseAuth mAuth;
    private ProgressBar progress;

    private EditText email_edittext, password_edittext;

    public TeacherSignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_teacher_sign_in, container, false);

        signInButton = view.findViewById(R.id.SignIn);
        signUp_Btn = view.findViewById(R.id.signUp_Btn);

        email_ET=view.findViewById(R.id.email_ET);
        name_ET=view.findViewById(R.id.name_ET);
        password_ET=view.findViewById(R.id.password_ET);
        signUp=view.findViewById(R.id.signUp);
        progress=view.findViewById(R.id.progress);
        signIn_container=view.findViewById(R.id.signIn_container);
        signUp_container=view.findViewById(R.id.signUp_container);

        login();
        signUp();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });

        return view;
    }

    private void signUp() {
        signUp_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn_container.setVisibility(View.GONE);
                signUp_container.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                mAuth= FirebaseAuth.getInstance();
            }
        });
    }



    private void startRegister() {
        progress.setVisibility(View.VISIBLE);
        nameText= name_ET.getText().toString();
        emailText= email_ET.getText().toString();
        passwordText= password_ET.getText().toString();
        if(!TextUtils.isEmpty(nameText) && !TextUtils.isEmpty(emailText) && !TextUtils.isEmpty(passwordText)){
            mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progress.setVisibility(View.GONE);
                                String user_id= mAuth.getCurrentUser().getUid();
                                Toast.makeText(getActivity(), user_id, Toast.LENGTH_SHORT).show();
                                name_ET.setText("");
                                email_ET.setText("");
                                password_ET.setText("");
                                signIn_container.setVisibility(View.VISIBLE);
                                signUp_container.setVisibility(View.GONE);
                            }
                        }
                    });

        }



    }

    private void login() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TeacherMainPageActivity.class);
                startActivity(intent);
            }
        });
    }




}

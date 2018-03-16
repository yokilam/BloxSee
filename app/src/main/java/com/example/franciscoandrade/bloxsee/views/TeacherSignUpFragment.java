package com.example.franciscoandrade.bloxsee.views;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.franciscoandrade.bloxsee.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherSignUpFragment extends Fragment {

    private View v;
    private EditText email_ET, name_ET, password_ET;
    private Button signUp;

    private String nameText, emailText, passwordText;

    //1.Firebase
    private FirebaseAuth mAuth;
    private ProgressBar progress;


    public TeacherSignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_teacher_sign_up, container, false);

        email_ET=v.findViewById(R.id.email_ET);
        name_ET=v.findViewById(R.id.name_ET);
        password_ET=v.findViewById(R.id.password_ET);
        signUp=v.findViewById(R.id.signUp_Btn);
        progress=v.findViewById(R.id.progress);

        progress.setVisibility(View.GONE);
        mAuth= FirebaseAuth.getInstance();
        click();

        return v;

    }

    private void click() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
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
                                onDestroyView();
                            }
                        }
                    });

        }


    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.container_SignUp)).commit();
//        super.onDestroyView();
//    }
}

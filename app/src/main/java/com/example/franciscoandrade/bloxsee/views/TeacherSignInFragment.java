package com.example.franciscoandrade.bloxsee.views;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Teacher;
import com.example.franciscoandrade.bloxsee.views.teacher.TeacherMainPageActivity;
import com.github.aakira.expandablelayout.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.SharedPreferences;


import static android.content.Context.MODE_PRIVATE;

public class TeacherSignInFragment extends Fragment {
    private final String TAG = "SAVED_INFO:";
    private View view;
    private Button signInButton;
    private EditText email_ET, name_ET, password_ET;
    private TextInputEditText email_edittext, password_edittext;
    private Button signUp;
    private TextView signUp_Btn;
    private LinearLayout signUp_container, signIn_container;
    private String nameText, emailText, passwordText;
    private String passwordLogin, emailLogin;
    private String teacherName;
    //1.Firebase
    private FirebaseAuth mAuth;
    private ProgressBar progress;
    private DatabaseReference ref;
    private DatabaseReference teacherRef;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthListener;
    CheckBox remeber_checkBox;
    //SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
    public static final String MyPREFERENCES = "myprefs";
    public static final String value = "key";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_teacher_sign_in, container, false);
        setUpViews();
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        teacherRef = database.getReference("teacher");
        nameText = name_ET.getText().toString();
        emailText = email_ET.getText().toString();
        passwordText = password_ET.getText().toString();
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

    @Override
    public void onResume() {
        super.onResume();

        String email = sharedpreferences.getString("email", "");
        String pass = sharedpreferences.getString("password", "");

        if (!email.equals("") & !pass.equals("")) {
            email_edittext.setText(sharedpreferences.getString("email", ""));
            password_edittext.setText(sharedpreferences.getString("password", ""));
            remeber_checkBox.setChecked(true);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    //setting all views
    private void setUpViews() {
        signInButton = view.findViewById(R.id.signIn);
        signUp_Btn = view.findViewById(R.id.signUp_Btn);
        email_ET = view.findViewById(R.id.email_ET);
        name_ET = view.findViewById(R.id.name_ET);
        //goToTeacher = view.findViewById(R.id.goToTeache);
        password_ET = view.findViewById(R.id.password_ET);
        signUp = view.findViewById(R.id.signUp);
        progress = view.findViewById(R.id.progress);
        signIn_container = view.findViewById(R.id.signIn_container);
        signUp_container = view.findViewById(R.id.signUp_container);
        email_edittext = view.findViewById(R.id.email_edittext);
        password_edittext = view.findViewById(R.id.password_edittext);
        remeber_checkBox = view.findViewById(R.id.remeber_checkBox);
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Hides Sign in container and shows Sign UP container
     * gets instance of firebase to authenticate
     */
    private void signUp() {
        signUp_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn_container.setVisibility(View.GONE);
                signUp_container.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Handles User Creation if not exist
     * Adds User to Db if successfully added as user
     */
    private void startRegister() {
        progress.setVisibility(View.VISIBLE);
        nameText = name_ET.getText().toString();
        emailText = email_ET.getText().toString();
        passwordText = password_ET.getText().toString();
        if (!TextUtils.isEmpty(nameText) && !TextUtils.isEmpty(emailText) && !TextUtils.isEmpty(passwordText)) {
            mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(new OnCompleteListener <AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task <AuthResult> task) {
                            if (task.isSuccessful()) {
                                progress.setVisibility(View.GONE);
                                String user_id = mAuth.getCurrentUser().getUid();
                                Toast.makeText(getActivity(), user_id, Toast.LENGTH_SHORT).show();
                                signIn_container.setVisibility(View.VISIBLE);
                                signUp_container.setVisibility(View.GONE);
                                createNewUser(nameText, emailText, user_id);
                                emptyEditText();
                            }

                            task.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progress.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "Login Failed User exist already", Toast.LENGTH_SHORT).show();
                                    emptyEditText();
                                }
                            });
                        }
                    });
        }
    }

    /**
     * @param name
     * @param email
     * @param user_id Method Runs if user succesfully created a new account
     *                Creates a new instance of Teacher which is added to the FireBase DataBase to teacher section
     */
    private void createNewUser(String name, String email, String user_id) {
        Teacher teacher = new Teacher(name, email, user_id);
        ref.child("teacher").child(name).setValue(teacher);
    }

    /**
     * Method checks if user has valid credentials
     * If user approved, user can access their account
     */
    private void login() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInLogic();
            }
        });
    }

    private void signInLogic() {
        passwordLogin = password_edittext.getText().toString();
        emailLogin = email_edittext.getText().toString();

        if (!TextUtils.isEmpty(emailLogin) && !TextUtils.isEmpty(passwordLogin)) {
            mAuth.signInWithEmailAndPassword(emailLogin, passwordLogin)
                    .addOnCompleteListener(new OnCompleteListener <AuthResult>() {
                        @Override

                        public void onComplete(@NonNull Task <AuthResult> task) {
                            if (task.isSuccessful()) {
                                saveCredentials(emailLogin, passwordLogin);
                                /* checks the database where if the email equals to the email that the user type and sign in successfully,
                                    then addChildEventListener to get the ref's key, which is equal to the name of the teacher.
                                 */
                                teacherRef.orderByChild("email").equalTo(emailLogin).addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        teacherName = dataSnapshot.getRef().getKey();
                                        intentToTeacherMainPageActivity(teacherName);
                                    }

                                    @Override
                                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                                    @Override
                                    public void onChildRemoved(DataSnapshot dataSnapshot) {}

                                    @Override
                                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {}
                                });

                                password_edittext.setText("");
                                email_edittext.setText("");
                            }

                            task.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    password_edittext.setText("");
                                    email_edittext.setText("");
                                }
                            });
                        }
                    });
        }
    }

    private void saveCredentials(String email, String password) {
        editor = sharedpreferences.edit();
        if (remeber_checkBox.isChecked()) {
            editor.putString("email", email);
            editor.putString("password", password);
        } else {
            editor.putString("email", "");
            editor.putString("password", "");
        }
        editor.apply();
    }

    private void intentToTeacherMainPageActivity(String teacherName) {
        Intent intent = new Intent(view.getContext(), TeacherMainPageActivity.class);
        intent.putExtra("teacherName", teacherName);
        Log.d("intent==", "intentToTeacherMainPageActivity: " + teacherName);
        view.getContext().startActivity(intent);
    }

    /**
     * Empty Editext Text
     */
    private void emptyEditText() {
        name_ET.setText("");
        email_ET.setText("");
        password_ET.setText("");
    }

}

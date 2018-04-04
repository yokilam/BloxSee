package com.example.franciscoandrade.bloxsee.views.student;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.util.Screenshot;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlocklyActivity extends AbstractBlocklyActivity implements BlocklyListener {

    private View root;
    private ImageView sprite;
    private AnimatorSet animSetXY;
    private List <Animator> animSequenceArr;
    private TextView questionTV;

    private com.github.clans.fab.FloatingActionButton playFab;
    private List <FloatingActionMenu> menus = new ArrayList <>();

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Bitmap b;
    private FrameLayout blockContainer;

    private Dialog dialog;
    private LottieAnimationView lottieAnimationView;

    private String snapShotLesson;
    private String snapShotQuestion;

    private String snapShotName;

    //Joanne*** These are the values you need
    String user;
    String currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar.hide();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        getInfo();
    }

    @Override
    protected View onCreateContentView(int containerId) {
        root = getLayoutInflater().inflate(R.layout.activity_blockly, null);
        questionTV = root.findViewById(R.id.blockly_activity_student_question);
        sprite = root.findViewById(R.id.sprite);
        playFab = root.findViewById(R.id.play_fab);
        blockContainer = findViewById(R.id.blockContainer);
        getInfo();
        return root;
    }

    @Override
    protected void onResume() {
        super.onResume();

        playFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                b = Screenshot.takeScreenShotofRootView(root);
                //image.setImageBitmap(b)
                if (getController().getWorkspace().hasBlocks()) {
                    Log.d("runcode", "uhoh");
                    onRunCode();
                } else {
                    Log.i("hihi", "No blocks in workspace. Skipping run request.");
                }
                uploadImage();
            }
        });

    }

    private void uploadImage() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference imagesRef = storageReference.child("images/" + snapShotName + ".jpg");

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener <UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                // Do what you want
            }
        });

        Toast.makeText(getApplicationContext(),"save", Toast.LENGTH_SHORT).show();

    }

    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return "toolbox.xml";
    }

    @NonNull
    @Override
    protected List <String> getBlockDefinitionsJsonPaths() {
        return Arrays.asList(
                "blocks_one.json",
                "blocks_two.json",
                "blocks_three.json",
                "blocks_four.json"
        );
    }

    private static final List <String> JAVASCRIPT_GENERATORS = Arrays.asList(
            "generator_one.js"
    );

    @NonNull
    @Override
    protected List <String> getGeneratorsJsPaths() {
        return JAVASCRIPT_GENERATORS;
    }

    CodeGenerationRequest.CodeGeneratorCallback mCodeGeneratorCallback =
            new BlocklyGenerator(this, "LoggingTag", this);

    @NonNull
    @Override
    protected CodeGenerationRequest.CodeGeneratorCallback getCodeGenerationCallback() {
        return mCodeGeneratorCallback;
    }

    @Override
    protected void onInitBlankWorkspace() {
        getController().addVariable("item");
    }

    @Override
    public void sendGeneratedCode(String str) {

        animSequenceArr = new ArrayList <>();
        animSetXY = new AnimatorSet();
        animSetXY.setDuration(5000);
        ObjectAnimator animMove10 = ObjectAnimator.ofFloat(sprite, "x", sprite.getX(), sprite.getX() + 100f);
        ObjectAnimator animMoveUp = ObjectAnimator.ofFloat(sprite, "y", sprite.getY(), sprite.getY() - 100f);
        ObjectAnimator animMoveLeft = ObjectAnimator.ofFloat(sprite, "x", sprite.getX(), sprite.getX() - 100f);
        ObjectAnimator animMoveRight = ObjectAnimator.ofFloat(sprite, "x", sprite.getX(), sprite.getX() + 100f);
        ObjectAnimator animMoveDown = ObjectAnimator.ofFloat(sprite, "y", sprite.getY(), sprite.getY() + 100f);

        String[] removeIndentArr = str.split("\n");
        Log.d("hihi", "in sendGC");

        for (int i = 0; i < removeIndentArr.length; i++) {
            Log.d("hihi", "in forloop");
            Log.d("hihi", removeIndentArr[i]);
            switch (removeIndentArr[i]) {
                case "start":
                    Log.d("hihi", "start");
                    break;
                case "move":
                    Log.d("hihi", "I'm in move");
                    animSequenceArr.add(animMove10);
                    break;
                case "moveup":
                    animSequenceArr.add(animMoveUp);
                    Log.d("hihi", "I'm in moveup");
                    break;
                case "moveleft":
                    animSequenceArr.add(animMoveLeft);
                    break;
                case "moveright":
                    animSequenceArr.add(animMoveRight);
                    break;
                case "movedown":
                    animSequenceArr.add(animMoveDown);
                    break;
            }
        }
        animSetXY.playSequentially(animSequenceArr);
        animSetXY.start();

        if (checkAnswer(str)) {
            runOnAnotherThread("goodjob.json");
        } else {
            runOnAnotherThread("tryagain.json");
        }
    }

    public boolean checkAnswer(String str) {
        String answerKey = "start\n" + "moveright\n" + "movedown\n";
        if (str.equals(answerKey)) {
            Log.d("hihi", str + "hihi");
            Log.d("hihi", answerKey);
            return true;
        } else {
            Log.d("hihi", str + "hihi");
            return false;
        }

    }

    private void setUpDialog(String json) {
        dialog = new Dialog(BlocklyActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        lottieAnimationView = dialog.findViewById(R.id.goodjob);
        lottieAnimationView.setAnimation(json);
        lottieAnimationView.playAnimation();
        dialog.show();
    }

    public void getInfo() {

        Intent intent = getIntent();
        user = intent.getStringExtra("studentName");
        currentQuestion = intent.getStringExtra("currentQuestion");
        snapShotLesson = intent.getStringExtra("SnapShotL");
        snapShotQuestion = intent.getStringExtra("SnapShotQ");

        questionTV.setText(currentQuestion);

        snapShotName = user  + snapShotLesson  + snapShotQuestion;

        Log.d("SSHIHI", snapShotName);

        Log.d("STUDENT", "getInfo: " + user);
        Log.d("STUDENT", "getInfo: " + currentQuestion);
    }

    public void runOnAnotherThread(final String json) {
        Handler refresh = new Handler(Looper.getMainLooper());
        refresh.post(new Runnable() {
            public void run() {
                setUpDialog(json);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog!= null && dialog.isShowing()) {
            dialog.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog!= null && dialog.isShowing()) {
            dialog.cancel();
        }
    }
}

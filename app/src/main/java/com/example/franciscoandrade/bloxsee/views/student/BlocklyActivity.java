package com.example.franciscoandrade.bloxsee.views.student;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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
    private List<Animator> animSequenceArr;
    private TextView questionTV;

    private FloatingActionMenu menu_yellow;
    private com.github.clans.fab.FloatingActionButton  fab22, fab32;
    private List<FloatingActionMenu> menus= new ArrayList<>();

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Bitmap b;
    private FrameLayout blockContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sprite = findViewById(R.id.sprite);
        mActionBar.hide();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

    }

    @Override
    protected View onCreateContentView(int containerId) {
        root = getLayoutInflater().inflate(R.layout.activity_blockly, null);
        //questionTV = root.findViewById(R.id.blockly_activity_student_question);

        menu_yellow = root.findViewById(R.id.menu_yellow);

        fab22 = root.findViewById(R.id.fab22);
        fab32 = root.findViewById(R.id.fab32);
        blockContainer= findViewById(R.id.blockContainer);

        return root;
    }

    @Override
    protected void onResume() {
        super.onResume();
        menus.add(menu_yellow);
        menu_yellow.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                String text;
                if (opened) {
                    text = "Menu opened";

                } else {
                    text = "Menu closed";
                }
            }
        });

        fab22.setImageResource(R.drawable.ic_save_black_24dp);
        fab32.setImageResource(R.drawable.ic_play);

        fab22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  b = Screenshot.takeScreenShotofRootView(root);
                // image.setImageBitmap(b)

                uploadImage();
            }
        });

        fab32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getController().getWorkspace().hasBlocks()) {
                    onRunCode();
                } else {
                    Log.i("hihi", "No blocks in workspace. Skipping run request.");
                }

            }
        });

    }

    private void uploadImage() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference imagesRef = storageReference.child("images/Test1.jpg");

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                // Do what you want
            }
        });
/*
            if(b != null)
            {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                StorageReference ref = storageReference.child("images/image5");
                ref.putFile(b)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(BlocklyActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(BlocklyActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            }
                        });
            }*/
        }

    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return "toolbox.xml";
    }

    @NonNull
    @Override
    protected List<String> getBlockDefinitionsJsonPaths() {
        return Arrays.asList(
                "blocks_one.json",
                "blocks_two.json",
                "blocks_three.json",
                "blocks_four.json"
        );
    }
    private static final List<String> JAVASCRIPT_GENERATORS = Arrays.asList(
            "generator_one.js"
    );
    @NonNull
    @Override
    protected List<String> getGeneratorsJsPaths() {
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

        animSequenceArr = new ArrayList<>();
        animSetXY = new AnimatorSet();
        animSetXY.setDuration(5000);
        ObjectAnimator animMove10 = ObjectAnimator.ofFloat(sprite, "x", sprite.getX(), sprite.getX() + 100f);
        ObjectAnimator animMoveUp = ObjectAnimator.ofFloat(sprite, "y", sprite.getY(), sprite.getY() - 100f);
        ObjectAnimator animMoveLeft = ObjectAnimator.ofFloat(sprite, "x", sprite.getX(), sprite.getX() - 100f);
        ObjectAnimator animMoveRight = ObjectAnimator.ofFloat(sprite, "x", sprite.getX(), sprite.getX() + 100f);
        ObjectAnimator animMoveDown = ObjectAnimator.ofFloat(sprite, "y", sprite.getY(), sprite.getY() + 100f);

        String[] removeIndentArr = str.split("\n");
        Log.d("hihi", "in sendGC");

        for(int i = 0; i<removeIndentArr.length; i++){
            Log.d("hihi", "in forloop");
            Log.d("hihi", removeIndentArr[i]);
            switch(removeIndentArr[i]){
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

        checkAnswer(str);
    }

    public boolean checkAnswer(String str){
        String answerKey = "start\n" + "moveright\n" + "movedown\n";
        if(str.equals(answerKey)){
            Log.d("hihi", str + "hihi");
            Log.d("hihi", answerKey);
            Toast.makeText(getApplicationContext(),"yay! you did it!", Toast.LENGTH_LONG).show();
            return true;
        }
        else{
            Toast.makeText(getApplicationContext(),"try again", Toast.LENGTH_SHORT).show();
            Log.d("hihi", str + "hihi");
            return false;
        }
    }

}

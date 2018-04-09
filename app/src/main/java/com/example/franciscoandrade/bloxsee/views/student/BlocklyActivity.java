package com.example.franciscoandrade.bloxsee.views.student;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
    Intent intent;


    //BLuettoth
    private boolean isBtConnected = false;
    BluetoothAdapter myBluetooth = null;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    String address="20:13:10:25:40:47";
    BluetoothSocket btSocket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ConnectBT().execute();
        mActionBar.hide();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //getInfo();


        if(savedInstanceState!=null){
            user= savedInstanceState.getString("studentName");
            currentQuestion= savedInstanceState.getString("currentQuestion");
            snapShotLesson= savedInstanceState.getString("SnapShotL");
            snapShotQuestion= savedInstanceState.getString("SnapShotQ");

        }
        else {
            intent = getIntent();
            user = intent.getStringExtra("studentName");
            currentQuestion = intent.getStringExtra("currentQuestion");
            snapShotLesson = intent.getStringExtra("SnapShotL");
            snapShotQuestion = intent.getStringExtra("SnapShotQ");
        }

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
                    //sends signal to arduino
                    onRunCode();

                    //moveCar();
                } else {
                    Log.i("hihi", "No blocks in workspace. Skipping run request.");
                }
                uploadImage();
            }
        });


        user = intent.getStringExtra("studentName");
        currentQuestion = intent.getStringExtra("currentQuestion");
        snapShotLesson = intent.getStringExtra("SnapShotL");
        snapShotQuestion = intent.getStringExtra("SnapShotQ");

    }

    private void moveCar() {

        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("5".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }


    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.d("SAVEINSTANCE", "onSaveInstanceState: "+user);
        Log.d("SAVEINSTANCE", "onSaveInstanceState: "+currentQuestion);
        Log.d("SAVEINSTANCE", "onSaveInstanceState: "+snapShotLesson);
        Log.d("SAVEINSTANCE", "onSaveInstanceState: "+snapShotQuestion);
        outState.putString("studentName", user);
        outState.putString("currentQuestion", currentQuestion);
        outState.putString("SnapShotL", snapShotLesson);
        outState.putString("SnapShotQ", snapShotQuestion);
        super.onSaveInstanceState(outState, outPersistentState);
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
    public void sendGeneratedCode(final String str) {

        Log.d("Joannecode", "bActivity: " + str);

        animSequenceArr = new ArrayList <>();
        animSetXY = new AnimatorSet();
        animSetXY.setDuration(2000);
        ObjectAnimator animMove10 = ObjectAnimator.ofFloat(sprite, "x", sprite.getX(), sprite.getX() + 100f);
        ObjectAnimator animMoveUp = ObjectAnimator.ofFloat(sprite, "y", sprite.getY(), sprite.getY() - 300f);
        ObjectAnimator animMoveLeft = ObjectAnimator.ofFloat(sprite, "x", sprite.getX(), sprite.getX() - 555f);
        ObjectAnimator animMoveRight = ObjectAnimator.ofFloat(sprite, "x", sprite.getX(), sprite.getX() + 555f);
        ObjectAnimator animMoveDown = ObjectAnimator.ofFloat(sprite, "y", sprite.getY(), sprite.getY() + 555f);

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
                case "repeat":
                    animSequenceArr.add(animMoveDown);
                    animSequenceArr.add(animMoveRight);
                    animSequenceArr.add(animMoveUp);
                    animSequenceArr.add(animMoveLeft);
                    break;
            }
        }
        animSetXY.playSequentially(animSequenceArr);
        //Toast.makeText(this, animSequenceArr.toString(), Toast.LENGTH_SHORT).show();
        animSetXY.start();

        animSetXY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (checkAnswer(str) ) {
                    runOnAnotherThread("goodjob.json");
                } else {
                    runOnAnotherThread("tryagain.json");
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    public boolean checkAnswer(String str) {
        String answerKey = "start\n" + "repeat\n";
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

    private class ConnectBT extends AsyncTask<Void, Void, Void> { // UI thread
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            //progress = ProgressDialog.show(ledControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                //finish();
            } else {
                msg("Connected.");
                isBtConnected = true;
            }
            //progress.dismiss();
        }
    }


    // fast way to call Toast
    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }


}

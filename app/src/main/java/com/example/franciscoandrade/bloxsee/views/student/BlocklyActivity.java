package com.example.franciscoandrade.bloxsee.views.student;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.franciscoandrade.bloxsee.R;
import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;
import com.google.blockly.android.codegen.LoggingCodeGeneratorCallback;
import com.google.blockly.model.DefaultBlocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlocklyActivity extends AbstractBlocklyActivity implements BlocklyListener {

    private ImageView sprite;
    private AnimatorSet animSetXY;
    private ArrayList<Animator> animSequenceArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sprite = findViewById(R.id.sprite);

    }

    @Override
    protected View onCreateContentView(int containerId) {
        View root = getLayoutInflater().inflate(R.layout.activity_blockly, null);
        return root;
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
//        final AnimationSet as = new AnimationSet(true);;
//        as.setFillAfter(true);
//        as.setDuration(5000);
        animSetXY = new AnimatorSet();
        animSetXY.setDuration(5000);

        String[] removeIndentArr = str.split("\n");

        for(int i = 0; i<removeIndentArr.length; i++){
            switch(removeIndentArr[i]){
                case "start":
                    break;
                case "move":
                    ObjectAnimator animMove10 = ObjectAnimator.ofFloat(sprite, "x", 50f,20f);
                    animSequenceArr.add(animMove10);
                    //animSetXY.playSequentially(animMove10);
                    Log.d("hihi", "I'm n move");
                    break;
                case "moveup":
                    ObjectAnimator animMoveUp = ObjectAnimator.ofFloat(sprite, "ScaleY", 1, 0);
                    animSequenceArr.add(animMoveUp);
                    Log.d("hihi", "I'm n moveup");
            }

        }
        animSetXY.playSequentially(animSequenceArr);
        animSetXY.start();

    }
}

























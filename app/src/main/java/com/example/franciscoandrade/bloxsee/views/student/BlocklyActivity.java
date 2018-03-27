package com.example.franciscoandrade.bloxsee.views.student;

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
    private TranslateAnimation animation;
    private TranslateAnimation animation2;

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
        final AnimationSet as = new AnimationSet(true);;
        as.setFillAfter(true);
        as.setDuration(5000);

        Log.d("hijoanne", str);
        String[] removeIndentArr = str.split("\n");
        Log.d("hihi", String.valueOf(removeIndentArr));
        Log.d("hihi", String.valueOf(removeIndentArr.length));
        for(int i = 0; i<removeIndentArr.length; i++){
            Log.d("hihi", removeIndentArr[i]);
            Log.d("hihi", String.valueOf(removeIndentArr.length));

            switch (removeIndentArr[i]){

                case "start":
                    break;
                case "move":

                    animation = new TranslateAnimation(0.0f, 100.0f,
                             0.0f, 0.0f);
//                    animation.setDuration(5000);
                    //animation.setFillAfter(true);
                    //animation.setFillEnabled(true);
                    as.addAnimation(animation);
//                    sprite.startAnimation(as);

                    Log.d("hihi", "i'm inside move");

                    break;
                case "moveup":
                    animation2 = new TranslateAnimation(0.0f, 0.0f,
                            0.0f,-300.0f);
//                    animation2.setDuration(5000);
                    //animation.setFillAfter(true);
                    //animation.setFillEnabled(true);
                    as.addAnimation(animation2);


//                    sprite.startAnimation(as);
                    break;
                default:
                    break;

            }


        }

//        Log.d("hihi", String.valueOf(as));
        sprite.startAnimation(as);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//            }
//        }, 5000);

//        sprite.clearAnimation();
//
//        sprite.startAnimation(as1);

    }
}

package com.example.franciscoandrade.bloxsee.views.student;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
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
         final AnimationSet as = new AnimationSet(false);
        AnimationSet as1 = new AnimationSet(true);


        Log.d("hijoanne", str);
        String[] removeIndentArr = str.split("\n");
        Log.d("hihi", removeIndentArr[0]+"-"+removeIndentArr[1]+"-"+removeIndentArr[2]);
        Log.d("hihi", String.valueOf(removeIndentArr.length));

//        animation = new TranslateAnimation(0.0f, 100.0f,
//                0.0f, 0.0f);
//
//        animation2 = new TranslateAnimation(100.0f, 0.0f,
//                0.0f,-300.0f);
//
//
//
//
//        animation.setDuration(5000);
//
//        as.addAnimation(animation);
//
//
//        animation2.setDuration(5000);
//
//        as.addAnimation(animation2);
//        sprite.animate();
//
//        sprite.setAnimation(as);
//        Log.d("hihi", "Finish==="+sprite.getAnimation());
//

        TranslateAnimation animation = new TranslateAnimation(0, 50, 100, 0);
        animation.setDuration(1000);
        animation.setFillAfter(false);
        animation.setAnimationListener(new MyAnimationListener());

        //sprite.startAnimation(animation);
        animation.start();
        animation.cancel();
        Log.d("hihi", "Finish===");

        TranslateAnimation animation2 = new TranslateAnimation(0, 0    , 0, 100);
        animation2.setDuration(5000);
        animation2.setFillAfter(false);
        animation2.setAnimationListener(new MyAnimationListener());

        sprite.startAnimation(animation2);



        /*
        for(int i = 0; i<removeIndentArr.length; i++){
            Log.d("hihi", removeIndentArr[i]);
            Log.d("hihi", String.valueOf(removeIndentArr.length));
            //as = new AnimationSet(true);
            switch (removeIndentArr[i]){

                case "start":
                    break;
                case "move":

                    animation = new TranslateAnimation(0.0f, 100.0f,
                             0.0f, 0.0f);
                    animation.setDuration(5000);
                    //animation.setFillAfter(true);
                    //animation.setFillEnabled(true);
                    as.addAnimation(animation);
//                    sprite.startAnimation(as);

                    Log.d("hihi", "i'm inside move");

                    break;
                case "moveup":
                    animation2 = new TranslateAnimation(0.0f, 0.0f,
                            0.0f,-300.0f);
                    animation2.setDuration(5000);
                    //animation.setFillAfter(true);
                    //animation.setFillEnabled(true);
                    as.addAnimation(animation2);


//                    sprite.startAnimation(as);
                    break;
                default:
                    break;

            }
        }


*/


//        Log.d("hihi", String.valueOf(as));
       // sprite.startAnimation(as);
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


    private class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {
//            sprite.clearAnimation();
//            Toolbar.LayoutParams lp = new Toolbar.LayoutParams(sprite.getWidth(), sprite.getHeight());
//            lp.setMargins(50, 100, 0, 0);
//            sprite.setLayoutParams(lp);

            TranslateAnimation animation3 = new TranslateAnimation(0, 100, 100, 100);
            animation3.setDuration(1000);
            animation3.setFillAfter(false);
            //animation3.setAnimationListener(new MyAnimationListener());
            animation3.start();
            sprite.startAnimation(animation3);


            Log.d("hihi", "onAnimationEnd: ANIMATIONEND");
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            Log.d("hihi", "onAnimationRepeat: ONANIMATIONREPEAT");
        }

        @Override
        public void onAnimationStart(Animation animation) {
            Log.d("hihi", "onAnimationStart: ANIMATIONSTART");

        }

    }
}

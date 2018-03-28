package com.example.franciscoandrade.bloxsee.views.student;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.example.franciscoandrade.bloxsee.R;
import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlocklyActivity extends AbstractBlocklyActivity implements BlocklyListener {

    private ImageView sprite;
    private AnimatorSet animSetXY;
    private List<Animator> animSequenceArr;

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
    }
}

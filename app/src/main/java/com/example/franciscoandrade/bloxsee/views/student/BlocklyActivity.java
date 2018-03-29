package com.example.franciscoandrade.bloxsee.views.student;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.franciscoandrade.bloxsee.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlocklyActivity extends AbstractBlocklyActivity implements BlocklyListener {

    private ImageView sprite;
    private AnimatorSet animSetXY;
    private List<Animator> animSequenceArr;

    FloatingActionMenu menu_yellow;
    com.github.clans.fab.FloatingActionButton  fab22, fab32, fab3;
    private List<FloatingActionMenu> menus= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sprite = findViewById(R.id.sprite);

//        showDialog();

    }

    @Override
    protected View onCreateContentView(int containerId) {
        View root = getLayoutInflater().inflate(R.layout.activity_blockly, null);

        menu_yellow= root.findViewById(R.id.menu_yellow);

        fab22= root.findViewById(R.id.fab22);
        fab32= root.findViewById(R.id.fab32);
        fab3= root.findViewById(R.id.fab3);

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
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });

        fab22.setImageResource(R.drawable.ic_help);
        fab32.setImageResource(R.drawable.ic_play);
        fab3.setImageResource(R.drawable.ic_launcher_background);


        fab22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Fab22 Clicked", Toast.LENGTH_SHORT).show();

            }
        });

        fab32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Fab22 Clicked", Toast.LENGTH_SHORT).show();
                if (getController().getWorkspace().hasBlocks()) {
                    onRunCode();
                } else {
                    Log.i("hihi", "No blocks in workspace. Skipping run request.");
                }

            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kevin put your code here!!
            }
        });

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
//    void showDialog() {
//
//        // DialogFragment.show() will take care of adding the fragment
//        // in a transaction.  We also want to remove any currently showing
//        // dialog, so make our own transaction and take care of that here.
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
//        if (prev != null) {
//            ft.remove(prev);
//        }
//        ft.addToBackStack(null);
//
//        // Create and show the dialog.
//        DialogFragment newFragment = new BlocklyDialogFragment();
//        newFragment.show(fm, "hihi", ft, "hihi");
//    }
}

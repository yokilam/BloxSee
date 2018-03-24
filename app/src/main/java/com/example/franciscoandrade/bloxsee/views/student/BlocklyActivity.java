package com.example.franciscoandrade.bloxsee.views.student;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.franciscoandrade.bloxsee.R;
import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;
import com.google.blockly.android.codegen.LoggingCodeGeneratorCallback;
import com.google.blockly.model.DefaultBlocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlocklyActivity extends AbstractBlocklyActivity {

    private BlocklyRunFragment blocklyRunFragment = new BlocklyRunFragment();
    private FragmentManager fragmentManager;


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
            new LoggingCodeGeneratorCallback(this, "LoggingTag");
    @NonNull
    @Override
    protected CodeGenerationRequest.CodeGeneratorCallback getCodeGenerationCallback() {
        return mCodeGeneratorCallback;
    }
    @Override
    protected void onInitBlankWorkspace() {
        getController().addVariable("item");
    }
}

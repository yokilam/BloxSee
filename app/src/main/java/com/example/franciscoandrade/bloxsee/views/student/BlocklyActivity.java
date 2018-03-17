package com.example.franciscoandrade.bloxsee.views.student;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.franciscoandrade.bloxsee.R;
import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;
import com.google.blockly.android.codegen.LoggingCodeGeneratorCallback;
import com.google.blockly.model.DefaultBlocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlocklyActivity extends AbstractBlocklyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blockly);
    }

    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return "default/toolbox.xml";
    }

    @NonNull
    @Override
    protected List<String> getBlockDefinitionsJsonPaths() {
        List<String> assetPaths = new ArrayList<>(DefaultBlocks.getAllBlockDefinitions());

        return assetPaths;
    }

    private static final List<String> JAVASCRIPT_GENERATORS = Arrays.asList(
            // Custom block generators go here. Default blocks are already included.
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

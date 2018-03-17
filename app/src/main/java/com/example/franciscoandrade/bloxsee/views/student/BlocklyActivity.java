package com.example.franciscoandrade.bloxsee.views.student;

import android.support.annotation.NonNull;

import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;
import com.google.blockly.model.DefaultBlocks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joannesong on 3/17/18.
 */

public class BlocklyActivity extends AbstractBlocklyActivity {
    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return "default/toolbox.xml";
    }

    @NonNull
    @Override
    protected List<String> getBlockDefinitionsJsonPaths() {
        List<String> assetPaths = new ArrayList<>(DefaultBlocks.getAllBlockDefinitions());
        assetPaths.add("default/list_blocks.json");
        assetPaths.add("default/logic_blocks.json");
        assetPaths.add("default/loop_blocks.json");
        assetPaths.add("default/math_blocks.json");
        assetPaths.add("default/test_blocks.json");
        assetPaths.add("default/text_blocks.json");
        assetPaths.add("default/variable_blocks.json");
        assetPaths.add("default/colour_blocks.json");
        assetPaths.add("json/procedure_blocks.json");

        return assetPaths;
    }

    @NonNull
    @Override
    protected List<String> getGeneratorsJsPaths() {
        return null;
    }

    @NonNull
    @Override
    protected CodeGenerationRequest.CodeGeneratorCallback getCodeGenerationCallback() {
        return null;
    }
}

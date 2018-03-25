package com.example.franciscoandrade.bloxsee.views.student;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.blockly.android.codegen.CodeGenerationRequest;

/**
 * Created by joannesong on 3/24/18.
 */

public class BlocklyGenerator implements CodeGenerationRequest.CodeGeneratorCallback{
    private String mTag;
    private Context mContext;
    private BlocklyListener bL;

    public BlocklyGenerator(Context context, String loggingTag, BlocklyListener bL) {
        mTag = loggingTag;
        mContext = context;
        this.bL = bL;
    }

    public BlocklyGenerator() {
    }

    @Override
    public void onFinishCodeGeneration(final String generatedCode) {
        if (generatedCode.isEmpty()) {
            Toast.makeText(mContext, "Something went wrong with code generation.", Toast.LENGTH_LONG).show();
        } else {
            Log.d(mTag, "code: " + generatedCode);
            Toast.makeText(mContext, generatedCode + "hihihi", Toast.LENGTH_LONG).show();
            bL.sendGeneratedCode(generatedCode);

        }
    }

}

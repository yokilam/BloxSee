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

    @Override
    public void onFinishCodeGeneration(final String generatedCode) {
        if (generatedCode.isEmpty()) {
            Log.d("hihi", "gC is empty");
        } else {
            Log.d("Joannecode", generatedCode);
            bL.sendGeneratedCode(generatedCode);

        }
    }

}

package com.example.franciscoandrade.bloxsee;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.Toast;

import com.example.franciscoandrade.bloxsee.views.student.BlocklyGenerator;
import com.example.franciscoandrade.bloxsee.views.student.BlocklyListener;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.blockly.android.ui.CategoryTabs.TAG;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.franciscoandrade.bloxsee", appContext.getPackageName());
    }

//    @Test
//    public void testListener() {
//
//        BlocklyGenerator generator = new BlocklyGenerator(appContext, "TEST", new BlocklyListener() {
//            @Override
//            public void sendGeneratedCode(String str) {
//                Log.d(TAG, "sendGeneratedCode: " + "Listener test");
//            }
//        });
//
//        generator.onFinishCodeGeneration("STUFF");
//
//    }
}

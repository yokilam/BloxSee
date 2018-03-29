package com.example.franciscoandrade.bloxsee.util;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by c4q on 3/29/18.
 */

public class Screenshot {

    public static Bitmap takeScreenShot(View v) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        return b;
    }

    public static Bitmap takeScreenShotofRootView(View v) {
        return takeScreenShot(v.getRootView());
    }
}


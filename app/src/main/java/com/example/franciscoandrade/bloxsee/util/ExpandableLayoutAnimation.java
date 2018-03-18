package com.example.franciscoandrade.bloxsee.util;

import android.animation.ObjectAnimator;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

/**
 * Created by yokilam on 3/18/18.
 */

public class ExpandableLayoutAnimation {

    //Animation
    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    public void changeExpandableLayoutColorAndAnimation(View v, ExpandableLinearLayout expandableLayout, int colorId) {
        //set up the background color for expandable layout
        expandableLayout.setBackgroundColor(ContextCompat.getColor(v.getContext(), colorId));
        //set up the animation when the layout expands
        expandableLayout.setInterpolator(Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR));
    }


}

package com.example.franciscoandrade.bloxsee.util;

import android.animation.ObjectAnimator;
import android.support.v4.content.ContextCompat;
import android.util.SparseBooleanArray;
import android.view.View;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

public class ExpandableLayoutAnimation {

    //Animation
    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    public void changeExpandableLayoutColorAndAnimation(View v, ExpandableLinearLayout expandableLayout, int colorId, final SparseBooleanArray expandState) {
        //set up the background color for expandable layout
        expandableLayout.setBackgroundColor(ContextCompat.getColor(v.getContext(), colorId));
        //set up the animation when the layout expands
        expandableLayout.setInterpolator(Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR));
        expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            //Before the expandable layout opens, the arrow button do the animation of spinning
            public void onPreOpen() {
                expandState.put(0, true);
            }

            @Override
            public void onPreClose() {
                expandState.put(0, false);
            }
        });
        onClickButton(expandableLayout);
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }
}

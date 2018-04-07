package com.example.franciscoandrade.bloxsee.views.student;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.blockly.android.CategorySelectorFragment;
import com.google.blockly.android.control.BlocklyController;
import com.google.blockly.android.ui.CategorySelectorUI;
import com.google.blockly.android.ui.CategoryView;
import com.google.blockly.android.ui.Rotation;
import com.google.blockly.android.ui.WorkspaceHelper;
import com.google.blockly.model.BlocklyCategory;

/**
 * Created by joannesong on 4/5/18.
 */

public class ToolboxCategoryFragment extends CategorySelectorFragment{

    private static final String TAG = "CategorySelectorFragment";

    protected CategoryView mCategoryView;
    protected WorkspaceHelper mHelper;
    protected BlocklyController mController;

    protected int mScrollOrientation = OrientationHelper.VERTICAL;
    protected @Rotation.Enum int mLabelRotation = Rotation.COUNTER_CLOCKWISE;

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                com.google.blockly.android.R.styleable.BlocklyCategory,
                0, 0);

       try {
            mScrollOrientation = a.getInt(com.google.blockly.android.R.styleable.BlocklyCategory_scrollOrientation,
                    mScrollOrientation);
            //noinspection ResourceType
            mLabelRotation = a.getInt(com.google.blockly.android.R.styleable.BlocklyCategory_labelRotation, mLabelRotation);
        } finally {
            a.recycle();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int layout = mScrollOrientation == OrientationHelper.VERTICAL
                ? com.google.blockly.android.R.layout.default_category_start : com.google.blockly.android.R.layout.default_category_horizontal;
        mCategoryView = (CategoryView) inflater.inflate(layout, null);
        mCategoryView.setLabelRotation(mLabelRotation);
        mCategoryView.setScrollOrientation(mScrollOrientation);
        return mCategoryView;
    }

    public void setContents(BlocklyCategory rootCategory) {
        mCategoryView.setContents(rootCategory);
    }

    public void setCurrentCategory(BlocklyCategory category) {
        mCategoryView.setCurrentCategory(category);
    }

    public BlocklyCategory getCurrentCategory() {
        return mCategoryView.getCurrentCategory();
    }

    public void setCategoryCallback(CategorySelectorUI.Callback categoryCallback) {
        mCategoryView.setCallback(categoryCallback);
    }


}

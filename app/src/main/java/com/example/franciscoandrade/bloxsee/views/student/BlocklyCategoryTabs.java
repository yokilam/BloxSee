package com.example.franciscoandrade.bloxsee.views.student;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.franciscoandrade.bloxsee.R;
import com.google.blockly.android.ui.CategorySelectorUI;
import com.google.blockly.android.ui.CategoryTabs;
import com.google.blockly.android.ui.RotatedViewGroup;
import com.google.blockly.android.ui.Rotation;
import com.google.blockly.model.BlocklyCategory;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by joannesong on 4/7/18.
 */

public class BlocklyCategoryTabs extends RecyclerView {

    public static final String TAG = "CategoryTabs";

    public static final int HORIZONTAL = OrientationHelper.HORIZONTAL;
    public static final int VERTICAL = OrientationHelper.VERTICAL;

    private final LinearLayoutManager mLayoutManager;
    private final BlocklyCategoryTabs.CategoryAdapter mAdapter;
    protected final List<BlocklyCategory> mCategories = new ArrayList<>();

    protected @Rotation.Enum int mLabelRotation = Rotation.NONE;
    protected boolean mTapSelectedDeselects = false;

    private LabelAdapter mLabelAdapter;
    protected @Nullable
    CategorySelectorUI.Callback mCallback;
    protected @Nullable
    BlocklyCategory mCurrentCategory;

    public BlocklyCategoryTabs(Context context) {
        this(context, null, 0);
    }

    public BlocklyCategoryTabs(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlocklyCategoryTabs(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);

        mLayoutManager = new LinearLayoutManager(context);
        setLayoutManager(mLayoutManager);
        mAdapter = new BlocklyCategoryTabs.CategoryAdapter();
        setAdapter(mAdapter);
        setLabelAdapter(new BlocklyCategoryTabs.DefaultTabsAdapter());

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                com.google.blockly.android.R.styleable.BlocklyCategory,
                0, 0);
        try {
            //noinspection ResourceType
            mLabelRotation = a.getInt(com.google.blockly.android.R.styleable.BlocklyCategory_labelRotation, mLabelRotation);
            int orientation = a.getInt(com.google.blockly.android.R.styleable.BlocklyCategory_scrollOrientation, VERTICAL);
            mLayoutManager.setOrientation(orientation);
        } finally {
            a.recycle();
        }
    }

    /**
     * Sets the {@link Adapter} responsible for the label views.
     */
    public void setLabelAdapter(BlocklyCategoryTabs.LabelAdapter labelAdapter) {
        mLabelAdapter = labelAdapter;
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Sets the {@link CategorySelectorUI.Callback} used by this instance.
     *
     * @param callback The {@link CategorySelectorUI.Callback} for event handling.
     */
    public void setCallback(@Nullable CategorySelectorUI.Callback callback) {
        mCallback = callback;
    }

    /**
     * Sets the orientation in which the tabs will accumulate, which is also the scroll direction
     * when there are more tabs than space allows.
     *
     * @param orientation Either {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    public void setOrientation(int orientation) {
        mLayoutManager.setOrientation(orientation);
    }

    /**
     * Sets the {@link Rotation} direction constant for the tab labels.
     *
     * @param labelRotation The {@link Rotation} direction constant for the tab labels.
     */
    public void setLabelRotation(@Rotation.Enum int labelRotation) {
        mLabelRotation = labelRotation;
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Sets whether the selected tab will deselect when clicked again.
     *
     * @param tapSelectedDeselects If {@code true}, selected tab will deselect when clicked again.
     */
    public void setTapSelectedDeselects(boolean tapSelectedDeselects) {
        mTapSelectedDeselects = tapSelectedDeselects;
    }

    /**
     * Sets the list of {@link BlocklyCategory}s used to populate the tab labels.
     *
     * @param categories The list of {@link BlocklyCategory}s used to populate the tab labels.
     */
    public void setCategories(List<BlocklyCategory> categories) {
        mCategories.clear();
        mCategories.addAll(categories);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Sets the currently selected tab. If the tab is not a member of the assigned categories, no
     * tab will render selected.
     *
     * @param category
     */
    public void setSelectedCategory(@Nullable BlocklyCategory category) {
        if (mCurrentCategory == category) {
            return;
        }
        if (mCurrentCategory != null) {
            // Deselect the old tab.
            BlocklyCategoryTabs.TabLabelHolder vh = getTabLabelHolder(mCurrentCategory);
            if (vh != null && mLabelAdapter != null) {  // Tab might not be rendered or visible yet.
                // Update style. Don't use notifyItemChanged(..), due to a resulting UI flash.
                mLabelAdapter.onSelectionChanged(
                        vh.mLabel, vh.mCategory, vh.getAdapterPosition(), false);
            }
        }
        mCurrentCategory = category;
        if (mCurrentCategory != null && mLabelAdapter != null) {
            // Select the new tab.
            BlocklyCategoryTabs.TabLabelHolder vh = getTabLabelHolder(mCurrentCategory);
            if (vh != null) {  // Tab might not be rendered or visible yet.
                // Update style. Don't use notifyItemChanged(..), due to a resulting UI flash.
                mLabelAdapter.onSelectionChanged(
                        vh.mLabel, vh.mCategory, vh.getAdapterPosition(), true);
            }
        }
    }

    /**
     * @return The currently highlighted category or null.
     */
    public BlocklyCategory getSelectedCategory() {
        return mCurrentCategory;
    }

    public int getTabCount() {
        return mCategories.size();
    }

    private void onCategoryClicked(BlocklyCategory category) {
        if (mCallback != null) {
            mCallback.onCategoryClicked(category);
        }
    }

    private BlocklyCategoryTabs.TabLabelHolder getTabLabelHolder(BlocklyCategory category) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View child = getChildAt(i);
            BlocklyCategoryTabs.TabLabelHolder vh = (BlocklyCategoryTabs.TabLabelHolder) child.getTag();
            if (vh != null && vh.mCategory == category) {
                return vh;
            }
        }
        return null;
    }

    private class CategoryAdapter extends RecyclerView.Adapter<BlocklyCategoryTabs.TabLabelHolder> {
        @Override
        public int getItemCount() {
            return getTabCount();
        }

        @Override
        public BlocklyCategoryTabs.TabLabelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mLabelAdapter == null) {
                throw new IllegalStateException("No LabelAdapter assigned.");
            }
            return new BlocklyCategoryTabs.TabLabelHolder(mLabelAdapter.onCreateLabel());
        }

        @Override
        public void onBindViewHolder(BlocklyCategoryTabs.TabLabelHolder holder, int tabPosition) {
            final BlocklyCategory category = mCategories.get(tabPosition);
            boolean isSelected = (category == mCurrentCategory);
            // These may throw a NPE, but that is an illegal state checked above.
            mLabelAdapter.onBindLabel(holder.mLabel, category, tabPosition);
            mLabelAdapter.onSelectionChanged(holder.mLabel, category, tabPosition, isSelected);
            holder.mCategory = category;
            holder.mRotator.setChildRotation(mLabelRotation);

            holder.mRotator.setTag(holder);  // For getTabLabelHolder() and deselection
            holder.mLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View label) {
                    onCategoryClicked(category);
                }
            });
        }

        @Override
        public void onViewRecycled(BlocklyCategoryTabs.TabLabelHolder holder) {
            holder.mRotator.setTag(null);  // Remove reference to holder.
            holder.mCategory = null;
            holder.mLabel.setOnClickListener(null);
        }
    }

    /** Manages TextView labels derived from {@link com.google.blockly.android.R.layout#default_toolbox_tab}. */
    protected class DefaultTabsAdapter extends BlocklyCategoryTabs.LabelAdapter {
        @Override
        public View onCreateLabel() {
            return  LayoutInflater.from(getContext())
                    .inflate(com.google.blockly.android.R.layout.default_toolbox_tab, null);
        }

        /**
         * Assigns the category name to the {@link TextView}. Tabs without labels will be assigned
         * the text {@link com.google.blockly.android.R.string#blockly_toolbox_default_category_name} ("Blocks" in English).
         *
         * @param labelView The view used as the label.
         * @param category The {@link BlocklyCategory}.
         * @param position The ordering position of the tab.
         */
        @Override
        public void onBindLabel(View labelView, BlocklyCategory category, int position) {
            String labelText = category.getCategoryName();
            View label = labelView;
            TextView tabText = label.findViewById(R.id.tab_text);
            ImageView tabImage = label.findViewById(R.id.tab_image);

            if (TextUtils.isEmpty(labelText)) {
                labelText = getContext().getString(com.google.blockly.android.R.string.blockly_toolbox_default_category_name);
            }
            tabText.setText(labelText);
            switch(labelText){
                case "Start":
                    tabImage.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                    label.setBackgroundColor(Color.RED);
                    break;
                case "Color":
                    tabImage.setImageResource(R.drawable.ic_color_lens_black_24dp);
                    label.setBackgroundColor(Color.RED);
                    break;
                case "Motions":
                    tabImage.setImageResource(R.drawable.ic_directions_run_black_24dp);
                    label.setBackgroundColor(Color.RED);
                    break;
                case "Control":
                    tabImage.setImageResource(R.drawable.ic_videogame_asset_black_24dp);
                    label.setBackgroundColor(Color.RED);
                    break;


            }
        }
    }
    public abstract static class LabelAdapter {
        /**
         * Create a label view for a tab. This view will later be assigned an
         * {@link View.OnClickListener} to handle tab selection and deselection.
         */
        public abstract View onCreateLabel();

        /**
         * Bind a {@link BlocklyCategory} to a label view, with any appropriate styling.
         *
         * @param labelView The tab's label view.
         * @param category The category to bind to.
         * @param position The position of the category in the list of tabs.
         */
        public abstract void onBindLabel(View labelView, BlocklyCategory category, int position);

        /**
         * Called when a label is bound or when clicking results in a selection change. Responsible
         * for updating the view to reflect the new state, including applying the category name.
         * <p/>
         * By default, it calls {@link View#setSelected(boolean)}. Many views and/or styles will
         * handle this appropriately.
         *
         * @param labelView The tab's label view.
         * @param category The category to bind to.
         * @param position The position of the category in the list of tabs.
         * @param isSelected the new selection state.
         */
        public void onSelectionChanged(
                View labelView, BlocklyCategory category, int position, boolean isSelected) {
            labelView.setSelected(isSelected);
        }
    }

    /**
     * ViewHolder for the display name of a category in the toolbox.
     */
    private static class TabLabelHolder extends RecyclerView.ViewHolder {
        public final RotatedViewGroup mRotator;
        public final View mLabel;

        public BlocklyCategory mCategory;

        TabLabelHolder(View label) {
            super(new RotatedViewGroup(label.getContext()));
            mRotator = (RotatedViewGroup) itemView;
            mLabel = label;
            mRotator.addView(mLabel);

            label.setBackgroundColor(Color.CYAN);
        }
    }
}

package com.luboganev.goodintentions.ui.views;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.luboganev.goodintentions.R;
import com.luboganev.goodintentions.UIUtils;

public class IntentionCategoriesLinearLayout extends LinearLayout {
	public void setOnFindCategoryButtonClickListener(OnClickListener l) {
		mCategorySearchButton.setOnClickListener(l);
	}
	
	@InjectView(R.id.btn_category_search) ImageButton mCategorySearchButton;
	@InjectView(R.id.et_new_intent_category) EditText mNewCategoryEditText;
	@InjectView(R.id.btn_category_add) ImageButton mCategoryAddButton;
	@InjectView(R.id.ll_categories_list) LinearLayout mCategoriesListLinearLayout;
	
	private ArrayList<String> mCategories;
	private static final String STATE_EXTRA_CATEGORIES_LIST = "categories_list";
	private LayoutInflater mLayoutInflater;

	public IntentionCategoriesLinearLayout(Context context) {
		super(context);
		initCustomView(context);
	}

	public IntentionCategoriesLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initCustomView(context);
	}

	public IntentionCategoriesLinearLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initCustomView(context);
	}

	private void initCustomView(Context context) {
		mCategories = new ArrayList<String>();
		setSaveEnabled(true);
		mLayoutInflater = LayoutInflater.from(context);
        View view = mLayoutInflater.inflate(R.layout.view_intention_categories, this, true);
        ButterKnife.inject(this, view);
        mCategoryAddButton.setOnClickListener(mOnAddCategoryListener);
	}
	
	public void setCategories(ArrayList<String> categories) {
		mCategories = categories;
		reloadCategoryViews();
	}
	
	public void setNewCategory(String category) {
		mNewCategoryEditText.setText(category);
	}
	
	private void reloadCategoryViews() {
		mCategoriesListLinearLayout.removeAllViews();
		for (String category : mCategories) {
			appendCategoryView(category);
		}
	}
	
	private void appendCategory(String category) {
		mCategories.add(category);
		appendCategoryView(category);
	}
	
	private void appendCategoryView(String category) {
		Button newCategoryButton = new Button(getContext(), null, R.attr.removableRowButtonStyle);
		newCategoryButton.setText(category);
		newCategoryButton.setOnClickListener(mOnCategoryClickedListener);
		newCategoryButton.setOnLongClickListener(mOnCategoryLongClickedListener);
		int height = UIUtils.getPixelsFromDips(getContext(), 40);
		int marginBottom = UIUtils.getPixelsFromDips(getContext(), 4);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, height);
		params.setMargins(0, 0, 0, marginBottom);
		mCategoriesListLinearLayout.addView(newCategoryButton, params);
	}
	
	private void removeCategoryAtPosition(int pos) {
		if(pos < 0 || pos >= mCategories.size()) return;
		mCategoriesListLinearLayout.removeViewAt(pos);
		mCategories.remove(pos);
	}
	
	private OnCategoryClickedListener mOnCategoryClickedListener = new OnCategoryClickedListener();
	
	private class OnCategoryClickedListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			removeCategoryAtPosition(mCategoriesListLinearLayout.indexOfChild(v));
		}
	}
	
	private OnCategoryLongClickedListener mOnCategoryLongClickedListener = new OnCategoryLongClickedListener();
	
	private class OnCategoryLongClickedListener implements OnLongClickListener {
		@Override
		public boolean onLongClick(View v) {
			setNewCategory(((Button)v).getText().toString());
			return true;
		}
	}
	
	private OnAddCategoryListener mOnAddCategoryListener = new OnAddCategoryListener();
	
	private class OnAddCategoryListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if(mNewCategoryEditText.getText().length() > 0) {
				appendCategory(mNewCategoryEditText.getText().toString());
				mNewCategoryEditText.getText().clear();
			}
		}
	}
	
	public ArrayList<String> getCategoriesList() {
		return mCategories;
	}
	
	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable p = super.onSaveInstanceState();
		Bundle b = new Bundle();
		b.putStringArrayList(STATE_EXTRA_CATEGORIES_LIST, mCategories);
		b.putParcelable("super", p);
		return b;
	}
	
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (!(state instanceof Bundle))
		{
			throw new RuntimeException("unexpected bundle");
		}
		Bundle b = (Bundle)state;
		if(b.containsKey(STATE_EXTRA_CATEGORIES_LIST)) {
			mCategories = b.getStringArrayList(STATE_EXTRA_CATEGORIES_LIST);
		}
		Parcelable sp = b.getParcelable("super");
		super.onRestoreInstanceState(sp);
		reloadCategoryViews();
	}
}

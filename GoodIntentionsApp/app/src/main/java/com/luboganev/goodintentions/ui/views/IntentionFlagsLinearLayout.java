package com.luboganev.goodintentions.ui.views;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.luboganev.goodintentions.R;
import com.luboganev.goodintentions.UIUtils;

public class IntentionFlagsLinearLayout extends LinearLayout {
	public void setOnAddFlagButtonClickListener(OnClickListener l) {
		mFlagAddButton.setOnClickListener(l);
	}
	
	@InjectView(R.id.btn_flag_add) Button mFlagAddButton;
	@InjectView(R.id.ll_flags_list) LinearLayout mFlagsListLinearLayout;
	
	private ArrayList<String> mFlagNames;
	private ArrayList<Integer> mFlagValues;
	private static final String STATE_EXTRA_FLAGS_NAMES_LIST = "flags_names_list";
	private static final String STATE_EXTRA_FLAGS_VALUES_LIST = "flags_values_list";
	private LayoutInflater mLayoutInflater;

	public IntentionFlagsLinearLayout(Context context) {
		super(context);
		initCustomView(context);
	}

	public IntentionFlagsLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initCustomView(context);
	}

	public IntentionFlagsLinearLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initCustomView(context);
	}

	private void initCustomView(Context context) {
		mFlagNames = new ArrayList<String>();
		mFlagValues = new ArrayList<Integer>();
		setSaveEnabled(true);
		mLayoutInflater = LayoutInflater.from(context);
        View view = mLayoutInflater.inflate(R.layout.view_intention_flags, this, true);
        ButterKnife.inject(this, view);
	}
	
	public void setFlags(ArrayList<String> flagLabels, ArrayList<Integer> flagValues) {
		mFlagNames = flagLabels;
		mFlagValues = flagValues;
		reloadFlagViews();
	}
	
	public void appendFlag(String flagName, int flagValue) {
		mFlagNames.add(flagName);
		mFlagValues.add(flagValue);
		appendFlagView(flagName);
	}
	
	private void reloadFlagViews() {
		mFlagsListLinearLayout.removeAllViews();
		for (String flagName : mFlagNames) {
			appendFlagView(flagName);
		}
	}
	
	private void appendFlagView(String flagName) {
		Button newFlagButton = new Button(getContext(), null, R.attr.removableRowButtonStyle);
		newFlagButton.setText(flagName);
		newFlagButton.setOnClickListener(mOnFlagClickedListener);
		int height = UIUtils.getPixelsFromDips(getContext(), 40);
		int marginBottom = UIUtils.getPixelsFromDips(getContext(), 4);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, height);
		params.setMargins(0, 0, 0, marginBottom);
		mFlagsListLinearLayout.addView(newFlagButton, params);
	}
	
	private void removeFlagAtPosition(int pos) {
		if(pos < 0 || pos >= mFlagNames.size() || pos >= mFlagValues.size()) return;
		mFlagsListLinearLayout.removeViewAt(pos);
		mFlagNames.remove(pos);
		mFlagValues.remove(pos);
	}
	
	private OnFlagClickedListener mOnFlagClickedListener = new OnFlagClickedListener();
	
	private class OnFlagClickedListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			removeFlagAtPosition(mFlagsListLinearLayout.indexOfChild(v));
		}
	}
	
	public ArrayList<String> getFlagNamesList() {
		return mFlagNames;
	}
	
	public ArrayList<Integer> getFlagValuesList() {
		return mFlagValues;
	}
	
	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable p = super.onSaveInstanceState();
		Bundle b = new Bundle();
		b.putStringArrayList(STATE_EXTRA_FLAGS_NAMES_LIST, mFlagNames);
		b.putIntegerArrayList(STATE_EXTRA_FLAGS_VALUES_LIST, mFlagValues);
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
		if(b.containsKey(STATE_EXTRA_FLAGS_NAMES_LIST)) {
			mFlagNames = b.getStringArrayList(STATE_EXTRA_FLAGS_NAMES_LIST);
		}
		if(b.containsKey(STATE_EXTRA_FLAGS_NAMES_LIST)) {
			mFlagValues = b.getIntegerArrayList(STATE_EXTRA_FLAGS_VALUES_LIST);
		}
		Parcelable sp = b.getParcelable("super");
		super.onRestoreInstanceState(sp);
		reloadFlagViews();
	}
}

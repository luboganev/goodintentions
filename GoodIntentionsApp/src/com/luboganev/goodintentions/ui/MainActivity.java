package com.luboganev.goodintentions.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.luboganev.goodintentions.IntentionLauncher;
import com.luboganev.goodintentions.R;
import com.luboganev.goodintentions.data.Intention;
import com.luboganev.goodintentions.data.LocalStorageManager;

public class MainActivity extends Activity {
	
	@InjectView(R.id.sp_intention_type) Spinner mType;
	@InjectView(R.id.et_intent_action) EditText mAction;
	@InjectView(R.id.et_intent_component_package_name) EditText mPackageName;
	@InjectView(R.id.et_intent_component_class_name) EditText mClassName;
	@InjectView(R.id.et_intent_data) EditText mData;
	@InjectView(R.id.et_intent_mimetype) EditText mMimeType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
		LocalStorageManager manager = new LocalStorageManager(getApplicationContext());
		Intention storedIntention = manager.getStoredIntention();
		
		mType.setSelection(storedIntention.type >= 0 ? storedIntention.type : 0);
		mAction.setText(storedIntention.action);
		mPackageName.setText(storedIntention.componentPackageName);
		mClassName.setText(storedIntention.componentClassName);
		mData.setText(storedIntention.data);
		mMimeType.setText(storedIntention.mimeType);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.action_play:
				// TODO launch the currently loaded Intent
				Intention intention = new Intention();
				if(mType.getSelectedItemPosition() >= 0) intention.type = mType.getSelectedItemPosition();
				intention.action = mAction.getText().toString();
				intention.componentPackageName = mPackageName.getText().toString();
				intention.componentClassName = mClassName.getText().toString();
				intention.data = mData.getText().toString();
				intention.mimeType = mMimeType.getText().toString();
				
				LocalStorageManager manager = new LocalStorageManager(getApplicationContext());
				manager.setStoredIntention(intention);
				
				IntentionLauncher.launchIntention(getApplicationContext(), intention);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}

package com.luboganev.goodintentions.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.luboganev.goodintentions.IntentionLauncher;
import com.luboganev.goodintentions.R;
import com.luboganev.goodintentions.data.Intention;
import com.luboganev.goodintentions.data.LocalStorageManager;
import com.luboganev.goodintentions.ui.views.IntentionCategoriesLinearLayout;

public class MainActivity extends Activity {
	
	@InjectView(R.id.sp_intention_type) Spinner mType;
	@InjectView(R.id.et_intent_action) EditText mAction;
	@InjectView(R.id.et_intent_component_package_name) EditText mPackageName;
	@InjectView(R.id.et_intent_component_class_name) EditText mClassName;
	@InjectView(R.id.et_intent_data) EditText mData;
	@InjectView(R.id.et_intent_mimetype) EditText mMimeType;
	@InjectView(R.id.custom_view_intention_categories) IntentionCategoriesLinearLayout mIntentionCategories;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
		
		
		if(savedInstanceState == null) {
			Intention storedIntention = LocalStorageManager.getInstance(getApplicationContext())
					.getStoredIntention();
			
			mType.setSelection(storedIntention.type);
			mAction.setText(storedIntention.action);
			mPackageName.setText(storedIntention.componentPackageName);
			mClassName.setText(storedIntention.componentClassName);
			mData.setText(storedIntention.data);
			mMimeType.setText(storedIntention.mimeType);
			mIntentionCategories.setCategories(storedIntention.categories);
		}
		mIntentionCategories.setOnFindCategoryButtonClickListener(new OnFindExistingCategoryClicked());
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
				intention.categories = mIntentionCategories.getCategoriesList();
				
				LocalStorageManager.getInstance(getApplicationContext())
					.setStoredIntention(intention);
				
				String errorMessage = IntentionLauncher.launchIntention(getApplicationContext(), intention);
				if(errorMessage != null) {
					Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	
	private static final String FIND_EXISTING_CATEGORY_DIALOG_FRAGMENT_TAG = "find_existing_category_dialog_fragment_tag";
	
	private class OnFindExistingCategoryClicked implements OnClickListener {
		@Override
		public void onClick(View v) {
			FindExistingCategoryDialogFragment newFragment = new FindExistingCategoryDialogFragment();
			newFragment.show(getFragmentManager(), FIND_EXISTING_CATEGORY_DIALOG_FRAGMENT_TAG);
		}
	}
	
	private void onExistingCategoryPicked(String category) {
		mIntentionCategories.setNewCategory(category);
	}
	
	public static class FindExistingCategoryDialogFragment extends DialogFragment {
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setTitle(R.string.pick_category_title)
	        .setItems(R.array.intent_categories_labels, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                   final String pickedCategory = "android.intent.category." + 
                		   getResources().
                		   getStringArray(R.array.intent_categories_labels)[which];
                   ((MainActivity)getActivity()).onExistingCategoryPicked(pickedCategory);
               }
	        });
	        return builder.create();
	    }
	}
}

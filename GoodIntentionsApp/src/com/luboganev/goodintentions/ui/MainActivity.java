package com.luboganev.goodintentions.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.luboganev.goodintentions.IntentionLauncher;
import com.luboganev.goodintentions.R;
import com.luboganev.goodintentions.data.Intention;
import com.luboganev.goodintentions.data.LocalStorageManager;
import com.luboganev.goodintentions.ui.views.IntentionCategoriesLinearLayout;
import com.luboganev.goodintentions.ui.views.IntentionFlagsLinearLayout;

public class MainActivity extends Activity {
	
	@InjectView(R.id.sp_intention_type) Spinner mType;
	@InjectView(R.id.sp_intention_context_type) Spinner mContextType;
	@InjectView(R.id.btn_action_search) ImageButton mActionSearchButton;
	@InjectView(R.id.et_intent_action) EditText mAction;
	@InjectView(R.id.et_intent_component_package_name) EditText mPackageName;
	@InjectView(R.id.et_intent_component_class_name) EditText mClassName;
	@InjectView(R.id.et_intent_data) EditText mData;
	@InjectView(R.id.et_intent_mimetype) EditText mMimeType;
	@InjectView(R.id.custom_view_intention_categories) IntentionCategoriesLinearLayout mIntentionCategories;
	@InjectView(R.id.custom_view_intention_flags) IntentionFlagsLinearLayout mIntentionFlags;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
		
		
		if(savedInstanceState == null) {
			Intention storedIntention = LocalStorageManager.getInstance(getApplicationContext())
					.getStoredIntention();
			
			initViewsFromIntention(storedIntention);
		}
		mIntentionCategories.setOnFindCategoryButtonClickListener(new OnFindExistingCategoryClicked());
		mIntentionFlags.setOnAddFlagButtonClickListener(new OnAddFlagClicked());
		mActionSearchButton.setOnClickListener(new OnFindExistingActionClicked());
	}
	
	private void initViewsFromIntention(Intention intention) {
		mType.setSelection(intention.type);
		mContextType.setSelection(intention.contextType);
		mAction.setText(intention.action);
		mPackageName.setText(intention.componentPackageName);
		mClassName.setText(intention.componentClassName);
		mData.setText(intention.data);
		mMimeType.setText(intention.mimeType);
		mIntentionCategories.setCategories(intention.categories);
		mIntentionFlags.setFlags(intention.flagsNames, intention.flagsValues);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intention intention;
		switch(item.getItemId()) {
			case R.id.action_play:
				intention = new Intention();
				intention.type = mType.getSelectedItemPosition();
				intention.contextType = mContextType.getSelectedItemPosition();
				intention.action = mAction.getText().toString();
				intention.componentPackageName = mPackageName.getText().toString();
				intention.componentClassName = mClassName.getText().toString();
				intention.data = mData.getText().toString();
				intention.mimeType = mMimeType.getText().toString();
				intention.categories = mIntentionCategories.getCategoriesList();
				intention.flagsNames = mIntentionFlags.getFlagNamesList();
				intention.flagsValues = mIntentionFlags.getFlagValuesList();
				
				LocalStorageManager.getInstance(getApplicationContext())
					.setStoredIntention(intention);
				
				String errorMessage = IntentionLauncher.launchIntention(this, intention);
				if(errorMessage != null) {
					Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
				}
				return true;
			case R.id.action_clear:
				intention = new Intention();
				LocalStorageManager.getInstance(getApplicationContext()).setStoredIntention(intention);
				initViewsFromIntention(intention);
				return true;
			case R.id.action_about:
				startActivity(new Intent(getApplicationContext(), AboutActivity.class));
	        	return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private class OnFindExistingActionClicked implements OnClickListener {
		@Override
		public void onClick(View v) {
			ItemPickerDialogFragment newFragment = ItemPickerDialogFragment.getInstance(
					R.string.pick_action_title, R.array.intent_actions_labels, PICK_CODE_ACTION);
			newFragment.show(getFragmentManager(), ITEM_PICKER_DIALOG_FRAGMENT_TAG);
		}
	}
	
	private class OnFindExistingCategoryClicked implements OnClickListener {
		@Override
		public void onClick(View v) {
			ItemPickerDialogFragment newFragment = ItemPickerDialogFragment.getInstance(
					R.string.pick_category_title, R.array.intent_categories_labels, PICK_CODE_CATEGORY);
			newFragment.show(getFragmentManager(), ITEM_PICKER_DIALOG_FRAGMENT_TAG);
		}
	}
	
	private class OnAddFlagClicked implements OnClickListener {
		@Override
		public void onClick(View v) {
			ItemPickerDialogFragment newFragment = ItemPickerDialogFragment.getInstance(
					R.string.pick_flag_title, R.array.intent_flags_labels, PICK_CODE_FLAG);
			newFragment.show(getFragmentManager(), ITEM_PICKER_DIALOG_FRAGMENT_TAG);
		}
	}
	
	private static final int PICK_CODE_ACTION = 1;
	private static final int PICK_CODE_CATEGORY = 2;
	private static final int PICK_CODE_FLAG = 3;
	
	private void onItemPicked(int pickedPosition, int pickCode) {
		switch(pickCode) {
			case PICK_CODE_ACTION:
				mAction.setText("android.intent.action." + 
						getResources().
						getStringArray(R.array.intent_actions_labels)[pickedPosition]);
			break;
			case PICK_CODE_CATEGORY:
				mIntentionCategories.setNewCategory("android.intent.category." + 
			     		   getResources().
			     		   getStringArray(R.array.intent_categories_labels)[pickedPosition]);
				break;
			case PICK_CODE_FLAG:
				mIntentionFlags.appendFlag(getResources().
						getStringArray(R.array.intent_flags_labels)[pickedPosition], 
						getResources().getIntArray(R.array.intent_flags_values)[pickedPosition]);
				break;
			default:
				break;
		}
	}
	
	private static final String ITEM_PICKER_DIALOG_FRAGMENT_TAG = "item_picker_dialog_fragment_tag";
	
	public static class ItemPickerDialogFragment extends DialogFragment {
		private static final String ARG_LIST_ARRAY_RESOURCE_ID = "list_array_resource_id";
		private static final String ARG_TITLE_RESOURCE_ID = "title_resource_id";
		private static final String ARG_PICK_CODE = "pick_code";
		
		public static ItemPickerDialogFragment getInstance(int titleResourceId, int listArrayResourceId, int pickCode) {
			ItemPickerDialogFragment fragment = new ItemPickerDialogFragment();
			Bundle arguments = new Bundle();
			arguments.putInt(ARG_TITLE_RESOURCE_ID, titleResourceId);
			arguments.putInt(ARG_LIST_ARRAY_RESOURCE_ID, listArrayResourceId);
			arguments.putInt(ARG_PICK_CODE, pickCode);
			fragment.setArguments(arguments);
			return fragment;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(getArguments().getInt(ARG_TITLE_RESOURCE_ID))
			.setItems(getArguments().getInt(ARG_LIST_ARRAY_RESOURCE_ID), 
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					((MainActivity)getActivity()).onItemPicked(which, 
							getArguments().getInt(ARG_PICK_CODE));
				}
			});
			return builder.create();
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == IntentionLauncher.START_FOR_RESULT_DEFAULT_CODE) {
			switch(resultCode) {
				case RESULT_OK:
					Toast.makeText(getApplicationContext(), "Result OK", Toast.LENGTH_SHORT).show();
					break;
				case RESULT_CANCELED:
					Toast.makeText(getApplicationContext(), "Result CANCELED", Toast.LENGTH_SHORT).show();
					break;
				case RESULT_FIRST_USER:
					Toast.makeText(getApplicationContext(), "Result FIRST USER", Toast.LENGTH_SHORT).show();
					break;
				default:
					Toast.makeText(getApplicationContext(), "Result unknown:" + resultCode, Toast.LENGTH_SHORT).show();
					break;
			}
			Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_LONG).show();
		}
	}
}

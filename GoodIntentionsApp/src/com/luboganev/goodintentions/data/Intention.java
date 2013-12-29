package com.luboganev.goodintentions.data;
import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Intention implements Parcelable {
    public static final int INTENTION_TYPE_ACTIVITY = 0;
    public static final int INTENTION_TYPE_SERVICE = 1;
    public static final int INTENTION_TYPE_BROADCAST = 2;
    
	/**
	 * One of the following types required in order to 
	 * know how to launch the Intent:
	 * 
	 * <ul>
	 * 	<li>{@link #INTENTION_TYPE_ACTIVITY} - The Intent is used when staring an activity</li>
	 * 	<li>{@link #INTENTION_TYPE_SERVICE} - The Intent is used when starting a service</li>
	 * 	<li>{@link #INTENTION_TYPE_BROADCAST} - The Intent is used when sending a broadcast </li>
	 * </ul>
	 */
	public int type;
	
	/**
	 * The action related to this Intent
	 */
	public String action;
	
	/**
	 * The name of the package of the component to start. Crucial in case of explicit intents
	 */
	public String componentPackageName;
	
	/**
	 * The name of the class of the component to start. Crucial in case of explicit intents
	 */
	public String componentClassName;
	
	/**
	 * The URI that references the data to be acted on 
	 */
	public String data;
	
	/**
	 * The MIME type of the data under the URI in {@link #data}
	 */
	public String mimeType;
	
	/**
	 * A string containing additional information about the kind of component that should handle the intent.
	 */
	public ArrayList<String> categories;
	
	/**
	 * The names of the flags
	 */
	public ArrayList<String> flagsNames;
	
	/**
	 * The values of the flags
	 */
	public ArrayList<Integer> flagsValues;
	
    public static final int INTENTION_EXTRA_TYPE_BOOLEAN = 0; 
    public static final int INTENTION_EXTRA_TYPE_BYTE = 1; 
    public static final int INTENTION_EXTRA_TYPE_CHAR = 2; 
    public static final int INTENTION_EXTRA_TYPE_DOUBLE = 3; 
    public static final int INTENTION_EXTRA_TYPE_FLOAT = 4; 
    public static final int INTENTION_EXTRA_TYPE_INT = 5; 
    public static final int INTENTION_EXTRA_TYPE_LONG = 6; 
    public static final int INTENTION_EXTRA_TYPE_SHORT = 7; 
    public static final int INTENTION_EXTRA_TYPE_STRING = 8; 
	
	/**
	 * Keys of the extras
	 */
	public ArrayList<String> extrasKeys;
	
	/**
	 * Type of the extras. Supported types
	 */
	public ArrayList<Integer> extrasTypes;

	/**
	 * Value of the extras represented as Strings
	 */
	public ArrayList<String> extrasValues;
	    
    /**
     * Creates a new empty
     */
    public Intention() {
    	type = INTENTION_TYPE_ACTIVITY;
    	action = "";
    	componentPackageName = "";
    	componentClassName = "";
    	data = "";
    	mimeType = "";
    	categories = new ArrayList<String>();
    	flagsNames = new ArrayList<String>();
    	flagsValues = new ArrayList<Integer>();
    	extrasKeys = new ArrayList<String>();
    	extrasTypes = new ArrayList<Integer>();
    	extrasValues = new ArrayList<String>();
    }
    
    public Intent buildIntent() {
    	Intent intent = new Intent();
    	if(action.length() > 0) {
    		intent.setAction(action);
    	}
    	if(componentClassName != null 
    			&& componentPackageName.length() > 0 && componentClassName.length() > 0) {
    		intent.setClassName(componentPackageName, componentClassName);
    	}
    	if(data.length() > 0) {
    		if(mimeType != null && mimeType.length() > 0) {
    			intent.setDataAndType(Uri.parse(data), mimeType);
    		} else {
    			intent.setData(Uri.parse(data));
    		}
    	}
    	for(String category : categories) {
    		if(category.length() > 0) {
    			intent.addCategory(category);
    		}
    	}
    	for(Integer flag : flagsValues) {
    		intent.addFlags(flag);
    	}
    	
    	if(type == INTENTION_TYPE_ACTIVITY) {
    		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	}
    	
    	for (int i = 0; i < extrasKeys.size(); i++) {
    		if(extrasValues.get(i).length() <= 0) continue;
			switch(extrasTypes.get(i)) {
			case INTENTION_EXTRA_TYPE_BOOLEAN:
				intent.putExtra(extrasKeys.get(i), Boolean.parseBoolean(extrasValues.get(i)));
				break;
			case INTENTION_EXTRA_TYPE_BYTE:
				intent.putExtra(extrasKeys.get(i), Byte.parseByte(extrasValues.get(i)));
				break;
			case INTENTION_EXTRA_TYPE_CHAR:
				intent.putExtra(extrasKeys.get(i), extrasValues.get(i).charAt(0));
				break;
			case INTENTION_EXTRA_TYPE_DOUBLE:
				intent.putExtra(extrasKeys.get(i), Double.parseDouble(extrasValues.get(i)));
				break;
			case INTENTION_EXTRA_TYPE_FLOAT:
				intent.putExtra(extrasKeys.get(i), Float.parseFloat(extrasValues.get(i)));
				break;
			case INTENTION_EXTRA_TYPE_INT:
				intent.putExtra(extrasKeys.get(i), Integer.parseInt(extrasValues.get(i)));
				break;
			case INTENTION_EXTRA_TYPE_LONG:
				intent.putExtra(extrasKeys.get(i), Long.parseLong(extrasValues.get(i)));
				break;
			case INTENTION_EXTRA_TYPE_SHORT:
				intent.putExtra(extrasKeys.get(i), Short.parseShort(extrasValues.get(i)));
				break;
			case INTENTION_EXTRA_TYPE_STRING:
				intent.putExtra(extrasKeys.get(i), extrasValues.get(i));
				break;
			}
		}
    	return intent;
    }

    protected Intention(Parcel in) {
        type = in.readInt();
        action = in.readString();
        componentPackageName = in.readString();
        componentClassName = in.readString();
        data = in.readString();
        mimeType = in.readString();
        categories = new ArrayList<String>();
        if (in.readByte() == 0x01) {
            in.readList(categories, String.class.getClassLoader());
        }
        flagsNames = new ArrayList<String>();
        if (in.readByte() == 0x01) {
            in.readList(flagsNames, String.class.getClassLoader());
        }
        flagsValues = new ArrayList<Integer>();
        if (in.readByte() == 0x01) {
            in.readList(flagsValues, Integer.class.getClassLoader());
        }
        extrasKeys = new ArrayList<String>();
        if (in.readByte() == 0x01) {
            in.readList(extrasKeys, String.class.getClassLoader());
        }
        extrasTypes = new ArrayList<Integer>();
        if (in.readByte() == 0x01) {
            in.readList(extrasTypes, Integer.class.getClassLoader());
        }
        extrasValues = new ArrayList<String>();
        if (in.readByte() == 0x01) {
            in.readList(extrasValues, String.class.getClassLoader());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(type);
        dest.writeString(action);
        dest.writeString(componentPackageName);
        dest.writeString(componentClassName);
        dest.writeString(data);
        dest.writeString(mimeType);
        if (categories.isEmpty()) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(categories);
        }
        if (flagsNames.isEmpty()) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(flagsNames);
        }
        if (flagsValues.isEmpty()) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(flagsValues);
        }
        if (extrasKeys.isEmpty()) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(extrasKeys);
        }
        if (extrasTypes.isEmpty()) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(extrasTypes);
        }
        if (extrasValues.isEmpty()) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(extrasValues);
        }
	}
	
	@SuppressWarnings("unused")
    public static final Parcelable.Creator<Intention> CREATOR = new Parcelable.Creator<Intention>() {
        @Override
        public Intention createFromParcel(Parcel in) {
            return new Intention(in);
        }

        @Override
        public Intention[] newArray(int size) {
            return new Intention[size];
        }
    };
}

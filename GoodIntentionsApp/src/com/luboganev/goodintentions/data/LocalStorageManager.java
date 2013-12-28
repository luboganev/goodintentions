package com.luboganev.goodintentions.data;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.luboganev.goodintentions.LogUtils;

/**
 * The local storage manager is responsible for reading and writing all locally stored data
 */
public class LocalStorageManager {
	private static final String LOCAL_STORAGE_FILE_NAME="good_intentions_storage.txt";
	private LocalStorage mLocalStorage;
	private final Gson mGson;
	private final Context mApplicationContext;
	
	public LocalStorageManager(Context applicationContext) {
		mApplicationContext = applicationContext;
		mGson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
	}
	
	/**
	 * Checks for existing persisted local storage
	 * 
	 * @return
	 * 		Persisted local storage exists
	 */
	private boolean hasSavedLocalStorage() {
		File storageFile = new File(mApplicationContext.getFilesDir(), LOCAL_STORAGE_FILE_NAME);
		return storageFile.exists();
	}

	/**
	 * @return
	 * 		Loads a previously saved local storage from disk
	 */
	private boolean loadFromFile() {
		FileInputStream fis = null;
		try {
			fis = mApplicationContext.openFileInput(LOCAL_STORAGE_FILE_NAME);
			String jsonString = readStreamAsString(fis);
			mLocalStorage = mGson.fromJson(jsonString, LocalStorage.class);
			return true;
		} catch (IOException x) {
			LogUtils.e(this, "Cannot read the file");
			return false;
		} finally {
			closeStreamSilently(fis);
		}
	}
	
	/**
	 * Loads a default empty storage
	 */
	private void loadDefaultStorage() {
		mLocalStorage = new LocalStorage();
		mLocalStorage.intention = new Intention();
	}
	
	public Intention getStoredIntention() {
		if(mLocalStorage == null) {
			if(hasSavedLocalStorage()) {
				loadFromFile();
			}
			else loadDefaultStorage();
		}
		return cloneIntention(mLocalStorage.intention);
	}
	
	public void setStoredIntention(Intention intention) {
		if(mLocalStorage == null) {
			getStoredIntention();
		}
		mLocalStorage.intention = cloneIntention(intention);
		saveToFile();
	}
	
	private static Intention cloneIntention(Intention original) {
		Intention intention = new Intention();
		intention.type = original.type;
		for(String category : original.categories) {
			intention.categories.add(new String(category));
		}
		intention.action = original.action;
		intention.componentClassName = new String(original.componentClassName);
		intention.componentPackageName = new String(original.componentPackageName);
		intention.data = new String(original.data);
		intention.mimeType = new String(original.mimeType);
		
		for(String extraKey : original.extrasKeys) {
			intention.extrasKeys.add(new String(extraKey));
		}
		
		for(Integer extraType : original.extrasTypes) {
			intention.extrasTypes.add(extraType.intValue());
		}
		
		for(String extraValue : original.extrasValues) {
			intention.extrasValues.add(new String(extraValue));
		}
		
		for(String flagName : original.flagsNames) {
			intention.flagsNames.add(new String(flagName));
		}
		
		for(Integer flagValue : original.flagsValues) {
			intention.flagsValues.add(flagValue.intValue());
		}
				
		return intention;
	}
	
	/**
	 * Persists the currently loaded {@link LocalStorage}
	 */
	private void saveToFile() {
		String json = mGson.toJson(mLocalStorage);
		FileOutputStream fos = null;
		try {
			fos = mApplicationContext.openFileOutput(LOCAL_STORAGE_FILE_NAME, Context.MODE_PRIVATE);
			fos.write(json.getBytes());
		} catch (IOException x) {
			LogUtils.e(this, "Cannot create or write to file");
		} finally {
			closeStreamSilently(fos);
		}
	}

	/**
	 * Helper method which reads all inputstream contents 
	 * and writes them to an outputstream
	 */
	private void copy(InputStream reader, OutputStream writer)
			throws IOException {
		byte byteArray[] = new byte[4092];
		while (true) {
			int numOfBytesRead = reader.read(byteArray, 0, 4092);
			if (numOfBytesRead == -1) {
				break;
			}
			// else
			writer.write(byteArray, 0, numOfBytesRead);
		}
		return;
	}

	/**
	 * Helper method which reads all inputstream contents as string
	 */
	private String readStreamAsString(InputStream is)
			throws FileNotFoundException, IOException {
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			copy(is, baos);
			return baos.toString();
		} finally {
			if (baos != null)
				closeStreamSilently(baos);
		}
	}

	/**
	 * Helper method that closes OutputStreams
	 */
	private void closeStreamSilently(OutputStream os) {
		if (os == null)
			return;
		// os is not null
		try {
			os.close();
		} catch (IOException x) {
			throw new RuntimeException(
					"This shouldn't happen. exception closing a file", x);
		}
	}

	/**
	 * Helper method that closes InputStreams
	 */
	private void closeStreamSilently(InputStream os) {
		if (os == null)
			return;
		// os is not null
		try {
			os.close();
		} catch (IOException x) {
			throw new RuntimeException(
					"This shouldn't happen. exception closing a file", x);
		}
	}
}

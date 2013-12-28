// Generated code from Butter Knife. Do not modify!
package com.luboganev.goodintentions.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final com.luboganev.goodintentions.ui.MainActivity target, Object source) {
    View view;
    view = finder.findById(source, 2131296258);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131296258' for field 'mPackageName' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mPackageName = (android.widget.EditText) view;
    view = finder.findById(source, 2131296260);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131296260' for field 'mData' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mData = (android.widget.EditText) view;
    view = finder.findById(source, 2131296259);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131296259' for field 'mClassName' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mClassName = (android.widget.EditText) view;
    view = finder.findById(source, 2131296261);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131296261' for field 'mMimeType' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mMimeType = (android.widget.EditText) view;
    view = finder.findById(source, 2131296256);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131296256' for field 'mType' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mType = (android.widget.Spinner) view;
    view = finder.findById(source, 2131296257);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131296257' for field 'mAction' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mAction = (android.widget.EditText) view;
  }

  public static void reset(com.luboganev.goodintentions.ui.MainActivity target) {
    target.mPackageName = null;
    target.mData = null;
    target.mClassName = null;
    target.mMimeType = null;
    target.mType = null;
    target.mAction = null;
  }
}

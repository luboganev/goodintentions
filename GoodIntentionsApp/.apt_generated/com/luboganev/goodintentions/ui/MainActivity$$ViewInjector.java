// Generated code from Butter Knife. Do not modify!
package com.luboganev.goodintentions.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final com.luboganev.goodintentions.ui.MainActivity target, Object source) {
    View view;
    view = finder.findById(source, 2131361795);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131361795' for field 'mActionSearchButton' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mActionSearchButton = (android.widget.ImageButton) view;
    view = finder.findById(source, 2131361796);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131361796' for field 'mAction' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mAction = (android.widget.EditText) view;
    view = finder.findById(source, 2131361797);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131361797' for field 'mPackageName' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mPackageName = (android.widget.EditText) view;
    view = finder.findById(source, 2131361799);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131361799' for field 'mData' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mData = (android.widget.EditText) view;
    view = finder.findById(source, 2131361798);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131361798' for field 'mClassName' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mClassName = (android.widget.EditText) view;
    view = finder.findById(source, 2131361794);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131361794' for field 'mContextType' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mContextType = (android.widget.Spinner) view;
    view = finder.findById(source, 2131361800);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131361800' for field 'mMimeType' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mMimeType = (android.widget.EditText) view;
    view = finder.findById(source, 2131361801);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131361801' for field 'mIntentionCategories' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mIntentionCategories = (com.luboganev.goodintentions.ui.views.IntentionCategoriesLinearLayout) view;
    view = finder.findById(source, 2131361802);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131361802' for field 'mIntentionFlags' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mIntentionFlags = (com.luboganev.goodintentions.ui.views.IntentionFlagsLinearLayout) view;
    view = finder.findById(source, 2131361793);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131361793' for field 'mType' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mType = (android.widget.Spinner) view;
  }

  public static void reset(com.luboganev.goodintentions.ui.MainActivity target) {
    target.mActionSearchButton = null;
    target.mAction = null;
    target.mPackageName = null;
    target.mData = null;
    target.mClassName = null;
    target.mContextType = null;
    target.mMimeType = null;
    target.mIntentionCategories = null;
    target.mIntentionFlags = null;
    target.mType = null;
  }
}

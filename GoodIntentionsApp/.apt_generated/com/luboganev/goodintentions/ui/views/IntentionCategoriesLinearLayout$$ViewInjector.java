// Generated code from Butter Knife. Do not modify!
package com.luboganev.goodintentions.ui.views;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class IntentionCategoriesLinearLayout$$ViewInjector {
  public static void inject(Finder finder, final com.luboganev.goodintentions.ui.views.IntentionCategoriesLinearLayout target, Object source) {
    View view;
    view = finder.findById(source, 2131296264);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131296264' for field 'mCategorySearchButton' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mCategorySearchButton = (android.widget.ImageButton) view;
    view = finder.findById(source, 2131296265);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131296265' for field 'mNewCategoryEditText' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mNewCategoryEditText = (android.widget.EditText) view;
    view = finder.findById(source, 2131296267);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131296267' for field 'mCategoriesListLinearLayout' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mCategoriesListLinearLayout = (android.widget.LinearLayout) view;
    view = finder.findById(source, 2131296266);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131296266' for field 'mCategoryAddButton' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mCategoryAddButton = (android.widget.ImageButton) view;
  }

  public static void reset(com.luboganev.goodintentions.ui.views.IntentionCategoriesLinearLayout target) {
    target.mCategorySearchButton = null;
    target.mNewCategoryEditText = null;
    target.mCategoriesListLinearLayout = null;
    target.mCategoryAddButton = null;
  }
}

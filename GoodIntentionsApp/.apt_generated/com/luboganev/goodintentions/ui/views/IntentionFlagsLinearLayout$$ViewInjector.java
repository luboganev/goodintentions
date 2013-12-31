// Generated code from Butter Knife. Do not modify!
package com.luboganev.goodintentions.ui.views;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class IntentionFlagsLinearLayout$$ViewInjector {
  public static void inject(Finder finder, final com.luboganev.goodintentions.ui.views.IntentionFlagsLinearLayout target, Object source) {
    View view;
    view = finder.findById(source, 2131361808);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131361808' for field 'mFlagsListLinearLayout' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mFlagsListLinearLayout = (android.widget.LinearLayout) view;
    view = finder.findById(source, 2131361807);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131361807' for field 'mFlagAddButton' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mFlagAddButton = (android.widget.Button) view;
  }

  public static void reset(com.luboganev.goodintentions.ui.views.IntentionFlagsLinearLayout target) {
    target.mFlagsListLinearLayout = null;
    target.mFlagAddButton = null;
  }
}

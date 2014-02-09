// Generated code from Butter Knife. Do not modify!
package com.pxl.android.cemeo.authenticator;

import android.view.View;
import butterknife.Views.Finder;

public class BootstrapAuthenticatorActivity$$ViewInjector {
  public static void inject(Finder finder, com.pxl.android.cemeo.authenticator.BootstrapAuthenticatorActivity target, Object source) {
    View view;
    view = finder.findById(source, 2131099751);
    target.emailText = (android.widget.AutoCompleteTextView) view;
    view = finder.findById(source, 2131099752);
    target.passwordText = (android.widget.EditText) view;
    view = finder.findById(source, 2131099753);
    target.signinButton = (android.widget.Button) view;
  }
}

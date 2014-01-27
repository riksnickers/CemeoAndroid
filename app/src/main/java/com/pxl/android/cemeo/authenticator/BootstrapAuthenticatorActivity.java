
package com.pxl.android.cemeo.authenticator;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.wishlist.Toaster;
import com.google.gson.Gson;
import com.pxl.android.cemeo.R.id;
import com.pxl.android.cemeo.R.layout;
import com.pxl.android.cemeo.R.string;
import com.pxl.android.cemeo.core.Constants;
import com.pxl.android.cemeo.core.User;
import com.pxl.android.cemeo.ui.TextWatcherAdapter;
import com.pxl.android.cemeo.util.Ln;
import com.pxl.android.cemeo.util.SafeAsyncTask;
import com.pxl.android.cemeo.util.Strings;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.Views;

import static android.R.layout.simple_dropdown_item_1line;
import static android.accounts.AccountManager.KEY_ACCOUNT_NAME;
import static android.accounts.AccountManager.KEY_ACCOUNT_TYPE;
import static android.accounts.AccountManager.KEY_AUTHTOKEN;
import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;
import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static com.github.kevinsawicki.http.HttpRequest.post;
import static com.pxl.android.cemeo.core.Constants.Http.URL_AUTH;

/**
 * Activity to authenticate the user against the api
 */
public class BootstrapAuthenticatorActivity extends SherlockAccountAuthenticatorActivity {

    /**
     * PARAM_CONFIRMCREDENTIALS
     */
    public static final String PARAM_CONFIRMCREDENTIALS = "confirmCredentials";

    /**
     * PARAM_PASSWORD
     */
    public static final String PARAM_PASSWORD = "password";

    /**
     * PARAM_USERNAME
     */
    public static final String PARAM_USERNAME = "username";

    /**
     * PARAM_AUTHTOKEN_TYPE
     */
    public static final String PARAM_AUTHTOKEN_TYPE = "bearer";


    private AccountManager accountManager;

    @InjectView(id.et_email)
    AutoCompleteTextView emailText;
    @InjectView(id.et_password)
    EditText passwordText;
    @InjectView(id.b_signin)
    Button signinButton;

    private TextWatcher watcher = validationTextWatcher();

    private SafeAsyncTask<Boolean> authenticationTask;
    private String authToken;
    private String authTokenType;

    /**
     * If set we are just checking that the user knows their credentials; this
     * doesn't cause the user's password to be changed on the device.
     */
    private Boolean confirmCredentials = false;

    private String email;

    private String password;


    /**
     * De bearer token die we van de api krijgen na het inloggen
     */
    private String token;

    /**
     * Was the original caller asking for an entirely new account?
     */
    protected boolean requestNewAccount = false;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        accountManager = AccountManager.get(this);
        final Intent intent = getIntent();
        email = intent.getStringExtra(PARAM_USERNAME);
        authTokenType = intent.getStringExtra(PARAM_AUTHTOKEN_TYPE);
        requestNewAccount = email == null;
        confirmCredentials = intent.getBooleanExtra(PARAM_CONFIRMCREDENTIALS, false);

        setContentView(layout.login_activity);

        Views.inject(this);

        emailText.setAdapter(new ArrayAdapter<String>(this,
                simple_dropdown_item_1line, userEmailAccounts()));

        passwordText.setOnKeyListener(new OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event != null && ACTION_DOWN == event.getAction()
                        && keyCode == KEYCODE_ENTER && signinButton.isEnabled()) {
                    handleLogin(signinButton);
                    return true;
                }
                return false;
            }
        });

        passwordText.setOnEditorActionListener(new OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == IME_ACTION_DONE && signinButton.isEnabled()) {
                    handleLogin(signinButton);
                    return true;
                }
                return false;
            }
        });

        emailText.addTextChangedListener(watcher);
        passwordText.addTextChangedListener(watcher);

        //TextView signupText = (TextView) findViewById(id.tv_signup);
        //signupText.setMovementMethod(LinkMovementMethod.getInstance());
        //signupText.setText(Html.fromHtml(getString(string.signup_link)));
    }

    //Een lijst met suggesties bij de ingave van een gebruikersnaam (of email)

    private List<String> userEmailAccounts() {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        List<String> emailAddresses = new ArrayList<String>(accounts.length);
        for (Account account : accounts)
            emailAddresses.add(account.name);
        return emailAddresses;
    }

    private TextWatcher validationTextWatcher() {
        return new TextWatcherAdapter() {
            public void afterTextChanged(Editable gitDirEditText) {
                updateUIWithValidation();
            }

        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUIWithValidation();
    }

    private void updateUIWithValidation() {
        boolean populated = populated(emailText) && populated(passwordText);
        signinButton.setEnabled(populated);
    }

    private boolean populated(EditText editText) {
        return editText.length() > 0;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getText(string.message_signing_in));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                if (authenticationTask != null)
                    authenticationTask.cancel(true);
            }
        });
        return dialog;
    }

    /**
     * Sends username/password to the server for authentication.
     * Specified by android:onClick="handleLogin" in the layout xml
     *
     * @param view
     */
    public void handleLogin(View view) {
        if (authenticationTask != null)
            return;

        if (requestNewAccount)
            email = emailText.getText().toString();
            password = passwordText.getText().toString();
            showProgress();

            authenticationTask = new SafeAsyncTask<Boolean>() {

            public Boolean call() throws Exception {

                //Post met user & passw
                final String query = String.format("grant_type=password&%s=%s&%s=%s", PARAM_USERNAME, email, PARAM_PASSWORD, password);

                HttpRequest request = post(URL_AUTH).contentType("application/x-www-form-urlencoded").send(query);

                Ln.d("statuslog: response = %s", request.code());

                if (request.ok()) {

                    //Request in json met token --> user obj
                    final User model = new Gson().fromJson(Strings.toString(request.buffer()), User.class);

                    token = model.getSessionToken();
                    Ln.d("statuslog: token = %s", model.getSessionToken());
                }

                return request.ok();
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                Throwable cause = e.getCause() != null ? e.getCause() : e;

                String message;
                // 404 --> Foutmelding
                if ("Received authentication challenge is null".equals(cause.getMessage()))
                    message = getResources().getString(string.message_bad_credentials);
                else
                    message = cause.getMessage();

                Toaster.showLong(BootstrapAuthenticatorActivity.this, message);
            }

            @Override
            public void onSuccess(Boolean authSuccess) {
                onAuthenticationResult(authSuccess);
            }

            @Override
            protected void onFinally() throws RuntimeException {
                hideProgress();
                authenticationTask = null;
            }
        };
        authenticationTask.execute();
    }

    /**
     * Called when response is received from the server for confirm credentials
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller.
     *
     * @param result
     */
    protected void finishConfirmCredentials(boolean result) {
        final Account account = new Account(email, Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE);
        accountManager.setPassword(account, password);

        final Intent intent = new Intent();
        intent.putExtra(KEY_BOOLEAN_RESULT, result);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Called when response is received from the server for authentication
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller. Also sets
     * the authToken in AccountManager for this account.
     */

    protected void finishLogin() {
        final Account account = new Account(email, Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE);

        if (requestNewAccount)
            accountManager.addAccountExplicitly(account, password, null);
        else
            accountManager.setPassword(account, password);
        final Intent intent = new Intent();
        authToken = token;
        intent.putExtra(KEY_ACCOUNT_NAME, email);
        intent.putExtra(KEY_ACCOUNT_TYPE, Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE);
        if (authTokenType != null && authTokenType.equals(Constants.Auth.AUTHTOKEN_TYPE))
            intent.putExtra(KEY_AUTHTOKEN, authToken);
            accountManager.setAuthToken(account, authTokenType, authToken);
            Ln.d("statuslog account : account token toegevoegd");
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Hide progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void hideProgress() {
        dismissDialog(0);
    }

    /**
     * Show progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void showProgress() {
        showDialog(0);
    }

    /**
     * Called when the authentication process completes (see attemptLogin()).
     *
     * @param result
     */
    public void onAuthenticationResult(boolean result) {
        if (result)
            if (!confirmCredentials)
                finishLogin();
            else
                finishConfirmCredentials(true);
        else {

            if (requestNewAccount)
                Toaster.showLong(BootstrapAuthenticatorActivity.this,string.message_auth_failed_new_account);
            else
                Toaster.showLong(BootstrapAuthenticatorActivity.this, string.message_auth_failed);

        }
    }
}

package com.mantra.chatatmantra.view.activities;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mantra.chatatmantra.R;
import com.mantra.chatatmantra.utils.ConstantClass;

import java.util.StringTokenizer;

public class LoginActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final int RC_SIGN_IN = 9001;
    public static String TAG = "Login ==>";

    Context context;

    Toolbar toolbar;
    ProgressDialog pDialog;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        context = this;
        setSupportActionBar(toolbar);

        pDialog = new ProgressDialog(context);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.google_plus_web_client_id))
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    public void onGPlusButtonClick(View view) {
        signInNew();
    }

    @Override
    public void onConnected(Bundle bundle) {
        signIn();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(context, "Google Sign-In Error:".concat(connectionResult.getErrorMessage()), Toast.LENGTH_SHORT).show();
    }

    private void signInNew() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestIdToken(getString(R.string.google_plus_web_client_id))
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleApiClient.connect();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "GoogleSignInResult: " + result);
        if (result.isSuccess()) {
            pDialog.dismiss();

            GoogleSignInAccount acct = result.getSignInAccount();
            String googleToken = acct.getIdToken();
            Log.d(TAG, "Google Token: " + googleToken);
//            String firstName = acct.getGivenName();
//            String lastName = acct.getFamilyName();
//            Log.d(TAG, "Name: " + firstName + " " + lastName);
            String googleEmail = acct.getEmail();
            Log.d(TAG, "Email: " + googleEmail);

            proceedPostLogin(googleEmail);
        } else {
            pDialog.dismiss();
            Log.d(TAG, "Google SignIn Failure");
            Log.d(TAG, "Status: " + result.getStatus().getStatusMessage());
        }
    }

    private void signIn() {
        pDialog.setMessage("Logging in...");
        pDialog.setCancelable(true);
        pDialog.show();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public boolean isCorrectId(String email) {
        StringTokenizer tokens = new StringTokenizer(email, "@");
        String id = tokens.nextToken();
        String domain = tokens.nextToken();
        Log.d(TAG, "Domain: " + domain);
        if (domain.equals(ConstantClass.universalDomain)) {
            return true;
        } else {
            Toast.makeText(context, "Please use you company's email id!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void proceedPostLogin(String email) {
        if (isCorrectId(email)) {
            Toast.makeText(LoginActivity.this, "Login Request!", Toast.LENGTH_SHORT).show();
            startNextActivity();
        } else {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            Toast.makeText(LoginActivity.this, "Login Failure! Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public void startNextActivity() {
        TabActivity_.intent(getApplicationContext()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
        finish();
    }
}

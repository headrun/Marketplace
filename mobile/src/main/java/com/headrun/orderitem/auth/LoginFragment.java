package com.headrun.orderitem.auth;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseUser;
import com.headrun.orderitem.R;
import com.headrun.orderitem.model.User;

import static android.content.ContentValues.TAG;

/**
 * Created by sujith on 7/3/17.
 */

public class LoginFragment extends Fragment implements AuthView, View.OnClickListener {


    EditText email_field;

    EditText password_field;
    Spinner role_field;
    Button btn_login;

    TextView btn_switch_to_signup;

    TextView btn_forgot_password;

    ImageView btn_sign_in_with_google;

    ImageView btn_sign_in_with_facebook;
    ProgressBar progress_bar;

    AuthPresenter mAuthPresenter;
    AuthActivity mAuthActivty;
    FragmentChanged mFragmentChanged;

    String user_role;

    public LoginFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.login, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        intiview();
        mFragmentChanged = (FragmentChanged) getActivity();
        mAuthPresenter = new AuthPresenter(this, getActivity());
        mAuthActivty = new AuthActivity();
    }

    private void intiview() {

        email_field = (EditText) getView().findViewById(R.id.email_field);
        password_field = (EditText) getView().findViewById(R.id.password_field);
        role_field = (Spinner) getView().findViewById(R.id.role_field);
        btn_login = (Button) getView().findViewById(R.id.btn_login);
        btn_switch_to_signup = (TextView) getView().findViewById(R.id.btn_switch_to_signup);
        btn_forgot_password = (TextView) getView().findViewById(R.id.btn_forgot_password);
        btn_sign_in_with_google = (ImageView) getView().findViewById(R.id.btn_sign_in_with_google);
        btn_sign_in_with_facebook = (ImageView) getView().findViewById(R.id.btn_sign_in_with_facebook);
        progress_bar = (ProgressBar) getView().findViewById(R.id.progress_bar);

        btn_login.setOnClickListener(this);
        btn_switch_to_signup.setOnClickListener(this);
        btn_forgot_password.setOnClickListener(this);
        btn_sign_in_with_google.setOnClickListener(this);
        btn_sign_in_with_facebook.setOnClickListener(this);
        role_field.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                user_role = ((TextView) view).getText().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setError(String msg) {
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_login:
                mAuthPresenter.loginValidation(email_field.getText().toString().trim(), password_field.getText().toString().trim(), user_role);
                break;

            case R.id.btn_switch_to_signup:
                mFragmentChanged.fragmentChanged(new SignupFragment());
                break;

            case R.id.btn_forgot_password:
                break;

            case R.id.btn_sign_in_with_google:
                // mAuthPresenter.signGoogle();
                break;

            case R.id.btn_sign_in_with_facebook:
                // mAuthPresenter.signFacebook(getActivity());
                break;

        }
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mAuthPresenter.callbackManager != null)
            mAuthPresenter.callbackManager.onActivityResult(requestCode, resultCode, data);
    }*/

    @Override
    public void redirectActivity(FirebaseUser mFirebaseUser) {
        Log.i(TAG, "login resposne is");
        mAuthPresenter.redirctToAtivty(mFirebaseUser);
    }

    @Override
    public void showProgress() {
        progress_bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress_bar.setVisibility(View.GONE);
    }

    @Override
    public void setData(User muser) {
        mAuthPresenter.setFireBaseUserData(muser);
    }
}

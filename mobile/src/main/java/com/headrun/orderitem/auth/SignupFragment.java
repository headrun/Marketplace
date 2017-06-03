package com.headrun.orderitem.auth;

import android.os.Bundle;
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
import android.widget.Toast;


import com.google.firebase.auth.FirebaseUser;
import com.headrun.orderitem.R;
import com.headrun.orderitem.model.User;

import static android.content.ContentValues.TAG;

/**
 * Created by sujith on 7/3/17.
 */

public class SignupFragment extends Fragment implements View.OnClickListener, AuthView {

    EditText first_name;
    EditText last_name, ph_field;
    AutoCompleteTextView email_field;
    Spinner role_field;
    EditText password_field;
    Button btn_signup;
    TextView btn_switch_to_sign_in;
    TextView btn_forgot_password;
    ImageView btn_sign_in_with_google;
    ImageView btn_sign_in_with_facebook;
    ProgressBar progress_bar;

    AuthPresenter mAuthPresenter;
    FragmentChanged mFragmentChanged;

    String user_role;

    public SignupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_email, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initview();
        mFragmentChanged = (FragmentChanged) getActivity();
        mAuthPresenter = new AuthPresenter(this, getActivity());

    }

    private void initview() {

        first_name = (EditText) getView().findViewById(R.id.first_name);
        last_name = (EditText) getView().findViewById(R.id.last_name);
        email_field = (AutoCompleteTextView) getView().findViewById(R.id.email_field);
        role_field = (Spinner) getView().findViewById(R.id.role_field);
        password_field = (EditText) getView().findViewById(R.id.password_field);
        ph_field = (EditText) getView().findViewById(R.id.ph_field);
        btn_signup = (Button) getView().findViewById(R.id.btn_signup);
        btn_switch_to_sign_in = (TextView) getView().findViewById(R.id.btn_switch_to_sign_in);
        btn_forgot_password = (TextView) getView().findViewById(R.id.btn_forgot_password);
        btn_sign_in_with_google = (ImageView) getView().findViewById(R.id.btn_sign_in_with_google);
        btn_sign_in_with_facebook = (ImageView) getView().findViewById(R.id.btn_sign_in_with_facebook);
        progress_bar = (ProgressBar) getView().findViewById(R.id.progress_bar);

        btn_signup.setOnClickListener(this);
        btn_switch_to_sign_in.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_signup:
                mAuthPresenter.signupValidation(first_name.getText().toString().trim(), last_name.getText().toString().trim(), email_field.getText().toString().trim(), password_field.getText().toString().trim(), ph_field.getText().toString().trim(), user_role);
                break;

            case R.id.btn_switch_to_sign_in:
                mFragmentChanged.fragmentChanged(new LoginFragment());
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

    @Override
    public void setError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

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

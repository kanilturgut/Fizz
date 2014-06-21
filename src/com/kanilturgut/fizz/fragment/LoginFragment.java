package com.kanilturgut.fizz.fragment;

import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.kanilturgut.fizz.R;
import com.kanilturgut.fizz.task.LoginTask;

/**
 * Author   : kanilturgut
 * Date     : 21/06/14
 * Time     : 16:49
 */
public class LoginFragment extends Fragment {

    final String TAG = "LoginFragment";
    Context context = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        final EditText etEmail = (EditText) view.findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) view.findViewById(R.id.etPassword);

        Button bLogin = (Button) view.findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Lütfen boş alanları doldurunuz", Toast.LENGTH_LONG).show();
                } else {
                    LoginTask loginTask = new LoginTask(context, false);
                    loginTask.execute(email, password);
                }
            }
        });

        return view;
    }
}
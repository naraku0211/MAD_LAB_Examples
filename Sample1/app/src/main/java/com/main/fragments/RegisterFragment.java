package com.main.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.main.R;
import com.main.helpers.DBHelper;
import com.main.models.User;

public class RegisterFragment extends DialogFragment {

    private TextInputEditText username, password, firstName, lastName;
    private DBHelper dbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //username = find
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        User user =  new User();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        return new AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.personal_details))
                .setView(R.layout.fragment_register)
                .setPositiveButton(getString(R.string.okay), (dialog, which) -> {
                    dbHelper.addUser(user);
                } )
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {})
                .create();
    }
    public static String TAG = "RegisterAccount";
}

package com.main.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;
import com.main.R;
import com.main.helpers.DBHelper;
import com.main.models.User;

import java.util.Objects;

public class RegisterFragment extends DialogFragment {

    private TextInputEditText rUsername, rPassword, rFirstName, rLastName;
    private DBHelper dbHelper;
    private LayoutInflater inflater;
    private View view;
    private AlertDialog.Builder builder;
    private String rUser="", rPass="", rFname="", rLname="";
    public static String TAG = "RegisterAccount";

    public RegisterFragment(){ }
    public RegisterFragment(DBHelper dbHelper){
        this.dbHelper = dbHelper;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_register, null);

        rUsername = view.findViewById(R.id.registerUsername);
        rPassword = view.findViewById(R.id.registerPassword);
        rFirstName = view.findViewById(R.id.registerFName);
        rLastName = view.findViewById(R.id.registerLName);

        builder  = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.personal_details);
        builder.setView(view);

        User user = new User(rUser, rPass, rFname, rLname);

        builder.setPositiveButton(getString(R.string.okay), (dialog, which) -> {

           /* if(!TextUtils.isEmpty(rUser) && !TextUtils.isEmpty(rPass)
                    && !TextUtils.isEmpty(rFname) && !TextUtils.isEmpty(rLname) ){

            }
            else {
                Toast.makeText(view.getContext(), "Please enter fields", Toast.LENGTH_SHORT).show();
            }

            */

            dbHelper.addUser(user);
        });

        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> {
            dialog.dismiss();
        });

        return builder.create();
    }

    private boolean checkRegisterInput(){
        DialogListener listener = (DialogListener) getActivity();
        rUser = rUsername.getText().toString();
        rPass = rPassword.getText().toString();
        rFname = rFirstName.getText().toString();
        rLname = rLastName.getText().toString();

        // You can use TextUtils to check for empty or null strings
        if (!TextUtils.isEmpty(rUser) && !TextUtils.isEmpty(rPass)
                && !TextUtils.isEmpty(rFname) && !TextUtils.isEmpty(rLname)) {
            listener.onFinishDialog(rUser, rPass, rFname, rLname);
            dismiss();
        } else {
            Toast.makeText(getActivity(), "Please enter fields.", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    public interface DialogListener{
        void onFinishDialog(String username, String password, String firstname, String lastname);
    }

}

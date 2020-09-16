package omkar.tilekar.loginapp.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import omkar.tilekar.loginapp.activities.MainActivity;
import omkar.tilekar.loginapp.R;
import omkar.tilekar.loginapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFragment extends Fragment {
    EditText orgnamet,name_reg,email_reg,phone_reg,pass_reg;
    Button button_reg;


    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reg, container, false);
        orgnamet = view.findViewById(R.id.orgnameInput);
        name_reg= view.findViewById(R.id.nameInput);
        email_reg = view.findViewById(R.id.emailInput);
        phone_reg = view.findViewById(R.id.phoneInput);
        pass_reg = view.findViewById(R.id.passwordInput);
        button_reg = view.findViewById(R.id.regBtn);

        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        return view;
    }

    private void registerUser() {
        final String orgname = orgnamet.getText().toString().trim();
        final String name = name_reg.getText().toString().trim();
        final String email = email_reg.getText().toString().trim();
        String phone = phone_reg.getText().toString().trim();
        String pass = pass_reg.getText().toString().trim();

        if(TextUtils.isEmpty(orgname))
        {
            MainActivity.appPreference.showToast("Enter your Organization name:");
        }
        else if(TextUtils.isEmpty(name))
        {
            MainActivity.appPreference.showToast("Enter your name:");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            MainActivity.appPreference.showToast("Enter your email id:");
        }
        else if(TextUtils.isEmpty(phone))
        {
            MainActivity.appPreference.showToast("Enter your phone no");
        }
        else if(pass.length() <6)
        {
            MainActivity.appPreference.showToast("your password length is not valid");
        }
        else
        {
            ////  Data on Server
            Call<User> userCall = MainActivity.serviceApi.doRegistration(orgname,name,email,phone,pass);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull retrofit2.Call<User> call, @NonNull Response<User> response) {
                    Log.i("My Response",response.body().getResponse());
                    // System.out.println("Myway"+response.body().getResponse());
                    if(response.body().getResponse().matches("inserted"))
                    {
                        showMsg("Sucessfully register","Welcome"+name);
                    }
                    else  if(response.body().getResponse().matches("exists"))
                    {
                        showMsg("Already Register","Email ID is already registered" +email);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {

                    System.out.println("Myway"+t.getMessage());
                }
            });
        }
    }

    private void showMsg(String title,String msg) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Re-Try", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearText();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void clearText() {
        orgnamet.setText("");
        name_reg.setText("");
        email_reg.setText("");
        phone_reg.setText("");
        pass_reg.setText("");
    }
}
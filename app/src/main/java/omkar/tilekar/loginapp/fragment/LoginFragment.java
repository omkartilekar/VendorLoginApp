package omkar.tilekar.loginapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import omkar.tilekar.loginapp.activities.MainActivity;
import omkar.tilekar.loginapp.R;
import omkar.tilekar.loginapp.model.User;
import omkar.tilekar.loginapp.services.MyInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    MyInterface myInterface_login;
    Button loginBtn_op;
    EditText emailInput_op,passInput_op;
    TextView registerTV_op;

    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_login, container, false);

        //Login
        emailInput_op = view.findViewById(R.id.emailInput);
        passInput_op = view.findViewById(R.id.passwordInput);
        loginBtn_op = view.findViewById(R.id.loginBtn);
        loginBtn_op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        // Register

        registerTV_op = view.findViewById(R.id.registerTV);
        registerTV_op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Register", Toast.LENGTH_SHORT).show();
                myInterface_login.register();
            }
        });
        return view;
    }


    private void loginUser() {
        String email=emailInput_op.getText().toString().trim();
        String password=passInput_op.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            MainActivity.appPreference.showToast("Email id is invalid");
        }else if (TextUtils.isEmpty(password))
        {
            MainActivity.appPreference.showToast("Enter your password");
        }else if (password.length()<6)
        {
            MainActivity.appPreference.showToast("Password too short!");
        }else
        {
            retrofit2.Call<User> userCall = MainActivity.serviceApi.doLogin(email,password);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull retrofit2.Call<User> call, @NonNull Response<User> response) {
                    if(response.body().getResponse().equals("data"))
                    {
                        MainActivity.appPreference.setLoginStatus(true);

                        myInterface_login.login(response.body().getOrgName(),response.body().getName(),response.body().getEmail(),response.body().getCreatedAt());
                        System.out.println("BOU" +response.body().getOrgName() +"\n"+response.body().getName() +"\n"+response.body().getEmail() +"\n"+response.body().getCreatedAt());
                        Toast.makeText(getActivity(), "Login Successfull!!", Toast.LENGTH_SHORT).show();
                    }
                    else  if(response.body().getResponse().equals("login_failed")){
                        Toast.makeText(getActivity(), "Login Failed!!", Toast.LENGTH_SHORT).show();
                        emailInput_op.setText("");
                        passInput_op.setText("");
                    }

                }

                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                    System.out.println("myerror" + t.getMessage());
                    Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity= (Activity) context;
        myInterface_login = (MyInterface) activity;
    }
}


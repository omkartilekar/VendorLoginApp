package omkar.tilekar.loginapp.activities;

import androidx.appcompat.app.AppCompatActivity;

//import com.facebook.CallbackManager;
//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;
import android.os.Bundle;
import android.widget.FrameLayout;

import omkar.tilekar.loginapp.R;
import omkar.tilekar.loginapp.constants.Constant;
import omkar.tilekar.loginapp.extras.AppPreference;
import omkar.tilekar.loginapp.fragment.LoginFragment;
import omkar.tilekar.loginapp.fragment.ProfileFragment;
import omkar.tilekar.loginapp.fragment.RegistrationFragment;
import omkar.tilekar.loginapp.services.MyInterface;
import omkar.tilekar.loginapp.services.RetrofitClient;
import omkar.tilekar.loginapp.services.ServiceApi;

public class MainActivity extends AppCompatActivity implements MyInterface {
    public static AppPreference appPreference;
    FrameLayout container_fragment;
    public static ServiceApi serviceApi;
    //private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container_fragment = findViewById(R.id.fragment_container);
        appPreference= new AppPreference(this);

        serviceApi = RetrofitClient.getApiClient(Constant.baseUrl.BASE_URL).create(ServiceApi.class);
        if(container_fragment!=null)
        {
            if(savedInstanceState!=null)
            {
                return;
            }
            if(appPreference.getLoginStatus())
            {

                getSupportFragmentManager().
                        beginTransaction().
                        add(R.id.fragment_container,new ProfileFragment())
                        .commit();
            }
            else
            {
                getSupportFragmentManager().
                        beginTransaction().
                        add(R.id.fragment_container,new LoginFragment())
                        .commit();
            }
        }

    }

    @Override
    public void register() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,new RegistrationFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void login(String orgname,String name, String email, String created_at) {
        appPreference.setDisplayOrgName(orgname);
        appPreference.setDisplayName(name);
        appPreference.setDisplayEmail(email);
        appPreference.setDisplayDate(created_at);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,new ProfileFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void logout() {
        appPreference.setLoginStatus(false);
        appPreference.setDisplayOrgName("Organization Name");
        appPreference.setDisplayName("Name");
        appPreference.setDisplayEmail("Email");
        appPreference.setDisplayDate("Date");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,new LoginFragment())
                .commit();
    }
}

package omkar.tilekar.loginapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import omkar.tilekar.loginapp.activities.MainActivity;
import omkar.tilekar.loginapp.R;
import omkar.tilekar.loginapp.services.MyInterface;

public class ProfileFragment extends Fragment {
    TextView name,email;
    Button logoutButton;
    MyInterface myInterface_profile;
    public ProfileFragment() {
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
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        //orgname1 = view.findViewById(R.id.orgname);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);

        //String Org = "Welcome To"+ MainActivity.appPreference.getDisplayOrgName();
        //orgname1.setText(Org);
        String Name = "Hi"+ MainActivity.appPreference.getDisplayName();
        name.setText(Name);
        String Email="Registered Email->"+ MainActivity.appPreference.getDisplayEmail()+"\n Register_At->"+ MainActivity.appPreference.getDisplayDate();
        email.setText(Email);
        // name.setText("Hi"+ MainActivity.appPreference.getDisplayName());
        //email.setText("Registered on:"+MainActivity.appPreference.getDisplayEmail() +".Since :"+MainActivity.appPreference.setDisplayDate());

        logoutButton = view.findViewById(R.id.logoutBtn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myInterface_profile.logout();
            }
        });
        return view;
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity= (Activity) context;
        myInterface_profile = (MyInterface) activity;

    }

}

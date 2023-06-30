package edu.uncc.loginApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.uncc.inclass0009.R;

public class MainActivity extends AppCompatActivity  implements LoginFragment.LoginFragmentListener,RegisterFragment.RegisterFragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.maincontainer, new LoginFragment()).commit();
    }

    @Override
    public void GoToRegisterPage() {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new RegisterFragment()).commit();

    }

    @Override
    public void GoToForumsFragment() {

    }

    @Override
    public void GoToForum() {

    }

    @Override
    public void GoToLogin() {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new LoginFragment()).commit();
    }
}
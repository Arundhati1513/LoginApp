package edu.uncc.loginApp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.uncc.inclass0009.R;
import edu.uncc.inclass0009.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {
    EditText LoginEmail;
    EditText LoginPassword;
    Button LoginButton;
    Button  CreateAccountButton;
    FragmentLoginBinding binding;
    FirebaseAuth mAuth;
    LoginFragmentListener mListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (LoginFragmentListener)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Login");
        LoginEmail = view.findViewById(R.id.LoginEmail);
        LoginPassword = view.findViewById(R.id.LoginPassword);
        LoginButton = view.findViewById(R.id.LoginButton);
        CreateAccountButton = view.findViewById(R.id.CreateAccountButton);


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.GoToRegisterPage();
            }
        });
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                String Email = LoginEmail.getText().toString();
                String Password = LoginPassword.getText().toString();
                if (Email.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Valid Email", Toast.LENGTH_SHORT).show();
                } else if (Password.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Valid Password", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Log.d("Aru","On Success..You are signed in ");
                                Log.d("Aru","User Id is " + mAuth.getCurrentUser().getUid());
                                mListener.GoToRegisterPage();
                            }
                            else {
                                Log.d("Aru","onComplete Error !!");
                                Log.d("Aru","onComplete" + task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });
    }


    interface LoginFragmentListener{
        void GoToRegisterPage();
        void GoToForumsFragment();
    }
}
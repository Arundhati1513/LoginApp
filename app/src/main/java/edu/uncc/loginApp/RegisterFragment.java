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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import edu.uncc.inclass0009.R;
import edu.uncc.inclass0009.databinding.FragmentLoginBinding;

public class RegisterFragment extends Fragment {
    FragmentLoginBinding fragmentLoginBinding;
    EditText signupname;
    EditText signupemail;
    EditText signuppassword;
    Button signupsubmitbutton;
    Button cancelsignupbutton;
    RegisterFragmentListener mListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (RegisterFragmentListener) context;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container ,false);
        getActivity().setTitle("Signup Page");



        return fragmentLoginBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signupname = view.findViewById(R.id.signupname);
        signupemail = view.findViewById(R.id.signupemail);
        signuppassword = view.findViewById(R.id.signuppassword);
        signupsubmitbutton = view.findViewById(R.id.signupsubmitbutton);
        cancelsignupbutton = view.findViewById(R.id.cancelsignupbutton);

        signupsubmitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signupemail.getText().toString();
                String password = signuppassword.getText().toString();
                String name = signupname.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(getActivity(), "Enter the Email ", Toast.LENGTH_SHORT).show();
                }  if (password.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter the Password", Toast.LENGTH_SHORT).show();
                } if (name.isEmpty()){
                    Toast.makeText(getActivity(), "Enter the name ", Toast.LENGTH_SHORT).show();
                }

                else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                UserProfileChangeRequest userprofile = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                                FirebaseAuth.getInstance().getCurrentUser().updateProfile(userprofile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                          FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                                            HashMap<String,Object>  data = new HashMap<>();


                                            data.put("name",name);
                                            data.put("uid",user.getUid());
                                            FirebaseFirestore.getInstance().collection("Users")
                                                    .document(user.getUid()).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    mListener.GoToForum();
                                                    Log.d("Aru","User Created Successfully " + FirebaseAuth.getInstance().getUid());
                                                }
                                            });


                                        }


                                    }
                                });





                            }
                        }
                    });
                }
            }
        });
    }

    interface RegisterFragmentListener{
        void GoToForum();
        void GoToLogin();
    }
}
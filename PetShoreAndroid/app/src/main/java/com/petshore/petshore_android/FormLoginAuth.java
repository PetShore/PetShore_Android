package com.petshore.petshore_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.NavHost;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.petshore.petshore_android.databinding.ActivityLoginBinding;
import com.petshore.petshore_android.network.RetrofitGenerator;
import com.petshore.petshore_android.network.model.TokenUser;
import com.petshore.petshore_android.network.service.AuthServices;
import com.petshore.petshore_android.network.storage.Storage;
import com.petshore.petshore_android.network.storage.sharePreferencesStorage;

import retrofit2.Retrofit;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class FormLoginAuth extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private Storage storage;
    private String rol;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        System.out.println("/*/***************/***********////");
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Spinner spinner = binding.Rol;
        String[] valores = {"Cliente","Tienda"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                rol = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // vacio

            }
        });


        final Button loginButton = binding.login;
        loginButton.setOnClickListener(view1 -> {
            if (validLoginForm()) {
                sendAuthRequest();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_preferences", Context.MODE_PRIVATE);
        //SharedPreferences sharedPreferences = requireContext().getSharedPreferences("SHARED_preferences", Context.MODE_PRIVATE);
        storage = new sharePreferencesStorage(sharedPreferences);
    }

    private void sendAuthRequest() {
        Retrofit retrofit = RetrofitGenerator.getInstance(storage);
        AuthServices authService = retrofit.create(AuthServices.class);
        //TokenUser tokenUser = new TokenUser("prueba","prueba", null);
        TokenUser tokenUser = new TokenUser(binding.username.getText().toString(), binding.password.getText().toString(), null);
        //LoginDto loginDto = new LoginDto(binding.inputUsername.getText().toString(), binding.inputPassword.getText().toString());
        Action1<TokenUser> successAction = tokenDto -> onAuthSuccess(tokenUser.getToken());
        Action1<Throwable> failedAction = throwable -> Log.e("Developer", "Auth error", throwable);
        authService.login(tokenUser)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(ContextCompat.getMainExecutor(getApplicationContext())))
                .subscribe(successAction, failedAction);
    }

    private boolean validLoginForm() {
        Editable usernameEditText = binding.username.getText();
        Editable passwordEditText = binding.password.getText();
        //f
        if(usernameEditText.length() == 0 || passwordEditText.length() == 0){
            binding.username.setError(getString(R.string.invalid_username));
            binding.password.setError(getString(R.string.invalid_username));
            return false;
        }else
        {
            binding.username.setError(null);
            binding.password.setError(null);
            return true;
        }
    }

    private void onAuthSuccess(String token){
        Log.d("Developer","TokenDto: " + token);
        Log.d("Developer","Rol: " + rol);
        storage.saveToken(token); //Value of token its lost if the app is uninstalled Ma
        Intent intent = new Intent(FormLoginAuth.this, MainActivity.class);
        startActivity(intent);
        //Navigation.findNavController(FormLoginAuth.this, R.id.);
    }


}

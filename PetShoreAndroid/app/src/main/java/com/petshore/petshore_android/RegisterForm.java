package com.petshore.petshore_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.PersistableBundle;
import android.text.Editable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.petshore.petshore_android.databinding.ActivityLoginBinding;
import com.petshore.petshore_android.databinding.ActivityRegisterFormBinding;
import com.petshore.petshore_android.network.RetrofitGenerator;
import com.petshore.petshore_android.network.model.Usuario;
import com.petshore.petshore_android.network.service.AuthServices;
import com.petshore.petshore_android.network.storage.Storage;
import com.petshore.petshore_android.network.storage.sharePreferencesStorage;

import java.net.HttpURLConnection;

import retrofit2.Retrofit;
import rx.Scheduler;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RegisterForm extends AppCompatActivity {
    private ActivityRegisterFormBinding binding;
    private Storage storage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Button registerButton = binding.buttonRegistrarse;
        registerButton.setOnClickListener(view1 -> {
            if(validRegisterForm()){
                sendRegisterRequest();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_preferences", Context.MODE_PRIVATE);
        //SharedPreferences sharedPreferences = requireContext().getSharedPreferences("SHARED_preferences", Context.MODE_PRIVATE);
        storage = new sharePreferencesStorage(sharedPreferences);
    }

    private void sendRegisterRequest() {
        Retrofit retrofit = RetrofitGenerator.getInstance(storage);
        AuthServices regService = retrofit.create(AuthServices.class);
        Usuario usuarioNuevo = new Usuario(binding.editTextTextPersonName.getText().toString(),
                binding.editTextTextPersonName.getText().toString(),
                binding.editPassword.getText().toString(),
                binding.editTextCorreo.getText().toString(),
                binding.editTextBarrio.getText().toString(),
                binding.editTextDireccion.getText().toString(),
                binding.editTextPhone.getText().toString());
        Action1<HttpURLConnection> successAction = usuarioRegistrado -> onRegisterSucces(usuarioRegistrado);
        Action1<Throwable> failedAction = throwable -> Log.e("Developer","Register Error", throwable);
        regService.addUser(usuarioNuevo)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(ContextCompat.getMainExecutor(getApplicationContext())))
                .subscribe(successAction, failedAction);
    }

    private void onRegisterSucces(HttpURLConnection httpURLConnection) {
        Log.d("Developer","Register: " + "Succes!!!");
        Intent home = new Intent(RegisterForm.this, FormLoginAuth.class);
        startActivity(home);
    }

    private boolean validRegisterForm() {
        Editable usuarioEditText = binding.editTextTextPersonName.getText();
        Editable correoEditText = binding.editTextCorreo.getText();
        Editable BarrioEditText = binding.editTextBarrio.getText();
        Editable direccionEditText = binding.editTextDireccion.getText();
        Editable celularEditText = binding.editTextPhone.getText();
        Editable passwordEditText = binding.editPassword.getText();
        Editable conPasswordEditText = binding.editTextTextPassword2.getText();

        if(usuarioEditText.length() == 0){
            binding.editTextTextPersonName.setError("Usuario no valido");
            return false;
        } else if(correoEditText.length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(correoEditText).matches()){
            binding.editTextCorreo.setError("Correo no valido");
            return false;
        } else if(BarrioEditText.length() == 0){
            binding.editTextBarrio.setError("Barrio No Valido");
            return false;
        } else if(direccionEditText.length() == 0){
            binding.editTextDireccion.setError("Direccion no valida");
            return false;
        } else if(celularEditText.length() == 0){
            binding.editTextPhone.setError("Celelar no valido");
            return false;
        } else if(passwordEditText.length() == 0 || conPasswordEditText.length() == 0){
            binding.editPassword.setError("Password Incorrecta");
            binding.editTextTextPassword2.setError("Password Incorrecta");
            return false;
        } else if(!passwordEditText.toString().equals(conPasswordEditText.toString())){
            binding.editPassword.setError("Password no coincide");
            binding.editTextTextPassword2.setError("Password no coincide");
            return false;
        } else {
            binding.editTextTextPersonName.setError(null);
            binding.editTextCorreo.setError(null);
            binding.editTextBarrio.setError(null);
            binding.editTextDireccion.setError(null);
            binding.editTextPhone.setError(null);
            binding.editTextCorreo.setError(null);
            binding.editTextTextPassword2.setError(null);
            binding.editPassword.setError(null);
            return true;
        }

    }
}
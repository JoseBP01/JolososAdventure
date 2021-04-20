package com.mygdx.game.Screens;

import com.mygdx.game.FirebaseController.FirebaseAuth;
import com.mygdx.game.JadventureMain;
import com.mygdx.game.MyWidgets.MyScreen;
import de.tomgrill.gdxfirebase.core.GDXFirebase;
import de.tomgrill.gdxfirebase.core.auth.AuthStateListener;

public class RegisterScreen extends MyScreen {

    private final FirebaseAuth firebaseAuth;

    public RegisterScreen(JadventureMain game) {
        super(game);
        firebaseAuth = new FirebaseAuth();
    }

    public void registrarUsuario(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password);
        GDXFirebase.FirebaseAuth().addAuthStateListener(new AuthStateListener() {
            @Override
            public void onAuthStateChanged(de.tomgrill.gdxfirebase.core.auth.FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    new MenuScreen(game);
                }else {
                    System.out.println("error al iniciar sesion");
                }
            }
        });
    }

    @Override
    public void show() {
        super.show();

    }
}

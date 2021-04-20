package com.mygdx.game.FirebaseController;

import de.tomgrill.gdxfirebase.core.auth.AuthCredential;
import de.tomgrill.gdxfirebase.core.auth.AuthProvider;
import de.tomgrill.gdxfirebase.core.auth.AuthStateListener;
import de.tomgrill.gdxfirebase.core.auth.FirebaseUser;

public class FirebaseAuth implements de.tomgrill.gdxfirebase.core.auth.FirebaseAuth {
    @Override
    public FirebaseUser getCurrentUser() {
        return null;
    }

    @Override
    public void addAuthStateListener(AuthStateListener authStateListener) {

    }

    @Override
    public void removeAuthStateListener(AuthStateListener authStateListener) {

    }

    @Override
    public void signInWithCredential(AuthCredential authCredential) {

    }

    @Override
    public void signInWithCustomToken(String token) {

    }

    @Override
    public void signInWithEmailAndPassword(String email, String password) {

    }

    @Override
    public void signInAnonymously() {

    }

    @Override
    public void createUserWithEmailAndPassword(String email, String password) {

    }

    @Override
    public void fetchProvidersForEmail(String email) {

    }

    @Override
    public void sendPasswordResetEmail(String email) {

    }

    @Override
    public void signOut() {

    }

    @Override
    public AuthProvider FacebookAuthProvider(String accessToken) {
        return null;
    }
}

package com.mygdx.game.FirebaseController;

import de.tomgrill.gdxfirebase.core.LogLevel;
import de.tomgrill.gdxfirebase.core.database.DatabaseReference;
import de.tomgrill.gdxfirebase.core.database.FirebaseDatabase;

public class RealTimeDB implements FirebaseDatabase {
    @Override
    public DatabaseReference getReference() {
        return null;
    }

    @Override
    public DatabaseReference getReference(String s) {
        return null;
    }

    @Override
    public String getSdkVersion() {
        return null;
    }

    @Override
    public void setPersistenceEnabled(boolean isEnabled) {

    }

    @Override
    public void setLogLevel(LogLevel logLevel) {

    }

    @Override
    public void goOnline() {

    }

    @Override
    public void goOffline() {

    }

    @Override
    public void purgeOutstandingWrites() {

    }

    @Override
    public DatabaseReference getReferenceFromUrl(String url) {
        return null;
    }
}

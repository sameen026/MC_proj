package com.example.myapplication.Model;

public class SavedPlaza {
    String savedPlazaId;
    String userId;

    public String getSavedPlazaId() {
        return savedPlazaId;
    }

    public void setSavedPlazaId(String savedPlazaId) {
        this.savedPlazaId = savedPlazaId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlazaId() {
        return plazaId;
    }

    public void setPlazaId(String plazaId) {
        this.plazaId = plazaId;
    }

    String plazaId;

    public SavedPlaza(String savedPlazaId, String userId, String plazaId) {
        this.savedPlazaId = savedPlazaId;
        this.userId = userId;
        this.plazaId = plazaId;
    }
}

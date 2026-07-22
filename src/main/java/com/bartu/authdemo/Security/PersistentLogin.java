package com.bartu.authdemo.Security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "persistent_logins")
public class PersistentLogin {

    @Id
    @Column(nullable = false, length = 64)
    private String series;

    @Column(nullable = false, length = 255)
    private String username;

    @Column(nullable = false, length = 64)
    private String token;

    @Column(name = "last_used", nullable = false)
    private Instant lastUsed;

    public PersistentLogin() {

    }

    public PersistentLogin(String series, String username, String token, Instant lastUsed) {
        this.series = series;
        this.username = username;
        this.token = token;
        this.lastUsed = lastUsed;
    }

    public String getSeries() {
        return series;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public Instant getLastUsed() {
        return lastUsed;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setLastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
    }
}
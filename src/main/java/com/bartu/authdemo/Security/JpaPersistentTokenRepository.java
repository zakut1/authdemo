package com.bartu.authdemo.Security;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public class JpaPersistentTokenRepository implements PersistentTokenRepository {

    private final PersistentLoginRepository persistentLoginRepository;

    public JpaPersistentTokenRepository(PersistentLoginRepository persistentLoginRepository) {
        this.persistentLoginRepository = persistentLoginRepository;
    }

    @Override
    @Transactional
    public void createNewToken(PersistentRememberMeToken token) {
        PersistentLogin persistentLogin = new PersistentLogin(
                token.getSeries(),
                token.getUsername(),
                token.getTokenValue(),
                token.getDate().toInstant()
        );

        persistentLoginRepository.save(persistentLogin);
    }

    @Override
    @Transactional
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentLogin persistentLogin = persistentLoginRepository.findById(series)
                .orElseThrow(() -> new IllegalStateException("Persistent login token not found"));

        persistentLogin.setToken(tokenValue);
        persistentLogin.setLastUsed(lastUsed.toInstant());

        persistentLoginRepository.save(persistentLogin);
    }

    @Override
    @Transactional(readOnly = true)
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return persistentLoginRepository.findById(seriesId)
                .map(token ->
                        new PersistentRememberMeToken(
                        token.getUsername(),
                        token.getSeries(),
                        token.getToken(),
                        Date.from(token.getLastUsed())
                ))
                .orElse(null);
    }

    @Override
    @Transactional
    public void removeUserTokens(String username) {
        persistentLoginRepository.deleteAllByUsername(username);
    }
}
package com.security.secretstore.core.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class MasterKeyUtil {

    private final String hashAlgorithm;

    public MasterKeyUtil(@Value("${app.algorithm.SHA}") String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    public byte[] generateMasterKey(String[] subKeys)
            throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(hashAlgorithm);
        for (String key : subKeys) {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            digest.update(keyBytes);
        }
        return digest.digest();
    }
}

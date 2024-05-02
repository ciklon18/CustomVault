package com.security.secretstore.manager;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MasterKeyManager {

    private final String hashAlgorithm;

    public MasterKeyManager(String hashAlgorithm) {
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

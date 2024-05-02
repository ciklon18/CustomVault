package com.security.secretstore.manager;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionManager {

    private final String algorithm;
    private final Cipher cipher;

    public EncryptionManager(String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.algorithm = algorithm;
        this.cipher = Cipher.getInstance(algorithm);
    }

    public String encrypt(String secret, byte[] masterKey) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec keySpec = new SecretKeySpec(masterKey, algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(secret.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedData, byte[] masterKey) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec keySpec = new SecretKeySpec(masterKey, algorithm);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedBytes = Base64.getDecoder().decode(encryptedData);
        return new String(cipher.doFinal(decryptedBytes));
    }
}

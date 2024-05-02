package com.security.secretstore.core.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class EncryptionUtil {

    private final String hashAlgorithm;
    private final Cipher cipher;

    public EncryptionUtil(@Value("${app.algorithm.hash}") String hashAlgorithm)
            throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.hashAlgorithm = hashAlgorithm;
        this.cipher = Cipher.getInstance(hashAlgorithm);
    }

    public String encrypt(String secret, byte[] masterKey) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec keySpec = new SecretKeySpec(masterKey, hashAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(secret.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedData, byte[] masterKey) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec keySpec = new SecretKeySpec(masterKey, hashAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedBytes = Base64.getDecoder().decode(encryptedData);
        return new String(cipher.doFinal(decryptedBytes));
    }
}

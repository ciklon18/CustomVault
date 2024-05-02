package com.security.secretstore.core.service;

import com.security.secretstore.core.entity.Secret;
import com.security.secretstore.core.repository.SecretRepository;
import com.security.secretstore.core.util.EncryptionUtil;
import com.security.secretstore.core.util.MasterKeyUtil;
import com.security.secretstore.core.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecretService {
    private final SecretRepository secretRepository;
    private final EncryptionUtil encryptionUtil;
    private final MasterKeyUtil masterKeyUtil;
    private final TokenUtil tokenUtil;

    @Transactional
    public void sealAllSecrets(String[] subKeys) {
        try {
            byte[] masterKey = masterKeyUtil.generateMasterKey(subKeys);
            List<Secret> secrets = secretRepository.findAll();
            for (Secret secret : secrets) {
                String encryptedData = encryptionUtil.encrypt(secret.getPayload(), masterKey);
                secret.setPayload(encryptedData);
                secretRepository.save(secret);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to seal secrets", e);
        }
    }


    public String wrapSecret(UUID secretId, String[] subKeys) {
        try {
            byte[] masterKey = masterKeyUtil.generateMasterKey(subKeys);
            String secretData = getSecretPayload(secretId);
            String encryptedData = encryptionUtil.encrypt(secretData, masterKey);
            return tokenUtil.createToken(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to wrap secret", e);
        }
    }

    public String unwrapSecret(String token, String[] subKeys) {
        try {
            byte[] masterKey = masterKeyUtil.generateMasterKey(subKeys);
            String encryptedData = tokenUtil.getToken(token);
            if (encryptedData == null) {
                throw new IllegalArgumentException("Invalid or expired token");
            }
            return encryptionUtil.decrypt(encryptedData, masterKey);
        } catch (Exception e) {
            throw new RuntimeException("Failed to unwrap secret", e);
        }
    }

    private String getSecretPayload(UUID secretId) {
        Optional<Secret> secretOptional = secretRepository.findById(secretId);
        if (secretOptional.isEmpty()) {
            throw new RuntimeException("Secret with id = " + secretId + " not found");
        }
        return secretOptional.get().getPayload();
    }

    public UUID postSecret(String secret) {
        Secret secretEntity = new Secret(secret);
        return secretRepository.save(secretEntity).getId();
    }
}

package com.security.secretstore.config;

import com.security.secretstore.core.util.EncryptionUtil;
import com.security.secretstore.core.util.MasterKeyUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class SecretConfig {

    private final String hashAlgorithm = "AES";

    @Bean
    public EncryptionUtil encryptionManager()
            throws NoSuchPaddingException, NoSuchAlgorithmException {
        return new EncryptionUtil(this.hashAlgorithm);
    }

    @Bean
    public MasterKeyUtil masterKeyManager() {
        return new MasterKeyUtil(this.hashAlgorithm);
    }
}

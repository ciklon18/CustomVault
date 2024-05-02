package com.security.secretstore.core.util.eliptic;


import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;

import java.security.*;
import java.security.spec.InvalidKeySpecException;

public class ElipticGenerator {
    public static void f() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeySpecException, SignatureException, InvalidKeyException{
        // Добавляем Bouncy Castle в качестве провайдера
        Security.addProvider(new BouncyCastleProvider());

        try {
            // Выбираем параметры кривой (например, secp256r1)
            ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256r1");

            // Генерируем секретный и публичный ключи
            KeyPairGenerator generator = KeyPairGenerator.getInstance("ECDSA", "BC");
            generator.initialize(spec, new SecureRandom());
            KeyPair keyPair = generator.generateKeyPair();
            ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
            ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

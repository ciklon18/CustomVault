package com.security.secretstore.api.controller;

import com.security.secretstore.core.service.SecretService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SecretController {
    private final SecretService secretService;

    @PostMapping("api/secret")
    public UUID postSecret(@RequestParam String secret){
        return secretService.postSecret(secret);
    }
    @PostMapping("api/seal-secrets")
    public ResponseEntity<String> sealAllSecrets(@RequestParam String[] subKeys) {
        try {
            secretService.sealAllSecrets(subKeys);
            return ResponseEntity.ok("Secrets sealed successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error sealing secrets: " + e.getMessage());
        }
    }

    @PostMapping("api/wrap-secret/{secretId}")
    public ResponseEntity<String> wrapSecret(@PathVariable UUID secretId, @RequestParam String[] subKeys) {
        try {
            String token = secretService.wrapSecret(secretId, subKeys);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error wrapping secret: " + e.getMessage());
        }
    }

    @GetMapping("api/unwrap-secret")
    public ResponseEntity<String> unwrapSecret(@RequestParam String token, @RequestParam String[] subKeys) {
        try {
            String secret = secretService.unwrapSecret(token, subKeys);
            return ResponseEntity.ok(secret);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error unwrapping secret: " + e.getMessage());
        }
    }
}

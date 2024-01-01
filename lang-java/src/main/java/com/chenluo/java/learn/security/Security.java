package com.chenluo.java.learn.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.*;

public class Security {
    private static final String originalMsg = "content to hash";

    public static void main(String[] args)
            throws NoSuchAlgorithmException, SignatureException, InvalidKeyException,
                   NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Security security = new Security();
        security.msgDigest();
        security.msgSig();
        security.encryptAndDecryptRSA();
    }

    private void msgDigest() throws NoSuchAlgorithmException {
        MessageDigest instance = java.security.MessageDigest.getInstance("SHA-256");
        byte[] digest = instance.digest(originalMsg.getBytes());
        System.out.println("original message: %s".formatted(originalMsg));
        System.out.println(
                "digest via SHA-256: %s".formatted(Base64.getEncoder().encodeToString(digest)));

        byte[] changed = instance.digest((originalMsg + "slight change").getBytes());
        System.out.println("[changed] digest via SHA-256: %s".formatted(
                Base64.getEncoder().encodeToString(changed)));
    }

    private void msgSig()
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException,
                   NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
        rsa.initialize(2048);
        KeyPair rsaKeyPair = rsa.generateKeyPair();

        Signature sigInst = Signature.getInstance("SHA256withRSA");
        sigInst.initSign(rsaKeyPair.getPrivate());
        sigInst.update(originalMsg.getBytes());
        byte[] sig = sigInst.sign();
        System.out.println("computed sig by private key: %s".formatted(
                Base64.getEncoder().encodeToString(sig)));

        Signature verifyInst = Signature.getInstance("SHA256withRSA");
        verifyInst.initVerify(rsaKeyPair.getPublic());
        verifyInst.update(originalMsg.getBytes());
        boolean verify = verifyInst.verify(sig);
        System.out.println("verified? %s".formatted(verify));

        // not using Signature tool
        // compute digest and use private key to compute signature
        MessageDigest instance = MessageDigest.getInstance("SHA-256");
        byte[] digest = instance.digest(originalMsg.getBytes());
        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, rsaKeyPair.getPrivate());
        rsaCipher.update(digest);
        byte[] encrypted = rsaCipher.doFinal();
        boolean isManualSigSame = Arrays.equals(sig, encrypted);
        System.out.println("sig computed by private key is same? %s".formatted(isManualSigSame));

        rsaCipher.init(Cipher.DECRYPT_MODE, rsaKeyPair.getPublic());
        rsaCipher.update(encrypted);
        byte[] decrypted = rsaCipher.doFinal();
        System.out.println("digest: %s".formatted(Base64.getEncoder().encodeToString(digest)));
        System.out.println("encrypted from digest -> signature: %s".formatted(
                Base64.getEncoder().encodeToString(encrypted)));
        System.out.println("decrypted from signature -> digest: %s".formatted(
                Base64.getEncoder().encodeToString(decrypted)));
        System.out.println("decrypted from signature is equal to digest? %s".formatted(
                Arrays.equals(decrypted, digest)));
    }

    private void encryptAndDecryptRSA()
            throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
                   NoSuchPaddingException, NoSuchAlgorithmException {
        String msgToEncrypt = "";
        for (int i = 0; i < 1000; i++) {
            msgToEncrypt += UUID.randomUUID().toString();
        }
        KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
        rsa.initialize(2048);
        KeyPair rsaKeyPair = rsa.generateKeyPair();

        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, rsaKeyPair.getPrivate());
        int offset = 0;
        List<Byte> encrypted = new ArrayList<>();
        int originalLength = msgToEncrypt.getBytes().length;
        int chunkSizeWhenEncrypt = 245; // for rsa 2048
        while (offset < originalLength) {
            rsaCipher.update(msgToEncrypt.getBytes(), offset,
                    Math.min(chunkSizeWhenEncrypt, originalLength - offset));
            for (byte b : rsaCipher.doFinal()) {
                encrypted.add(b);
            }
            offset += chunkSizeWhenEncrypt;
        }
        System.out.println("encrypted bytes length: %s".formatted(encrypted.size()));
        offset = 0;
        rsaCipher.init(Cipher.DECRYPT_MODE, rsaKeyPair.getPublic());
        List<Byte> decrypted = new ArrayList<>();
        int chunkSizeForDecrypt = 256; // for rsa 2048
        while (offset < encrypted.size()) {
            byte[] bytes = new byte[chunkSizeForDecrypt];
            List<Byte> subList = encrypted.subList(offset, offset + chunkSizeForDecrypt);
            for (int i = 0; i < subList.size(); i++) {
                bytes[i] = subList.get(i);
            }
            rsaCipher.update(bytes);
            for (byte b : rsaCipher.doFinal()) {
                decrypted.add(b);
            }
            offset += chunkSizeForDecrypt;
        }
        System.out.println("decrypted byte length: %s".formatted(decrypted.size()));
        byte[] bytes = new byte[originalLength];
        for (int i = 0; i < decrypted.size(); i++) {
            bytes[i] = decrypted.get(i);
        }
        String decryptedString = new String(bytes);
        System.out.println(decryptedString);
        System.out.println("decrypted string and original string is equal? %s".formatted(
                decryptedString.equals(msgToEncrypt)));
    }
}

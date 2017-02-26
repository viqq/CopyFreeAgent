package com.free.agent.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.codec.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by antonPC on 15.08.15.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EncryptionUtils {
    private static final Logger LOGGER = Logger.getLogger(EncryptionUtils.class);

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: EncryptionUtils <command><args>");
            System.out.println("\t<command>: encrypt, generate-key, showProvider");
            System.exit(0);
        }
        String command = args[0];
        if (command.equalsIgnoreCase("showProvider")) {
            EncryptionUtils.showProviders();
        }
        if (command.equalsIgnoreCase("generate-key")) {
            System.out.println("New key: " + EncryptionUtils.generateKey());
        } else if (command.equalsIgnoreCase("encrypt")) {
            String plaintext = args[1];
            System.out.println(plaintext + " = " + EncryptionUtils.encrypt(plaintext));
        } else if (command.equalsIgnoreCase("decrypt")) {
            String ciphertext = args[1];
            System.out.println(ciphertext + " = " + EncryptionUtils.decrypt(ciphertext));
        }
    }

    public static String decrypt(String source) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(getBytes(getKey()), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] ciphertext = getBytes(source);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] cleartext = cipher.doFinal(ciphertext);
            return new String(cleartext);
        } catch (Exception e) {
            LOGGER.error("Password cannot be decrypted", e);
        }
        return null;
    }

    public static String encrypt(String source) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(getBytes(getKey()), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] cleartext = source.getBytes();
            byte[] ciphertext = cipher.doFinal(cleartext);
            return getString(ciphertext);
        } catch (Exception e) {
            LOGGER.error("Password cannot be encrypted", e);
        }
        return null;
    }


    public static String generateKey() {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128);
            SecretKey sKey = keygen.generateKey();
            byte[] bytes = sKey.getEncoded();
            return getString(bytes);
        } catch (Exception e) {
            LOGGER.error("Key cannot be generated", e);
        }
        return null;
    }

    public static boolean isEncrypted(String text) {
        if (text.indexOf('-') == -1) {
            LOGGER.info("text is not encrypted: no dashes");
            return false;
        }

        StringTokenizer st = new StringTokenizer(text, "-", false);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.length() > 3) {
                LOGGER.info("text is not encrypted: length of token greater than 3: " + token);
                return false;
            }
            for (int i = 0; i < token.length(); i++) {
                if (!Character.isDigit(token.charAt(i))) {
                    LOGGER.info("text is not encrypted: token is not a digit");
                    return false;
                }
            }
        }
        LOGGER.info("text is encrypted");
        return true;
    }

    private static String getString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            sb.append((0x00FF & b));
            if (i + 1 < bytes.length) {
                sb.append("-");
            }
        }
        return sb.toString();
    }

    private static byte[] getBytes(String str) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        StringTokenizer st = new StringTokenizer(str, "-", false);
        while (st.hasMoreTokens()) {
            int i = Integer.parseInt(st.nextToken());
            bos.write((byte) i);
        }
        return bos.toByteArray();
    }

    private static void showProviders() {
        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            System.out.println("Provider: " + provider.getName() + ", " + provider.getInfo());
            for (Object element : provider.keySet()) {
                String key = (String) element;
                String value = (String) provider.get(key);
                System.out.println("\t" + key + " = " + value);
            }

        }
    }

    private static String getKey() {
        String key;
        File file = new File("/etc/free_agent_key");
        try {
            List<String> lines = FileUtils.readLines(file, "UTF-8");
            key = lines.get(0);
        } catch (IOException e) {
            LOGGER.warn("Use default key 179-148-214...");
            key = "179-148-214-15-232-42-145-147-198-251-203-43-228-134-119-188";
        }
        return key;
    }

    public static String md5(String source) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(source.getBytes(Charset.forName("UTF8")));
            final byte[] resultByte = messageDigest.digest();
            return new String(Hex.encode(resultByte));
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Password cannot be encrypted", e);
        }
        return null;
    }

    public static String getRandomString() {
        String symbols = "qwertyuiopasdfghjklzxcvbnm";
        String digits = "1234567890";
        String all = symbols + digits;
        StringBuilder randString = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                randString.append(symbols.toUpperCase().charAt((int) (Math.random() * symbols.length())));
            }
            if (i % 4 == 0) {
                randString.append(digits.charAt((int) (Math.random() * digits.length())));
            }
            randString.append(all.charAt((int) (Math.random() * all.length())));

        }
        return randString.toString();
    }


}

package com.example.park.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * This class contains the utils.
 */
public class Utils {

    static final Logger LOGGER = Logger.getLogger(String.valueOf(com.example.park.server.Utils.class));

    /**
     * Get the keystore managers in the given path.
     *
     * @param path     KeyManager path.
     * @param password KeyManager password.
     * @param type     KeyManager type.
     * @return KeyManager.
     * @throws NoSuchAlgorithmException If an error occurred while getting the KeyManager.
     */
    public static KeyManager[] getKeyManagers(String path, char[] password, String type)
            throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException {

        KeyStore keystore = getKeystore(path, password, type);
        KeyManagerFactory keyManagerFactory =
                KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keystore, password);
        return keyManagerFactory.getKeyManagers();
    }

    /**
     * Get trust managers in the given path.
     *
     * @param path     Trust manager path.
     * @param password rust manager password.
     * @param type     Type.
     * @return List of trust managers.
     * @throws NoSuchAlgorithmException If an error occurred while getting the trust managers.
     */
    public static TrustManager[] getTrustManagers(String path, char[] password, String type)
            throws NoSuchAlgorithmException, KeyStoreException {

        KeyStore truststore = getKeystore(path, password, type);
        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(truststore);
        return trustManagerFactory.getTrustManagers();
    }

    private static KeyStore getKeystore(String filePath, char[] password, String type) {

        Path path = Paths.get(filePath);
        KeyStore keystore = null;
        try (InputStream in = Files.newInputStream(path)) {
            keystore = KeyStore.getInstance(type);
            keystore.load(in, password);
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            LOGGER.log(Level.SEVERE, "Error in reading keystores");
            System.exit(1);
        }
        return keystore;
    }
}


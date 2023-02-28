/*
javadoc
 */
package com.example.park.server;

import io.grpc.Grpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.TlsServerCredentials;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Car Park Server.
 */
public class CarParkServer {
    static final Logger LOGGER = Logger.getLogger("CarParkServer");
    public static void main(String[] args) throws IOException, InterruptedException,
            UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {

        TlsServerCredentials.ClientAuth clientAuth = TlsServerCredentials.ClientAuth.REQUIRE;
        TlsServerCredentials.Builder tlsBuilder = TlsServerCredentials.newBuilder()
                .keyManager(Utils.getKeyManagers("<path-to-keystore>/carparkserver-keystore.jks",
                        "<keystore-password>".toCharArray(),
                        "JKS"))
                .trustManager(Utils.getTrustManagers("<path-to-truststore>/carparkserver-truststore.jks",
                        "<truststore-password>".toCharArray(),
                        "JKS"))
                .clientAuth(clientAuth);
        Server server = Grpc.newServerBuilderForPort(40404, tlsBuilder.build())
                .addService(new CarParkServiceImpl())
                .build();
        server.start();
        LOGGER.log(Level.INFO, "Server is running");
        server.awaitTermination();
    }
}

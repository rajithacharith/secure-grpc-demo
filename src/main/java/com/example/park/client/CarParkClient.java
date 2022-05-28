package com.example.park.client;


import com.example.park.CarParkServiceGrpc;
import com.example.park.ParkRequestManyTimes;
import com.example.park.Vehicle;
import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.TlsChannelCredentials;
import io.grpc.netty.GrpcSslContexts;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import java.io.File;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;

/**
 * CarPark Client.
 */
public class CarParkClient {
    static final Logger LOGGER = Logger.getLogger("CarParkClient");
    public static void main(String[] args)
            throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {

        TlsChannelCredentials.Builder tlsBuilder = TlsChannelCredentials.newBuilder();
        tlsBuilder.keyManager(Utils.getKeyManagers("scripts/carparkclient-keystore.jks",
                "111111".toCharArray(), "JKS"));
        tlsBuilder.trustManager(Utils.getTrustManagers("scripts/carparkclient-truststore.jks",
                "111111".toCharArray(), "JKS"));

        ManagedChannel channel = Grpc.newChannelBuilderForAddress("localhost", 40404, tlsBuilder.build()).build();

        CarParkServiceGrpc.CarParkServiceBlockingStub stub = CarParkServiceGrpc.newBlockingStub(channel);
        ParkRequestManyTimes parkRequest = ParkRequestManyTimes.newBuilder()
                .setVehicle(Vehicle.newBuilder()
                        .setVehicleNumber("AAA")
                        .setVehicleType("Test")
                        .build())
                .build();

        stub.parkVehicleManyTimes(parkRequest).forEachRemaining(
                parkResponseManyTimes -> LOGGER.log(Level.INFO, parkResponseManyTimes.getResult())
        );
    }


}

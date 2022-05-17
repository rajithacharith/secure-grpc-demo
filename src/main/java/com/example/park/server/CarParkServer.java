/*
javadoc
 */
package com.example.park.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Car Park Server.
 */
public class CarParkServer {
    static final Logger LOGGER = Logger.getLogger("CarParkServer");
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(40404)
                .addService(new SubscriptionServiceImpl())
                .build();
        server.start();
        LOGGER.log(Level.INFO, "Server is running");
        server.awaitTermination();
    }
}

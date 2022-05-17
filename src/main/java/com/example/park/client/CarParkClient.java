package com.example.park.client;


import com.example.park.CarParkServiceGrpc;
import com.example.park.ParkRequestManyTimes;
import com.example.park.Vehicle;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CarPark Client.
 */
public class CarParkClient {
    static final Logger LOGGER = Logger.getLogger("CarParkClient");
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 40404)
                .usePlaintext()
                .build();

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

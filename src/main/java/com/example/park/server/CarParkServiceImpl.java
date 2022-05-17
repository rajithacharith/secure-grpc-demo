package com.example.park.server;

import com.example.park.*;
import io.grpc.stub.StreamObserver;

public class CarParkServiceImpl extends CarParkServiceGrpc.CarParkServiceImplBase {
    @Override
    public void parkVehicle(ParkRequest request, StreamObserver<ParkResponse> responseObserver) {
        String vehicleNo = request.getVehicle().getVehicleNumber();
        String vehicleType = request.getVehicle().getVehicleType();

        String message = "The Vehicle: "+ vehicleNo + " and type: " + vehicleType + " is parked";
        System.out.println(message);

        ParkResponse parkResponse = ParkResponse
                .newBuilder()
                .setResult(message)
                .build();
        responseObserver.onNext(parkResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void parkVehicleManyTimes(ParkRequestManyTimes request, StreamObserver<ParkResponseManyTimes> responseObserver) {
        String vehicleNo = request.getVehicle().getVehicleNumber();
        String vehicleType = request.getVehicle().getVehicleType();

        String message = "The Vehicle: "+ vehicleNo + " and type: " + vehicleType + " is parked";
        System.out.println(message);

        ParkResponseManyTimes parkResponse = ParkResponseManyTimes
                .newBuilder()
                .setResult(message)
                .build();
        responseObserver.onNext(parkResponse);
        responseObserver.onNext(parkResponse);
        responseObserver.onCompleted();
    }
}

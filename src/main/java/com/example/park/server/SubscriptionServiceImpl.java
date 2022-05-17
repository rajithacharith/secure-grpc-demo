package com.example.park.server;

import com.wso2.identity.asgardeo.subscription.DailyUsageRequest;
import com.wso2.identity.asgardeo.subscription.DailyUsageResponse;
import com.wso2.identity.asgardeo.subscription.SubscriptionServiceGrpc;
import io.grpc.stub.StreamObserver;

public class SubscriptionServiceImpl extends SubscriptionServiceGrpc.SubscriptionServiceImplBase {
    @Override
    public void updateDailyUsage(DailyUsageRequest request, StreamObserver<DailyUsageResponse> responseObserver) {
        String message = "Daily usage for " + request.getTenantDomain() + " is " + request.getUsage();
        System.out.println(message);
        DailyUsageResponse response = DailyUsageResponse
                .newBuilder().setResponse("Done").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

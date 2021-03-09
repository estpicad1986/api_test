package specifications;

import helper.RequestHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {

    public static RequestSpecification generateToken() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String token = RequestHelper.getUserToken();
        requestSpecBuilder.addHeader("Authorization", "Bearer " + token);
        return requestSpecBuilder.build();
    };//generateToken

    public static RequestSpecification generateFakeToken() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addHeader("Authorization", "Bearer ecJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NfdXVpZCI6ImVlMzVkZDUwLTc1OTktNDE0My05NGZkLTMzZTljNDRkNDg1OCIsImF1dGhvcml6ZWQiOnRydWUsImV4cCI6MTYxNDU0MjQxNywidXNlcl9pZCI6NTc0fQ.Dj3-KsOyESgWYeN2WLPjyNg5uDXE-ycSchLoB8pDRyc");
        return requestSpecBuilder.build();
    };//generateFakeToken
}

package com.code.springbootlibrary.service;

import com.code.springbootlibrary.responsemodels.ProfileResponse;
import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Clients;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.UserApi;
import org.openapitools.client.model.User;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class ProfileService {

    private final static String Token = "00XUoKjh1f9xDfWTOVBV8OrymM5HumxZVeU2eKa3-G";


    public ProfileResponse getUser( String userId){
        ProfileResponse profileResponse = new ProfileResponse();
        try {
            ApiClient apiClient = Clients.builder()
                    .setOrgUrl("https://dev-61036179.okta.com")
                    .setClientId("0oag7m41baZ5e9GRN5d7")
                    .setClientCredentials(new TokenClientCredentials(Token))
                    .build();

            UserApi userApi = new UserApi(apiClient);
            User user = userApi.getUser(userId);
            profileResponse.setFirstName(Objects.requireNonNull(user.getProfile()).getFirstName());
            profileResponse.setLastName(Objects.requireNonNull(user.getProfile()).getLastName());
            profileResponse.setEmail(Objects.requireNonNull(user.getProfile()).getEmail());
            profileResponse.setUsername(user.getProfile().getLogin());
        } catch (Exception e) {
            // Handle the error
            System.out.println("Error fetching user: " + e.getMessage());
        }

        return profileResponse;
    }
}

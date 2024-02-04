package co.il.treat.treatassign.api;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

@Slf4j
public class FeignConfig {

    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    public FeignConfig(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
        this.oAuth2AuthorizedClientManager = oAuth2AuthorizedClientManager;
    }

    @Bean
    public RequestInterceptor interceptor (){
        return requestTemplate -> requestTemplate.header("Authorization", "Bearer "+getAccessToken());
    }


    private String getAccessToken() {
        String res = null;
        OAuth2AuthorizeRequest oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("external")
                .principal("principal-name")
                .build();
        OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientManager.authorize(oAuth2AuthorizeRequest);
        if (oAuth2AuthorizedClient !=null) {
            OAuth2AccessToken oAuth2AccessToken = oAuth2AuthorizedClient.getAccessToken();
            if (oAuth2AccessToken != null) {
                res = oAuth2AccessToken.getTokenValue();
            }
        }
        return res;
    }
}

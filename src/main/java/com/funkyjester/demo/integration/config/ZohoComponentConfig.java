package com.funkyjester.demo.integration.config;

import com.zoho.api.authenticator.OAuthToken;
import com.zoho.api.authenticator.Token;
import com.zoho.api.authenticator.store.FileStore;
import com.zoho.api.authenticator.store.TokenStore;
import com.zoho.api.logger.Logger;
import com.zoho.crm.api.HeaderMap;
import com.zoho.crm.api.Initializer;
import com.zoho.crm.api.SDKConfig;
import com.zoho.crm.api.UserSignature;
import com.zoho.crm.api.dc.DataCenter;
import com.zoho.crm.api.dc.USDataCenter;
import com.zoho.crm.api.exception.SDKException;
import com.zoho.crm.api.modules.*;
import com.zoho.crm.api.util.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class ZohoComponentConfig {
    @Autowired
    ApplicationContext ac;

    @Autowired
    ZohoContext zohoContext;


    boolean zcrmInitCompleted = false;
    @PostConstruct
    public void zcrmInitialization() throws Exception {
        log.info("Begin zCRM Initialization");
        if (zcrmInitCompleted) return;
        Logger logger = Logger.getInstance(Logger.Levels.INFO, zohoContext.getLogpath());
        UserSignature user = new UserSignature(zohoContext.getAuth().get("email"));
        DataCenter.Environment environment = USDataCenter.PRODUCTION;
        /*
         * Create a Token instance
         * 1 -> OAuth client id.
         * 2 -> OAuth client secret.
         * 3 -> REFRESH/GRANT token.
         * 4 -> Token type(REFRESH/GRANT).
         * 5 -> OAuth redirect URL.
         */
        TokenStore tokenstore = new FileStore(zohoContext.getAuth().get("tokenstore.path"));
        Token token;
        if (zohoContext.getAuth().containsKey("grant.token")) {
            //tokenstore.deleteTokens();
            log.info("Using provided grant token");
            token = new OAuthToken(
                    zohoContext.getAuth().get("client.id"),
                    zohoContext.getAuth().get("client.secret"),
                    zohoContext.getAuth().get("grant.token"),
                    OAuthToken.TokenType.GRANT);
        } else if (tokenstore.getTokens().size() > 0) {
            log.info("Using existing token in tokenstore ({} found)", tokenstore.getTokens().size());
            token = tokenstore.getTokens().get(0);
        } else if (zohoContext.getAuth().containsKey("refresh.token")) {
            log.info("Using provided refresh token");
            token = new OAuthToken(
                    zohoContext.getAuth().get("client.id"),
                    zohoContext.getAuth().get("client.secret"),
                    zohoContext.getAuth().get("refresh.token"),
                    OAuthToken.TokenType.REFRESH);
        } else {
            throw new Exception("initial grant or refresh oauth token not found");
        }

        SDKConfig sdkConfig = new SDKConfig.Builder()
                .setAutoRefreshFields(false).setPickListValidation(true).build();

        String resourcePath = zohoContext.getResourcepath();

        Initializer.initialize(
                user,
                environment,
                token,
                tokenstore,
                sdkConfig,
                resourcePath,
                logger);

        zcrmInitCompleted = true;
        log.info("End zCRM Initialization");

        // validation
        //validateZCRMConnect();
    }

    private void validateZCRMConnect() {
        try {
            ModulesOperations modulesOperations = new ModulesOperations();
            final APIResponse<ResponseHandler> modules = modulesOperations.getModules(new HeaderMap());
            if (modules.isExpected()) {
                log.info("Modules fetch status: {}", modules.getStatusCode());
                final ResponseHandler responseHandler = modules.getObject();
                if (responseHandler instanceof ResponseWrapper) {
                    ResponseWrapper wrapper = (ResponseWrapper)responseHandler;
                    for (Module module : wrapper.getModules()) {
                        log.info("Module: {}", module.getAPIName());
                    }
                } else if (responseHandler instanceof APIException) {
                    APIException exception = (APIException)responseHandler;
                    log.info("status: {}, code: {}, message: {}", exception.getStatus().getValue(), exception.getCode().getValue(), exception.getMessage().getValue());
                }

            }
        } catch (SDKException e) {
            log.error("sdk", e);
        }
    }

}

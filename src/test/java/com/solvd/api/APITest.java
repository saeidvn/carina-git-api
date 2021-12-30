package com.solvd.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.qaprosoft.carina.core.foundation.api.http.HttpResponseStatusType;
import com.solvd.api.repos.DeleteRepoMethod;
import com.solvd.api.repos.GetMyProfileMethod;
import com.solvd.api.repos.PatchRepoMethod;
import com.solvd.api.repos.PostRepoMethod;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class APITest implements IAbstractTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void verifyGetUserMethod() {
        LOGGER.info("Getting User Test");
        GetUserMethod getUserMethod = new GetUserMethod();
        getUserMethod.addProperty("login", "defunkt");
        getUserMethod.addProperty("name", "Chris Wanstrath");
        getUserMethod.callAPI();
        getUserMethod.validateResponse(JSONCompareMode.LENIENT);
//        getUserMethod.validateResponseAgainstSchema("api/users/rs.schema");
    }

    @Test
    public void verifyGetMyProfile() {
        LOGGER.info("Test Get My Profile.");
        GetMyProfileMethod getMyProfileMethod = new GetMyProfileMethod();
        getMyProfileMethod.addProperty("name", "Saeidvn");
        getMyProfileMethod.addProperty("id", 62662918);
        getMyProfileMethod.addProperty("login", "saeidvn");
        getMyProfileMethod.setHeaders(String.format("Authorization=%s", "token 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"));
        getMyProfileMethod.callAPI();
        getMyProfileMethod.validateResponse(JSONCompareMode.LENIENT);
//        getMyProfileMethod.validateResponseAgainstSchema("api/repos/_get/rs.schema");
    }

    @Test
    public void verifyRepoCreateTest() {
        LOGGER.info("Creating Test.");
        PostRepoMethod postRepoMethod = new PostRepoMethod();
        postRepoMethod.setHeaders(String.format("Authorization=%s", "token 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"));
        File file = new File("src/test/resources/api/repos/_post/rq.json");
        try {
            postRepoMethod.setBodyContent(Files.readFile(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        postRepoMethod.callAPI();
    }

    @Test
    public void verifyRepoPatchTest() {
        LOGGER.info("Patching Test.");
        PatchRepoMethod patchRepoMethod = new PatchRepoMethod();
        patchRepoMethod.addProperty("private", true);
        patchRepoMethod.setHeaders(String.format("Authorization=%s", "token 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"));
        patchRepoMethod.callAPI();
    }

    @Test
    public void verifyRepoDeleteTest() {
        LOGGER.info("Deleting Test.");
        DeleteRepoMethod deleteRepoMethod = new DeleteRepoMethod();
        deleteRepoMethod.expectResponseStatus(HttpResponseStatusType.NO_CONTENT_204);
        deleteRepoMethod.setHeaders(String.format("Authorization=%s", "token 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"));
        deleteRepoMethod.callAPI();

    }
}

package com.tg.qa.mytf.api.steps;

import com.tg.qa.mytf.api.MytfApiClient;
import com.tg.qa.mytf.core.MytfServiceConfig;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static com.tg.qa.mytf.api.MytfApiClient.MYTF_API_RETURN_KEY_HTTP_RESPONSE_CODE;
import static com.tg.qa.mytf.api.MytfApiClient.MYTF_API_RETURN_KEY_HTTP_RESPONSE_JSON;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ContextConfiguration(classes = {MytfServiceConfig.class})
public class SubmitBankDetailsVerifySteps {

    @Autowired
    private MytfApiClient mytfApiClient;

    private String paymentMethod;
    private String bankCountryCode;
    private String accountName;
    private String accountNumber;
    private String swiftCode;
    private String bsb;
    private String aba;

    private int httpResponseCode;
    private String httpResponseJson;

    private static Log stepsLogger = LogFactory.getLog(SubmitBankDetailsVerifySteps.class);

    @Given("^the customer has all required bank details as \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void the_customer_has_all_required_bank_details_as(String paymentMethod, String bankCountryCode
            , String accountName, String accountNumber, String swiftCode, String bsb, String aba) throws Throwable {
        this.paymentMethod = paymentMethod;
        this.bankCountryCode = bankCountryCode;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.swiftCode = swiftCode;
        this.bsb = bsb;
        this.aba = aba;
        stepsLogger.info("Received data set for.");
        stepsLogger.info("paymentMethod : "+this.paymentMethod);
        stepsLogger.info("bankCountryCode : "+this.bankCountryCode);
        stepsLogger.info("accountName : "+this.accountName);
        stepsLogger.info("accountNumber : "+this.accountNumber);
        stepsLogger.info("swiftCode : "+this.swiftCode);
        stepsLogger.info("bsb : "+this.bsb);
        stepsLogger.info("aba : "+this.aba);
    }

    @When("^customer submits the details$")
    public void customer_submits_the_details() throws Throwable {
        Map<String, String> httpResponse = mytfApiClient.submitBankDetails(paymentMethod, bankCountryCode, accountName
                , accountNumber, swiftCode, bsb, aba);
        httpResponseCode = Integer.parseInt(httpResponse.get(MYTF_API_RETURN_KEY_HTTP_RESPONSE_CODE));
        httpResponseJson = httpResponse.get(MYTF_API_RETURN_KEY_HTTP_RESPONSE_JSON);
    }

    @Then("^the api should return (\\d+) http code$")
    public void the_api_should_return_http_code(int expectedHttpResponseCode) throws Throwable {
        assertThat(httpResponseCode).isEqualTo(expectedHttpResponseCode);
    }

    @Then("^success message$")
    public void success_message() throws Throwable {
        String successMessage = extractSuccessMessage(httpResponseJson);
        assertThat(successMessage).isNotNull().contains("Bank details saved");
    }

    @Then("^error message must be equal to \"([^\"]*)\"$")
    public void error_message_must_be_equal_to(String expectedErrorMessage) throws Throwable {
        String actualErrorMessage = extractErrorMessage(httpResponseJson);
        stepsLogger.info("Expected: "+expectedErrorMessage+" and Actual: "+actualErrorMessage);
        assertThat(actualErrorMessage).isNotNull().contains(expectedErrorMessage);
    }

    private String extractSuccessMessage(String httpResponseJson){
        return extractJsonMessage(httpResponseJson, "success");
    }

    private String extractErrorMessage(String httpResponseJson){
        return extractJsonMessage(httpResponseJson, "error");
    }

    private String extractJsonMessage(String httpResponseJson, String key){
        JSONObject jsonObject = new JSONObject(httpResponseJson);
        String successMessage = null;
        try{
            Object successMsgObj = jsonObject.get(key);
            successMessage = successMsgObj.toString();
        }
        catch (JSONException e){
            stepsLogger.info("Key not found in JSON.");
        }
        return successMessage;
    }
}

package com.tg.qa.mytf.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.tg.qa.mytf.core.MytfSketch.MYTF_PROPERTY_API_HOST_NAME;

@Component
public class MytfApiClientImpl implements MytfApiClient {

    private static Log apiClientLogger = LogFactory.getLog(MytfApiClientImpl.class);

    @Override
    public Map<String, String> submitBankDetails(String paymentMethod, String bankCountryCode
            , String accountName, String accountNumber
            , String swiftCode, String bsb, String aba) throws Exception {

        //Building the HTTP POST request
        String submitBankDetailsUri = new URIBuilder()
                .setScheme("http")
                .setHost(MYTF_PROPERTY_API_HOST_NAME)
                .setPath(MYTF_API_PATH_SUBMIT_BANK_DETAILS)
                .toString();
        apiClientLogger.info("API End-point: "+submitBankDetailsUri);

        //Building the payload
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("payment_method", paymentMethod);
        jsonObject.put("bank_country_code", bankCountryCode);
        jsonObject.put("account_name", accountName);
        jsonObject.put("account_number", accountNumber);
        jsonObject.put("swift_code", swiftCode);
        jsonObject.put("bsb", bsb);
        jsonObject.put("aba", aba);
        String jsonString = jsonObject.toString();
        apiClientLogger.info("Payload: "+jsonString);

        //Sending request
        HttpPost httpPost = new HttpPost(submitBankDetailsUri);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        StringEntity stringEntity = new StringEntity(jsonString);
        httpPost.setEntity(stringEntity);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpPost);

        //Processing response
        Map<String, String> returnData = new HashMap();
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        returnData.put(MYTF_API_RETURN_KEY_HTTP_RESPONSE_CODE, Integer.toString(statusCode));
        apiClientLogger.info("Status code: "+statusCode);
        String httpResponseStr = httpResponse.getStatusLine().toString();
        returnData.put(MYTF_API_RETURN_KEY_HTTP_RESPONSE_MESSAGE, httpResponseStr);
        apiClientLogger.info("Response message: "+httpResponseStr);
        HttpEntity entity = httpResponse.getEntity();
        String strHttpEntity = EntityUtils.toString(entity);
        returnData.put(MYTF_API_RETURN_KEY_HTTP_RESPONSE_JSON, strHttpEntity);
        apiClientLogger.info("Response body: "+strHttpEntity);
        return returnData;
    }
}

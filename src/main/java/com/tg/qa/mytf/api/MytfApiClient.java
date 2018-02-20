package com.tg.qa.mytf.api;

import java.util.Map;

/**
 * The interface Mytf api client.
 */
public interface MytfApiClient {

    /**
     * The constant MYTF_API_PATH_SUBMIT_BANK_DETAILS.
     */
    public static final String MYTF_API_PATH_SUBMIT_BANK_DETAILS = "bank";

    /**
     * The constant MYTF_API_RETURN_KEY_HTTP_RESPONSE_CODE.
     */
    public static final String MYTF_API_RETURN_KEY_HTTP_RESPONSE_CODE = "HTTP_RESPONSE_CODE";
    /**
     * The constant MYTF_API_RETURN_KEY_HTTP_RESPONSE_MESSAGE.
     */
    public static final String MYTF_API_RETURN_KEY_HTTP_RESPONSE_MESSAGE = "HTTP_RESPONSE_MESSAGE";
    /**
     * The constant MYTF_API_RETURN_KEY_HTTP_RESPONSE_JSON.
     */
    public static final String MYTF_API_RETURN_KEY_HTTP_RESPONSE_JSON = "HTTP_RESPONSE_JSON";

    /**
     * Submit bank details map.
     *
     * @param paymentMethod   the payment method
     * @param bankCountryCode the bank country code
     * @param accountName     the account name
     * @param accountNumber   the account number
     * @param swiftCode       the swift code
     * @param bsb             the bsb
     * @param aba             the aba
     * @return the map
     * @throws Exception the exception
     */
    public Map<String, String> submitBankDetails(String paymentMethod, String bankCountryCode
            , String accountName, String accountNumber
            , String swiftCode, String bsb, String aba) throws Exception;
}

Feature: As a customer I should be able to register my bank details

  @positive_scenario
  Scenario Outline: Successful registration of customer bank details
    Given the customer has all required bank details as "<PaymentMethod>", "<BankCountryCode>", "<AccountName>", "<AccountNumber>", "<SwiftCode>", "<BSB>", "<ABA>"
    When customer submits the details
    Then the api should return 200 http code
    And success message
    Examples:
      | PaymentMethod | BankCountryCode | AccountName | AccountNumber | SwiftCode | BSB     | ABA       |
      | SWIFT         | US              | John Smith  | 123           | ICBCUSBJ  |         | 11122233A |
      | LOCAL         | CN              | Jack        | 12456789      |           |         |           |

  @negative_scenario
  Scenario Outline: Failure registration of customer bank details
    Given the customer has all required bank details as "<PaymentMethod>", "<BankCountryCode>", "<AccountName>", "<AccountNumber>", "<SwiftCode>", "<BSB>", "<ABA>"
    When customer submits the details
    Then the api should return 400 http code
    And error message must be equal to "<ExpectedErrorMessage>"
    Examples:
      | PaymentMethod | BankCountryCode | AccountName | AccountNumber | SwiftCode | BSB     | ABA       | ExpectedErrorMessage                                                                |
      | SWIFT         | US              | John Smith  |               | ICBCUSBJ  |         | 11122233A | 'account_number' is required                                                        |
      | SWIFT         | US              | John Smith  | 123           | ICBCCNBJ  |         |           | The swift code is not valid for the given bank country code: US                     |
      | SWIFT         | US              | John Smith  | 123           | ICBCCNB   |         |           | Length of 'swift_code' should be either 8 or 11                                     |
      | SWEET         | US              | John Smith  | 123           | ICBCCNB   |         |           | 'payment_method' field required, the value should be either 'LOCAL' or 'SWIFT       |
      | SWIFT         | BR              | John Smith  | 123           | ICBCCNBJ  |         |           | 'bank_country_code' is required, and should be one of 'US', 'AU', or 'CN'           |
      | SWIFT         | US              | K           | 123           | ICBCCNBJ  |         |           | Length of account_name should be between 2 and 10                                   |
      | SWIFT         | CN              | John Smith  | 123           | ICBCCNBJ  |         |           | Length of account_number should be between 8 and 20 when bank_country_code is 'CN'  |


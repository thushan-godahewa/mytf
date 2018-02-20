package com.tg.qa.mytf.api.test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true
        , features = "classpath:features/com/tg/qa/mytf/api/features/submit_bank_details_verify.feature"
        , strict = true
        , dryRun = false
        , format = {"pretty"
        , "html:target/cucumber-report"
        , "json:target/cucumber-report/cucumber.json"}
        , glue = {"com.tg.qa.mytf.api.steps"})
public class SubmitBankDetailsVerifyTest {
}

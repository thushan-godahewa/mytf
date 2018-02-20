# MYTF (A BDD Test Framework)
This is a sample project that demonstrates how to use Cucumber-JVM for automated regression testing.

The project comprises of an easily extensible framework plus a sample test. The main feature of this test framework is that the tests are completely independant from the framework layer and underlying tools. This is achieved by using Spring as the Dependancy Injection
framework and the clear test API provided by the framework. The main advantage of this design is that the framework implementation can be changed without having to change any tests.

Another key feature of this framework is the ability to externalize the properties required to access the target environment as well as any other parameters unique for that environment. For example, you can have individual property files to represent each test environment with required properties. Then when you want to run a test, you can simply specify the target environment by supplying the respective property file as a Java environment variable. See "framework setup" section for more details.

# Tech Stack (bottom to top)
1. Maven - build and dependancy management tool
2. JUnit - the core test framework
3. Spring - the DI (Dependancy Injection) tool
4. AssertJ - Assertion framework
5. Cucumber-JVM - Tool for BDD Style test implementation

# How to configure the framework ?

## Prerequisites:
1. Configure JDK 1.8 on your computer
2. Configure Maven 3.x on your computer

## Framework setup:
1. Clone the project
2. Add your own property file or just update an existing property file to suite your needs. If you add a new property file, just take a copy of an existing file and change the values. You can find existing samples on mytf\src\main\resources.
3. Mount the project on to your favourite IDE. You can use either Intellij or Eclipse. This step is only required if you want to edit the code or run the test using an IDE.

## Running tests:
1. Open command window
2. Navigate to the project root
3. Exceute the maven goal as:

mvn -Dmytf.env=sys-test-env clean test -Dtest=com.tg.qa.mytf.api.test.SubmitBankDetailsVerifyTest

Here, "mytf.env" is the environment variable used to specify the property file that contains the target environment details. Just give the file name and do not provide the .properties extension.

4. View the test report using mytf\target\cucumber-report\index.html

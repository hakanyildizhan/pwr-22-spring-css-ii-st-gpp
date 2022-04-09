### How to run the application

Before you run the application, make sure than Java 17 is set everywhere where possible. 
Also, you need to set JAVA_HOME environment variable:
`export JAVA_HOME=/path/to/your/jdk17`

Then, run the `./gradlew bootRun` command in the console and your application should start

Currently, DynamoDB is run locally. To run the DynamoDB locally, you need to have the docker installed.
Then simply run the `docker run -p 8000:8000 amazon/dynamodb-local`. You have to configure AWS locally. 
To do that, run `aws configure`. Specify keys according to the `application.yml`. Set the region to `eu-west-1`
and the last thing as `json`.
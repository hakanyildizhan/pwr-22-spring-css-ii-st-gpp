## Backend Layer


### Install Java

Before you run the application, make sure than Java 17 is set everywhere where possible. 
Also, you need to set JAVA_HOME environment variable:
`export JAVA_HOME=/path/to/your/jdk17`


### Install AWS SDK

First, install AWS SDK and run `aws configure`.

For the local environment,
- Set `aws_access_key_id` as `key`
- Set `aws_secret_access_key` as `key2`
- Set the `region` to `eu-central-1`
- Set the `output` as `json`

For production environment, set the access key and secret to the IAM user which has access to relevant DynamoDB and S3 resources & privileges (read/write). Also, set the region to your preference.


### Install Docker

Next, make sure you have Docker installed.


### Run DynamoDB

To run the DynamoDB locally, either
- Run `docker run -p 8000:8000 amazon/dynamodb-local` or
- From this directory, simply run `docker-compuse-direct up` which will get the DynamoDB up and running.


### Create an S3 Bucket 

For the local environment, run this command:
- `docker run --rm -it -p 4566:4566 -p 4571:4571 localstack/localstack`.

The app will create the necessary bucket itself, but to create it yourself, run
- `aws s3 mb s3://drive --endpoint-url=https://localhost.localstack.cloud:4566`

Refer to AWS SDK docs for all available commands.

For production environment, simply create an S3 bucket via AWS dashboard and note the bucket name you chose.


### Finally, running the app

From this directory,
- For the local environment, run `./gradlew bootRun`. `\src\main\resources\application-dev.yml` file already contains the necessary DynamoDB and S3 endpoints.
- For production, create a `application-prod.yml` in the same folder as the file above. Set the endpoints (they will probably look like `http://s3.eu-central-1.amazonaws.com/` and `http://dynamodb.eu-central-1.amazonaws.com/`), set the bucket name as you have created it on AWS Dashboard earlier, set the access key and secret of the IAM user with necessary privileges, and finally run `gradlew bootRun --args='--spring.profiles.active=prod`
### How to run the application

Before you run the application, make sure than Java 17 is set everywhere where possible. 
Also, you need to set JAVA_HOME environment variable:
`export JAVA_HOME=/path/to/your/jdk17`

Then, run the `./gradlew bootRun` command in the console and your application should start

Currently, DynamoDB is run locally. To run the DynamoDB locally, you need to have the docker installed.
Then simply run the `docker run -p 8000:8000 amazon/dynamodb-local`. You have to configure AWS locally. 
To do that, run `aws configure`. Specify keys according to the `application.yml`. Set the region to `eu-west-1`
and the last thing as `json`.

### Running S3 on Localstack
Run Localstack: `docker run --rm -it -p 4566:4566 -p 4571:4571 localstack/localstack
`.
 
In order to create the bucket, run `awslocal s3api create-bucket --bucket <bucket-name> --region eu-west-1 --create-bucket-configuration LocationConstraint=eu-west-1`.
As a response, you get the bucket's URL: `http://<bucket-name>.s3.localhost.localstack.cloud:4566/`

To list the buckets, run `awslocal s3api list-buckets`

Go to `http://s3.localhost.localstack.cloud:4566/sample` and check if file lands on local s3 bucket named `sample` once you invoke `GET http://localhost:8080/files endpoint`
The endpoint still needs polishing, for now it's just a spike
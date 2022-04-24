FROM openjdk:17-alpine

WORKDIR /app
RUN apk update && apk add aws-cli
RUN aws configure set default.region eu-west-1
RUN aws configure set aws_access_key_id key
RUN aws configure set aws_secret_access_key key2

COPY build/libs/drive-*SNAPSHOT.jar /app/application.jar

CMD ["java", "-jar", "/app/application.jar"]
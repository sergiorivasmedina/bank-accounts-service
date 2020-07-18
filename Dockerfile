FROM openjdk:8-alpine
MAINTAINER srivasme@everis.com

COPY ./target ./app
EXPOSE 8081
WORKDIR ./app/
ENTRYPOINT ["java", "-jar", "bank-accounts-0.0.1-SNAPSHOT.jar"]
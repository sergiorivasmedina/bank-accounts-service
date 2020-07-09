FROM maven:latest
MAINTAINER srivasme@everis.com

COPY ./target/ ./app
EXPOSE 8080
WORKDIR ./app/
ENTRYPOINT ["java", "-jar", "bank-accounts-0.0.1-SNAPSHOT.jar"]
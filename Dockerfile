# DISCLAIMER: this Dockerfile is just for testing purposes, NEVER use in a PRODUCTION environment
FROM openjdk:8-jre-alpine

ADD libs/identity-integration-service.jar .
COPY identity-integration-service.yml .
EXPOSE 9091
ENTRYPOINT ["java", \
            "-Dspring.config.location=identity-integration-service.yml", \
            "-jar", \
            "identity-integration-service.jar"]
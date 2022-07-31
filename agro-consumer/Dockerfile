FROM amazoncorretto:11-alpine-jdk
MAINTAINER baeldung.com
COPY target/agro-consumer-0.0.1-SNAPSHOT.jar agro-consumer-0.0.1-SNAPSHOT.jar
ENV EMAIL_PASSWORD={passwod}
ENV EMAIL_USER={user}
ENTRYPOINT ["java","-jar","/agro-consumer-0.0.1-SNAPSHOT.jar"]
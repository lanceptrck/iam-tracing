FROM adoptopenjdk/openjdk8:jdk8u292-b10
MAINTAINER cict.com
COPY target/iam-tracing-0.0.1-SNAPSHOT.jar iam-tracing-0.0.1.jar
EXPOSE 8558
ENTRYPOINT ["java","-jar","/iam-tracing-0.0.1.jar"]
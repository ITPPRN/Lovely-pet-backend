From openjdk:19-alpine
COPY target/Lovelypet-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/Lovelypet-0.0.1-SNAPSHOT.jar"]
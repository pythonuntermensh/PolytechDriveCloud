FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY RestMVC/target/RestMVC-0.0.1-SNAPSHOT.jar app.jar
#CMD sleep 5 && java -jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
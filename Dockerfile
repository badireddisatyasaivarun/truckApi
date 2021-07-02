FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 9090
VOLUME /tmp
ADD target/TruckApi-0.0.1-SNAPSHOT.jar  truck.jar
ENTRYPOINT ["java","-jar","truck.jar"]

FROM openjdk:17
EXPOSE 8080
ADD target/music-streaming-custom-api.jar music-streaming-custom-api.jar
ENTRYPOINT ["java", "-jar", "/music-streaming-custom-api.jar"]
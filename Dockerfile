FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

COPY ./target/forum-service-0.0.1-SNAPSHOT.jar ./forum-service.jar

ENV MONGODB_URI=mongodb+srv://ivan:54321.com@cluster0.xjuef0h.mongodb.net/forum?retryWrites=true&w=majority

CMD ["java", "-jar", "/app/forum-service.jar"]
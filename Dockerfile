FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /usr/src/app

COPY . .

RUN dos2unix ./gradlew

RUN chmod +x ./gradlew
RUN ./gradlew build

# App
FROM eclipse-temurin:17-jre-alpine
# ARG TARGET

WORKDIR /app

COPY --from=builder /usr/src/app/build/libs/*.jar .


# ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","/app/app.jar"]

CMD java -jar ./${TARGET}.jar --spring.config.location=file:///app/application-prod.yml

# Use the official Gradle image as the base image
FROM gradle:8.0.0-jdk17 AS build

# Copy the source code into the container
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Build the application with Gradle
RUN gradle build --no-daemon

# Use the official OpenJDK image as the base image for the final image
FROM amazoncorretto:17.0.8-alpine3.18

# Copy the built JAR file from the previous stage
COPY --from=build /home/gradle/src/build/libs/*.jar /app.jar

# Expose the port that your application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","/app.jar"]

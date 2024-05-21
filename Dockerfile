# Use the official OpenJDK 17 image as a base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the executable jar file to the working directory
COPY target/AmelaManagement-0.0.1-SNAPSHOT.jar /app/AmelaManagement-0.0.1-SNAPSHOT.jar

# Expose the port that the application will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/AmelaManagement-0.0.1-SNAPSHOT.jar"]
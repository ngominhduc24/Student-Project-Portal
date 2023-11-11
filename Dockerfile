FROM maven:3.8.4-openjdk-17-slim AS builder

# Copy the pom.xml and project files to the container.
COPY pom.xml .
COPY src ./src

# Build the application using Maven.
RUN mvn clean package

# Copy the built JAR file to the root directory of the container.
RUN echo $(ls)
COPY target/*-SNAPSHOT.jar app.jar
# Production stage
FROM eclipse-temurin:17-jdk-alpine AS runner

# Copy the JAR file from the build stage to the container.
COPY --from=builder /app.jar .

# Set the entrypoint to run the JAR file.
ENTRYPOINT ["java", "-jar", "app.jar"]
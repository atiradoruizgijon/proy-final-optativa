# Stage 1: Build the application
# Uso una imagen base con JDK para compilar la aplicación
FROM eclipse-temurin:21-jdk AS builder

# Set the working directory
# Elijo un directorio de trabajo dentro del contenedor
WORKDIR /app

# Copy the application code
# Con este COPY, se copian todos los archivos del
# directorio actual (donde está el Dockerfile) al directorio de trabajo en el contenedor.
# Esto incluye el código fuente, archivos de configuración, y cualquier otro recurso necesario para construir la aplicación.
COPY . .

# Given permissions to mvnw
RUN chmod +x mvnw

# Build the application (requires Maven or Gradle)
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application
# Uso el JRE para ejecutar la aplicación, el anterior era el JDK para compilarla
FROM eclipse-temurin:21-jre

# Set the working directory
# Elijo un directorio de trabajo dentro del contenedor para ejecutar la aplicación.
WORKDIR /app

# Copy the JAR file from the builder stage
# Aquí se copia el archivo JAR generado en la etapa de construcción al nuevo contenedor que se usará para ejecutar la aplicación.
COPY --from=builder /app/target/*.jar app.jar

# Expose the port the app will run on
EXPOSE 8080

# Command to run the application
# Finalmente, se define el comando de entrada para ejecutar la aplicación Java.
# Este comando se ejecutará cuando el contenedor se inicie, y lanzará la aplicación utilizando el archivo JAR copiado.
ENTRYPOINT ["java", "-jar", "app.jar"]
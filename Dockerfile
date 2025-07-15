# Usar OpenJDK 21 con Alpine para menor tamaño
FROM openjdk:21-jdk-slim

# Establecer directorio de trabajo
WORKDIR /app

# Copiar Maven wrapper y archivos de configuración
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Hacer ejecutable el Maven wrapper
RUN chmod +x ./mvnw

# Descargar dependencias (aprovecha Docker layer caching)
RUN ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY src src

# Construir la aplicación
RUN ./mvnw clean package -DskipTests

# Exponer puerto
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/PedidosPoseidon-0.0.1-SNAPSHOT.jar"]
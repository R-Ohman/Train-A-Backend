FROM eclipse-temurin:17.0.6_10-jre

LABEL org.opencontainers.image.title="train-gateway"
LABEL org.opencontainers.image.authors="Ruslan (R-Ohman) Rabadanov"
LABEL org.opencontainers.image.source="https://github.com/R-Ohman/Train-A-Backend"
LABEL org.opencontainers.image.url="https://github.com/R-Ohman/Train-A-Backend"
LABEL org.opencontainers.image.vendor="Gdańsk University of Technology"
LABEL org.opencontainers.image.version="1.0.0-SNAPSHOT"
LABEL org.opencontainers.image.description="Train A app, gateway module."
LABEL org.opencontainers.image.licenses="MIT"

LABEL build_version=""
LABEL maintainer=""

ENV VERSION="1.0.0-SNAPSHOT"

ENV SERVER_PORT=8080

ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:train-gateway
ENV SPRING_DATASOURCE_DRIVERCLASSNAME=org.h2.Driver
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_DATASOURCE_PASSWORD=password

ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.H2Dialect
ENV SPRING_JPA_GENERATE_DDL=true
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update

ENV SPRING_H2_CONSOLE_enabled=true

ENV TRAIN_GATEWAY_HOST=localhost:8080
ENV TRAIN_USER_URL=http://localhost:8081
ENV TRAIN_RAILWAY_URL=http://localhost:8082

EXPOSE 8080

COPY target/train-gateway-${VERSION}.jar /opt/train-gateway/train-gateway.jar

CMD ["java", "-jar", "/opt/train-gateway/train-gateway.jar"]

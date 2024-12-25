FROM eclipse-temurin:17.0.6_10-jre

LABEL org.opencontainers.image.title="train-user"
LABEL org.opencontainers.image.authors="Ruslan (R-Ohman) Rabadanov"
LABEL org.opencontainers.image.source="https://github.com/R-Ohman/Train-A-Backend"
LABEL org.opencontainers.image.url="https://github.com/R-Ohman/Train-A-Backend"
LABEL org.opencontainers.image.vendor="Gdańsk University of Technology"
LABEL org.opencontainers.image.version="1.0.0-SNAPSHOT"
LABEL org.opencontainers.image.description="Train A app, user module."
LABEL org.opencontainers.image.licenses="MIT"

LABEL build_version=""
LABEL maintainer=""

ENV VERSION="1.0.0-SNAPSHOT"

ENV SERVER_PORT=8080

ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:train-user
ENV SPRING_DATASOURCE_DRIVERCLASSNAME=org.h2.Driver
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_DATASOURCE_PASSWORD=password

ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.H2Dialect
ENV SPRING_JPA_GENERATE_DDL=true
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update

ENV SPRING_H2_CONSOLE_enabled=true

ENV TRAIN_RAILWAY_URL=http://localhost:8082

EXPOSE 8080

COPY target/train-user-${VERSION}.jar /opt/train-user/train-user.jar

CMD ["java", "-jar", "/opt/train-user/train-user.jar"]

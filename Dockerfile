# Dockerfile

# 使用官方的 OpenJDK 21 作为基础镜像
FROM openjdk:17-slim

# 将 JAR 文件复制到容器中
COPY target/demo-0.0.1-SNAPSHOT.jar /app.jar

# 运行 Spring Boot 应用
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 允许配置端口
EXPOSE 8081

# 使用官方的 Java 运行时作为基础镜像
FROM openjdk:21-jdk-slim

# 设置工作目录
WORKDIR /app

# 将 JAR 文件添加到容器中
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# 启动 Spring Boot 应用
ENTRYPOINT ["java", "-jar", "app.jar"]
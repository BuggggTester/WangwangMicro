# 使用 Maven 的官方镜像作为构建环境
FROM openjdk:17-slim

COPY  target/demo-0.0.1-SNAPSHOT.jar /food-system.jar
# 运行 JAR 文件
ENTRYPOINT ["java", "-jar", "/app/food-system.jar"]


# 暴露应用程序端口
EXPOSE 8080

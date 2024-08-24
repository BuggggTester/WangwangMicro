# 使用 Maven 的官方镜像作为构建环境
FROM openjdk:21-slim

# 设置工作目录
# WORKDIR /app

# 将项目文件复制到容器中
# COPY src /app/src
# COPY pom.xml /app

# 构建项目
# RUN mvn clean package

# 设置工作目录
# WORKDIR /app

# 从构建阶段复制构建好的 JAR 文件到运行环境
# COPY --from=build /app/target/food-system.jar /app/food-system.jar
COPY  target/demo-0.0.1-SNAPSHOT.jar /food-system.jar
# 运行 JAR 文件
ENTRYPOINT ["java", "-jar", "/app/food-system.jar"]


# 暴露应用程序端口
EXPOSE 8080

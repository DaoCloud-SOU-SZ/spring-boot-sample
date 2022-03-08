all:
    BUILD --platform=linux/amd64 --platform=linux/arm64 +docker

docker:
    FROM 192.168.50.19/library/openjdk:8u322-ol7-ef

    COPY target/spring-boot-demo*.jar spring-boot-demo.jar

    SAVE IMAGE --push --insecure 192.168.50.19/demo/spring-boot-demo:0.0.1-SNAPSHOT

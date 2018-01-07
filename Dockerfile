# for build
FROM openjdk:8 AS build-env

# install Node.js
RUN curl -sL https://deb.nodesource.com/setup_8.x | bash - && \
    apt-get install -y nodejs

# install yarn
RUN npm install -g yarn

# compile app
RUN mkdir /root/tsujun
COPY . /root/tsujun
WORKDIR /root/tsujun
RUN rm -rf src/main/resources/static/node_modules
RUN ./yarnInstall.sh
RUN ./gradlew clean build



# for runtime
FROM openjdk:8

COPY --from=build-env /root/tsujun/build/libs/tsujun-*.jar /root/tsujun.jar

EXPOSE 8080

CMD ["java", "-jar", "/root/tsujun.jar"]

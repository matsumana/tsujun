FROM openjdk:9.0.1

# cacerts from JDK 8u152 to workaround http://bugs.java.com/view_bug.do?bug_id=8189357
#
# see also:
# https://github.com/docker-library/openjdk/issues/145
# https://github.com/keeganwitt/docker-gradle/blob/1d0a9b199274b66cbb247279bb50ceaacdfb2e31/jdk9/Dockerfile
COPY cacerts /etc/ssl/certs/java/cacerts

# install Node.js
RUN curl -sL https://deb.nodesource.com/setup_8.x | bash - && \
    apt-get install -y nodejs

# install yarn
RUN npm install -g yarn

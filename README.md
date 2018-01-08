# Tsūjun

[![CircleCI](https://circleci.com/gh/matsumana/tsujun.svg?style=shield)](https://circleci.com/gh/matsumana/tsujun)

Tsūjun is yet another Web UI for [KSQL](https://github.com/confluentinc/ksql).

![](https://i.gyazo.com/37254dd6d69b6199e6436e4017dfd9c8.png)

# Supporting KSQL syntax

- SELECT
- (LIST | SHOW) QUERIES
- (LIST | SHOW) STREAMS
- (LIST | SHOW) TABLES

__*Other syntax will be supported in future version__

# Tested browsers

- Safari
- Chrome
- Firefox

__Caution__

This application is using [Fetch API](https://caniuse.com/#feat=fetch) and Fetch API's Readable streams.  
But in Firefox, this feature disabled by default.  
It can be enabled in `about:config`.

ref:  
[Fetch API - Browser compatibility](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API#Browser_compatibility)

# How to setup local dev environment

- Install Node.js
- Install yarn

```
$ npm install -g yarn
```

- Install dependent javascript libraries

```
$ yarnInstall.sh
```

# How to launch on local

Specify the your KSQL server with the environment variable `KSQL_SERVER`

If the environment variable `KSQL_SERVER` is not set, it will connect to `http://localhost:8080`

## launch with Gradle

```
$ KSQL_SERVER=http://your_ksql_server ./gradlew bootRun
```

## launch with IntelliJ

![](https://i.gyazo.com/cb65ef7cf4964d6dcb3e34f3ec4d400f.jpg)

## build javascript sources

After launch the application, you must build javascript sources with an another terminal.

Since output directories are different between Gradle and IntelliJ, please use the following scripts.

### for Gradle

output directory is `build/resources/main/static/javascripts`

```
$ ./yarnWatchGradle.sh
```

### for IntelliJ

output directory is `out/production/resources/static/javascripts`

```
$ ./yarnWatchIntelliJ.sh
```

# How to build for production

```
$ ./gradlew clean build
```

# How to launch on production

```
$ KSQL_SERVER=http://your_ksql_server java -jar /path/to/tsujun-0.0.1.jar
```

# How to launch with Docker

```
$ docker run -p 8080:8080 -e KSQL_SERVER=http://your_ksql_server matsumana/tsujun:0.0.1
```

# Appendix

Q: What is Tsūjun's the origin of the name?  
A: [Tsūjun Bridge](https://en.wikipedia.org/wiki/Ts%C5%ABjun_Bridge)

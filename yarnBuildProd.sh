#!/bin/bash

PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:$PATH

cd src/main/resources/static/javascripts

yarn build:prod

cd ../../../../../build/resources/main/static

rm -rf node_modules
rm -rf *.*

mv ./javascripts/bundle.js .
rm -rf ./javascripts/*
mv ./bundle.js ./javascripts

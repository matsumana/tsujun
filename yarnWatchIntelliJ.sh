#!/bin/bash

cd src/main/resources/static/javascripts

export JS_OUTPUT_PATH=../../../../out/production/resources/static/javascripts

yarn watch

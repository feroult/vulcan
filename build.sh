#!/bin/bash

(cd refactor && mvn clean install -Dmaven.test.skip=true)

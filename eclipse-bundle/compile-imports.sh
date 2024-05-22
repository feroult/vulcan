#!/bin/bash

while IFS=':' read -r groupId artifactId jar version; do
  echo "        <dependency>
            <groupId>$groupId</groupId>
            <artifactId>$artifactId</artifactId>
            <version>$version</version>
            <scope>compile</scope>
        </dependency>"
done

#!/bin/bash

# https://download.eclipse.org/releases/2019-12/201912181000/plugins/
# https://stackoverflow.com/questions/6682028/use-dependencies-from-eclipse-p2-repository-in-a-regular-maven-build
# https://github.com/mrcalvin/qvto-cli
# https://stackoverflow.com/questions/6682028/use-dependencies-from-eclipse-p2-repository-in-a-regular-maven-build

split_filename() {
  local filename="$1"
  artifactId="${filename%_*}"
  version="${filename##*_}"
  version="${version%%.v*}"
}

is_deployed() {
  local input_string="$1"

  local part1=$(echo "$input_string" | awk -F ':' '{print $1}')
  local part2=$(echo "$input_string" | awk -F ':' '{print $2}')

  local part1_with_slash=$(echo "$part1" | tr '.' '/')

  local output_string="$HOME/.m2/repository/$part1_with_slash/$part1/$part2/$part1-$part2.pom"

  if [ -f "$output_string" ]; then
    return 0  # Success (file exists)
  else
    return 1  # Failure (file does not exist)
  fi
}

install_lib() {
  local filename="$1"
  split_filename $filename
  artifact="$artifactId:$version-custom"
  if ! is_deployed $artifact; then
    mvn install:install-file \
       -Dfile=./$filename \
       -DgroupId=$artifactId \
       -DartifactId=$artifactId \
       -Dversion=$version-custom \
       -Dpackaging=jar \
       -DgeneratePom=true
  fi
}

print_deps() {
  local filename="$1"
  split_filename $filename
  echo "        <dependency>
            <groupId>$artifactId</groupId>
            <artifactId>$artifactId</artifactId>
            <version>$version-custom</version>
            <scope>compile</scope>
        </dependency>"
}

install_all() {
  for filename in `ls *.jar`; do
    install_lib $filename
  done
  for filename in `ls *.jar`; do
    print_deps $filename
  done
}

(cd target/repository/plugins && install_all)
#!/bin/bash

WORKSPACE="../sample-workspace"
DEBUG="n"

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case "$1" in
        -w|--workspace)
            WORKSPACE="$2"
            shift 2
            ;;
        -d|--debug)
            DEBUG="$2"
            shift 2
            ;;
        *)
            echo "Usage: $0 [-w|--workspace <workspace_path>] [-d|--debug <y/n>]"
            exit 1
            ;;
    esac
done

# Set DEBUG_CMD based on the DEBUG value
if [ "$DEBUG" == "y" ]; then
    DEBUG_CMD="-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=7001"
else
    DEBUG_CMD=""
fi

VULCAN_RUN=y java -Dosgi.instance.area="$WORKSPACE" $DEBUG_CMD -jar refactor/target/application.jar

rm -rf $WORKSPACE/.metadata


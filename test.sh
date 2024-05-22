#!/bin/bash

WORKSPACE="/tmp/vulcan-test-workspace"

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case "$1" in
        -w|--workspace)
            WORKSPACE="$2"
            shift 2
            ;;
        *)
            echo "Usage: $0 [-w|--workspace <workspace_path>]"
            exit 1
            ;;
    esac
done

(cd refactor && mvn clean install -Dosgi.instance.area="$WORKSPACE")


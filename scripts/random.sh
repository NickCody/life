#!/bin/bash

SCRIPT_DIR="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

time java -XX:TieredStopAtLevel=1 -cp $SCRIPT_DIR/../target/life-1.0-SNAPSHOT.jar com.cody.life.immutable.Random ${1:-25}

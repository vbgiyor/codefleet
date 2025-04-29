#!/bin/bash
MODULE=$1
if [ -z "$MODULE" ]; then
  echo "Usage: $0 <module-name>"
  exit 1
fi
echo "Deploying module: $MODULE"
bash modules/$MODULE/deploy/deploy.sh
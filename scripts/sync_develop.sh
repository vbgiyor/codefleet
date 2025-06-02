#!/bin/bash

# Exit on error
set -e

# Ensure we're in the correct repository
echo "Synchronizing develop with main..."

# Switch to main and pull latest
git checkout main
git pull origin main

# Switch to develop and pull latest
git checkout develop
git pull origin develop

# Merge main into develop
git merge main

# Push updated develop
git push origin develop

echo "Sync completed successfully!"
#!/bin/bash
set -e

LOG_FILE="rundjango_detailed.log"
echo "Logging output to $LOG_FILE"

echo "Stopping and removing containers and volumes..." | tee -a "$LOG_FILE"
docker-compose down --volumes >> "$LOG_FILE" 2>&1

echo "Rebuilding and starting services..." | tee -a "$LOG_FILE"
docker-compose up --build -d >> "$LOG_FILE" 2>&1

echo "Waiting for database to be healthy..." | tee -a "$LOG_FILE"
for i in {1..30}; do
  if docker-compose ps | grep db | grep "Up.*(healthy)"; then
    echo "Database is healthy" | tee -a "$LOG_FILE"
    break
  fi
  echo "Waiting for database to be healthy..." | tee -a "$LOG_FILE"
  sleep 2
done

echo "Cleaning stray bookstore modules in site-packages..." | tee -a "$LOG_FILE"
docker-compose exec -T web sh -c "find /usr/local/lib/python3.9/site-packages -name 'bookstore*' -type f -o -type d | tee /tmp/stray_bookstore.txt && if [ -s /tmp/stray_bookstore.txt ]; then cat /tmp/stray_bookstore.txt | xargs -I {} rm -rf '{}'; echo 'Removed stray bookstore modules from site-packages'; else echo 'No bookstore modules in site-packages'; fi" >> "$LOG_FILE" 2>&1

echo "Verifying site-packages after cleanup..." | tee -a "$LOG_FILE"
docker-compose exec -T web sh -c "find /usr/local/lib/python3.9/site-packages -name 'bookstore*' -type f -o -type d || echo 'No bookstore modules found in site-packages'" >> "$LOG_FILE" 2>&1

echo "Debugging installed packages..." | tee -a "$LOG_FILE"
docker-compose exec -T web sh -c "pip list | grep -i bookstore || echo 'No bookstore-related packages found'" >> "$LOG_FILE" 2>&1

echo "Generating migrations for bookstore app..." | tee -a "$LOG_FILE"
docker-compose exec -T web python apps/bookslibrary/manage.py makemigrations bookstore --settings=core.settings.base >> "$LOG_FILE" 2>&1

echo "Applying migrations..." | tee -a "$LOG_FILE"
docker-compose exec -T web python apps/bookslibrary/manage.py migrate --settings=core.settings.base >> "$LOG_FILE" 2>&1

echo "Running tests..." | tee -a "$LOG_FILE"
docker-compose exec -T web pytest apps/bookslibrary/bookstore/tests.py --ds=core.settings.base --showlocals >> "$LOG_FILE" 2>&1

echo "Django setup and tests complete!" | tee -a "$LOG_FILE"
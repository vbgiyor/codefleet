#!/bin/bash
PROJECT_DIR="./"
if [ ! -d "$PROJECT_DIR" ]; then
  echo "Error: Project directory '$PROJECT_DIR' not found."
  exit 1
fi

echo "Project structure before cleanup:"
find "$PROJECT_DIR" -type d -o -type f -not -path "*venv*" | sort
echo "Python path:"
python -c "import sys; print('\n'.join(sys.path))"
echo "Debugging bookstore module resolution:"
python -c "
try:
    import bookstore
    print('Found bookstore module at:', bookstore.__file__)
except ImportError:
    print('No bookstore module found')
"

echo "Checking for stray bookstore modules..."
find "$PROJECT_DIR" -name "bookstore" -type d -not -path "*apps/bookslibrary/bookstore*" -not -path "*venv*" -o -name "bookstore.py" -not -path "*venv*" | tee stray_bookstore.txt
if [ -s stray_bookstore.txt ]; then
  echo "Found stray bookstore modules, removing..."
  while IFS= read -r item; do
    rm -rf "$item"
    echo "Removed: $item"
  done < stray_bookstore.txt
else
  echo "No stray bookstore modules found"
fi
rm -f stray_bookstore.txt

echo "Removing __pycache__ directories..."
find "$PROJECT_DIR" -name "__pycache__" -type d -not -path "*venv*" -exec rm -rf {} +
echo "Project structure after cleanup:"
find "$PROJECT_DIR" -type d -o -type f -not -path "*venv*" | sort

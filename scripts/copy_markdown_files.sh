#!/bin/bash
set -e

SRC="website/backend/static/markdown/md_to_html"
DEST="website/frontend/public/static/md_to_html"

cd ..
echo "📁 Copying from $SRC → $DEST"
mkdir -p "$DEST"
cp -r "$SRC/"* "$DEST/"

echo "✅ Copy complete."

#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
TARGET="$ROOT/app/src/main/cpp/mmapi_tsf/TinySoundFont"
if [ -f "$TARGET/tsf.h" ]; then
  echo "TinySoundFont já está presente em: $TARGET"
  exit 0
fi
mkdir -p "$(dirname "$TARGET")"
git clone --depth 1 https://github.com/schellingb/TinySoundFont.git "$TARGET"
test -s "$TARGET/tsf.h"
echo "TinySoundFont restaurado. Preserve o arquivo LICENSE do projeto."

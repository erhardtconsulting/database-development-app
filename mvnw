#!/usr/bin/env sh
set -eu

MAVEN_VERSION="3.9.11"
BASE_DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
MAVEN_HOME="$BASE_DIR/.mvn/apache-maven-$MAVEN_VERSION"
MAVEN_BIN="$MAVEN_HOME/bin/mvn"

if [ ! -x "$MAVEN_BIN" ]; then
  DIST_DIR="$BASE_DIR/.mvn/wrapper/dists"
  ARCHIVE="$DIST_DIR/apache-maven-$MAVEN_VERSION-bin.tar.gz"
  URL="https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz"

  mkdir -p "$DIST_DIR"
  if [ ! -f "$ARCHIVE" ]; then
    if command -v curl >/dev/null 2>&1; then
      curl -fsSL "$URL" -o "$ARCHIVE"
    elif command -v wget >/dev/null 2>&1; then
      wget -q "$URL" -O "$ARCHIVE"
    else
      echo "curl or wget is required to download Maven $MAVEN_VERSION." >&2
      exit 1
    fi
  fi

  tar -xzf "$ARCHIVE" -C "$BASE_DIR/.mvn"
fi

exec "$MAVEN_BIN" "$@"


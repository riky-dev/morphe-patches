#!/usr/bin/env bash
# run_env.sh - Runs any command with workspace tools and JDK in PATH
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"

export PATH="$ROOT_DIR/tools/bin:$ROOT_DIR/tools/jadx/bin:$ROOT_DIR/tools/apktool:$PATH"
export JAVA_HOME="$ROOT_DIR/tools/jdk-17"

exec "$@"

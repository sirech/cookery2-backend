#!/bin/bash

set -e
set -o nounset
set -o pipefail

SCRIPT_DIR=$(cd "$(dirname "$0")" ; pwd -P)

# shellcheck source=./go.helpers
source "${SCRIPT_DIR}/go.helpers"

goal_build() {
  ./gradlew build -x test -x detekt
}

goal_containerize() {
  docker build . -t cookery2-backend
}

goal_run() {
  ./gradlew bootRun
}

goal_outdated() {
  ./gradlew dependencyUpdates
}

goal_linter-kt() {
  ./gradlew detekt
}

goal_test-unit() {
  ./gradlew clean test
}

goal_test-container() {
  bundle install
  bundle exec rubocop
  bundle exec rspec spec
}

goal_help() {
  echo "usage: $0 <goal>

    goal:

    build                    -- Build the deployable artifacts
    containerize             -- Build the docker container for the app

    run                      -- Start the backend application

    outdated                 -- Check which dependencies are outdated

    linter-kt                -- Run the linter for kotlin files

    test-unit                -- Run unit tests
    test-container           -- Run container tests
    test-e2e                 -- Run newman tests
    "
  exit 1
}

main() {
  TARGET=${1:-}
  if [ -n "${TARGET}" ] && type -t "goal_$TARGET" &>/dev/null; then
    "goal_$TARGET" "${@:2}"
  else
    goal_help
  fi
}

main "$@"

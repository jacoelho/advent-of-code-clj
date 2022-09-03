# See here for image contents: https://github.com/microsoft/vscode-dev-containers/tree/v0.194.0/containers/java/.devcontainer/base.Dockerfile

# [Choice] Java version: 11, 16
ARG VARIANT="16"
FROM mcr.microsoft.com/vscode/devcontainers/java:0-${VARIANT}

ARG CLOJURE_VERSION="1.11.1.1155"
ARG CLOJURE_URL="https://download.clojure.org/install/linux-install-${CLOJURE_VERSION}.sh"
ARG CLOJURE_PATH="/usr/bin/clojure"
ARG CLOJURE_INSTALLER="/tmp/clojure"

RUN apt-get update \
  && DEBIAN_FRONTEND=noninteractive apt-get -y install --no-install-recommends rlwrap \
  && curl -Lo ${CLOJURE_INSTALLER} ${CLOJURE_URL} \
  && chmod +x ${CLOJURE_INSTALLER} \
  && ${CLOJURE_INSTALLER} --prefix ${CLOJURE_PATH} \
  && rm ${CLOJURE_INSTALLER} \
  && apt-get autoremove -y \
  && apt-get clean -y \
  && rm -rf /var/lib/apt/lists/*
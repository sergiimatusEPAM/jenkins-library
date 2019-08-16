#!/usr/bin/env bash
set +o xtrace
set +o errexit

PROVIDER="${1}"
TF_MODULE_SOURCE="./.."
# change the module source here to real git source, otherwise dcos module seems to be not able to find itself
if [ ${TF_MODULE_NAME} == "dcos" ]; then
  TF_MODULE_SOURCE="git::${GIT_URL}?ref=${BRANCH_NAME}"
fi
# we overwrite here the source with the real content of the WORKSPACE as we can rebuild builds in that case
ls -lha "${TF_MODULE_SOURCE}"
cat <<EOF | tee Terraformfile
{
  "dcos-terraform/${TF_MODULE_NAME}/${PROVIDER}": {
    "source": "${TF_MODULE_SOURCE}"
  }
}
EOF

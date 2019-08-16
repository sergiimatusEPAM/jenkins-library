#!/usr/bin/env bash
set +o xtrace
set +o errexit

PROVIDER="${1}"
TF_MODULE_SOURCE="./.."
# we overwrite here the source with the real content of the WORKSPACE as we can rebuild builds in that case
cat <<EOF | tee Terraformfile
{
  "dcos-terraform/${TF_MODULE_NAME}/${PROVIDER}": {
    "source": "${TF_MODULE_SOURCE}"
  }
}
EOF

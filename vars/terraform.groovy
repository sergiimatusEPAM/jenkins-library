#!/usr/bin/env groovy
def call() {
  pipeline {
    agent none
    stages {
      stage('Run Tests') {
        parallel {
          stage('Terraform FMT') {
            agent {
              label 'terraform'
            }
            steps {
              sh """
                #!/usr/bin/env sh
                set +o xtrace
                set -o errexit

                terraform fmt --check --diff
              """
            }
          }
          stage('Terraform validate') {
            agent {
              label 'terraform'
            }
            steps {
              sh """
                #!/usr/bin/env sh
                set +o xtrace
                set -o errexit

                terraform init --upgrade
                terraform validate -check-variables=false
              """
            }
          }
          stage('Validate README go generated') {
            agent {
              label 'terraform'
            }
            steps {
              sh """
                #!/usr/bin/env sh
                set +o xtrace
                set -o errexit

                terraform-docs --sort-inputs-by-required md ./ > README.md
                git --no-pager diff --exit-code
              """
            }
          }
        }
      }
      stage('Download tfdescan tsv') {
        agent {
          label 'tfdescsan'
        }
        steps {
          sh """
            #!/usr/bin/env sh
            set +o xtrace
            set -o errexit

            curl --location https://dcos-terraform-mappings.mesosphere.com/ > tfdescsan.tsv
          """
          stash includes: 'tfdescsan.tsv', name: 'tfdescsan.tsv'
        }
      }
      stage('Validate descriptions') {
        agent {
          label 'tfdescsan'
        }
        steps {
          unstash 'tfdescsan.tsv'
          sh """
            #!/usr/bin/env sh
            set +o xtrace
            set -o errexit

            for tf in ./*.tf; do
              tfdescsan --test --tsv tfdescsan.tsv --var \${tf} \
                --cloud \"\$(echo \${PWD##*/terraform-} | sed -E \"s/(rm)?-.*//\")\"
            done
          """
        }
      }
    }
  }
}

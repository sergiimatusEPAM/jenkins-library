#!/usr/bin/env groovy
pipeline {
  agent none
  stages {
    stage('Tests') {
      agent {
        label 'terraform'
      }
      steps {
        sh 'terraform fmt --check --diff'
      }
    }
  }
}

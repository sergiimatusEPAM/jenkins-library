#!/usr/bin/env groovy
def call() {
  return env.CHANGE_TARGET ? env.CHANGE_TARGET : env.BRANCH_NAME
}

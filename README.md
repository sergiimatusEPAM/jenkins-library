# Description

The jenkins-library should be used to have a common collection of pipelines and functions to be
used in projects which want to use Jenkins and the pipeline definition to run jobs.
See for more details on Jenkins [shared-libraries].

## Setup / Configuration Jenkins

To provide the library to a specific `GitHub Organization` in the Jenkins instance do the following:

1. Select the `GitHub Organization` of your choice.
2. Click on `Configure`.
3. Go to `Pipeline Libraries`.
4. Click on `Add`.
5. Define the `Name`, `Default version` (should be a stable / released one and be updated once a
  new version is released), set `Allow default version to be overridden`, unset `Load implicitly`
  and `Include @Library changes in job recent changes`.
6. Define the `Retrieval method` and `Source Code Management`, all the settings here just be used
  for the library and not for projects / other jobs.

## Vars

### terraform

Defines the pipeline to be called for a `dcos-terraform` repository.

```groovy
#!/usr/bin/env groovy
@Library('jenkins-library') _

terraform()
```

### getTargetBranch

Helper function to get the target branch also on PRs, otherwise the BRANCH_NAME.

```groovy
#!/usr/bin/env groovy
@Library('jenkins-library') _

getTargetBranch()
```

[shared-libraries]: https://jenkins.io/doc/book/pipeline/shared-libraries/

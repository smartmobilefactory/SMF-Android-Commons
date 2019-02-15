#!/usr/bin/env groovy
//
// Automatically generated. Do not edit.
//

pipeline {

    agent { label 'androidbuild' }

    environment {
        LC_ALL = 'en_US.UTF-8'
        LANG = 'en_US.UTF-8'
        MANUAL_BUILD = "${currentBuild.rawBuild.getCause(hudson.model.Cause$UserIdCause) != null}"
        DANGER_GITHUB_API_TOKEN = credentials('DANGER_GITHUB_API_TOKEN')
        HOCKEYAPP_API_TOKEN = credentials('HOCKEYAPP_API_TOKEN')
    }

    parameters {
        choice(choices: ['Alpha', 'Beta', 'Live'], description: 'Target', name: 'build_variant')
    }

    triggers {
        issueCommentTrigger('ok to test')
    }

    stages {
        stage('Check Pull Request') {
            when { expression { env.CHANGE_ID != null } }

            steps {
                sh '''#!/bin/bash -l
                    cd fastlane
                    fastlane building_pr_phase
                    '''
            }
        }

        stage('Build and Deploy') {
            when { expression { env.MANUAL_BUILD == "true" } }

            steps {
                sh '''#!/bin/bash -l
                    cd fastlane
                    fastlane releasing_pr_phase build_variant:${build_variant} branch:${BRANCH_NAME}
                    '''
            }
        }
    }
}
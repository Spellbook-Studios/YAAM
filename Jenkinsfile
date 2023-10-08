pipeline {
    agent any

    environment {
        MODRINTH_TOKEN=credentials('modrinth-spellbook-studios-artifact')
    }

    stages {
        stage('Checkout') {
            steps {
                scmSkip(deleteBuild: true, skipPattern:'.*\\[no ci\\].*')
            }
        }

        stage('Gen Changelist') {
            steps {
                script {
                    changes = "Changes:<n>"
                    build = currentBuild

                    while(build != null && build.result != 'SUCCESS') {
                        for (changeLog in build.changeSets) {
                            for (change in changeLog) {
                                changes += "<n>"
                                changes += "* ${change.getMsg()} - ${change.getAuthorName()}"
                            }
                        }
                        build = build.previousBuild
                    }
                }
            }
        }

        stage('Build') {
            steps {
                withGradle {
                    sh "./gradlew clean :forge:modrinth :fabric:modrinth -PchangeLog=\"${changes}\""
                }
                archiveArtifacts artifacts: "forge/build/libs/*.jar", fingerprint: true, followSymlinks: false, onlyIfSuccessful: true
                archiveArtifacts artifacts: "fabric/build/libs/*.jar", fingerprint: true, followSymlinks: false, onlyIfSuccessful: true
            }
        }
    }
}
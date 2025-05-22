pipeline {
  agent any

  triggers {
    pollSCM('H/5 * * * *') // Poll GitHub every 5 minutes for new commits
  }

  stages {
    stage('Build') {
      steps {
        echo 'Building the project using Maven...'
      }
    }

    stage('Unit and Integration Tests') {
      steps {
        echo 'Running unit and integration tests using JUnit...'
      }
    }

    stage('Code Analysis') {
      steps {
        echo 'Running static code analysis using SonarQube...'
      }
    }

    stage('Security Scan') {
      steps {
        echo 'Running security scan using OWASP Dependency-Check...'
      }
    }

    stage('Deploy to Staging') {
      steps {
        echo 'Deploying to staging environment (AWS EC2)...'
      }
    }

    stage('Integration Tests on Staging') {
      steps {
        echo 'Running integration tests on staging...'
      }
    }

    stage('final') {
      steps {
        echo 'this is my output...'
      }
    }
  }
}

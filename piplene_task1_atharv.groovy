pipeline {
  agent any

  triggers {
    pollSCM('H/5 * * * *') 
  }

  stages {
    stage('Build') {
      steps {
        echo 'Build the project using Maven...'
        
        sh 'mvn clean package'
      }
    }

    stage('Unit and Integration Tests') {
      steps {
        echo 'Run unit and integration tests using JUnit...'
        sh 'mvn test'
      }
    }

    stage('Code Analysis') {
      steps {
        echo 'Run static code analysis using SonarQube...'
        sh 'mvn sonar:sonar'
      }
    }

    stage('Security Scan') {
      steps {
        echo 'Run security scan using OWASP Dependency-Check...'
        sh 'mvn org.owasp:dependency-check-maven:check'
      }
    }

    stage('Deploy to Staging') {
      steps {
        echo 'Deploying to staging environment (AWS EC2)...'
        sh 'scp target/app.jar ec2-user@staging-server:/var/app/'
      }
    }

    stage('Integration Tests on Staging') {
      steps {
        echo 'Run integration tests on staging...'
        sh 'curl -X GET http://staging-server/api/health'
      }
    }

    stage('Deploy to Production') {
      steps {
        echo 'Deploying to production environment (AWS EC2)...'
        sh 'scp target/app.jar ec2-user@production-server:/var/app/'
      }
    }
  }
}

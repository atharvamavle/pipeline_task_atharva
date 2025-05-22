pipeline {
  agent any

  triggers {
    pollSCM('H/5 * * * *') // Poll GitHub every 5 minutes for new commits
  }

  stages {
    stage('Build') {
      steps {
        echo 'Building the project using Maven...'
        // Tool: Maven
        sh 'mvn clean package'
      }
    }

    stage('Unit and Integration Tests') {
      steps {
        echo 'Running unit and integration tests using JUnit...'
        // Tool: JUnit (via Maven Surefire Plugin)
        sh 'mvn test'
      }
    }

    stage('Code Analysis') {
      steps {
        echo 'Running static code analysis using SonarQube...'
        // Tool: SonarQube
        sh 'mvn sonar:sonar'
      }
    }

    stage('Security Scan') {
      steps {
        echo 'Running security scan using OWASP Dependency-Check...'
        // Tool: OWASP Dependency-Check
        sh 'mvn org.owasp:dependency-check-maven:check'
      }
    }

    stage('Deploy to Staging') {
      steps {
        echo 'Deploying to staging environment (AWS EC2)...'
        // Tool: SCP/SSH or Ansible
        sh 'scp target/app.jar ec2-user@staging-server:/var/app/'
      }
    }

    stage('Integration Tests on Staging') {
      steps {
        echo 'Running integration tests on staging...'
        // Tool: Postman or custom scripts
        sh 'curl -X GET http://staging-server/api/health'
      }
    }

    stage('Deploy to Production') {
      steps {
        echo 'Deploying to production environment (AWS EC2)...'
        // Tool: SCP/SSH or Ansible
        sh 'scp target/app.jar ec2-user@production-server:/var/app/'
      }
    }
  }
}

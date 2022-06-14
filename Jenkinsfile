pipeline {
  agent {
    docker {
        image 'maven'
        args '-v /root/.m2:/root/.m2'
    }
  }
  stages {
    stage('Build') {
      steps {
        echo 'Building..'
        sh 'mvn -B -DskipTests clean package'
      }
    }

    stage('Test') {
      steps {
        echo 'Testing..'
      }
    }

    stage('Deploy') {
      steps {
        echo 'Deploying....'
      }
    }

  }
}
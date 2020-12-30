def label = "worker-${UUID.randomUUID().toString()}"

podTemplate(label: label, containers: [
  containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true),
  containerTemplate(name: 'helm', image: 'core.harbor.domain/library/helm-kubectl:3.2.3', command: 'cat', ttyEnabled: true),
  containerTemplate(name: 'maven', image: 'maven:3.6.3-adoptopenjdk-8', command: 'cat', ttyEnabled: true),
  containerTemplate(name: 'jnlp', image: 'jenkinsci/jnlp-slave', args: '${computer.jnlpmac} ${computer.name}', envVars: [ 
      envVar(key: 'JENKINS_URL', value: 'http://jenkins.jenkins.svc.cluster.local:8080')
    ]
    )
],
volumes: [
  persistentVolumeClaim(mountPath: '/root/.m2/repository', claimName: 'mavenrepo', readOnly: false),
  hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')
]) {
  node(label) {
    def myRepo = checkout scm
    def registry = env.REGISTRY_URL
    def shortGitCommit = sh(script: "printf \$(git rev-parse --short HEAD)",returnStdout: true)
    def image = "tcb-docker/identity-integration"
    def version = "0.0.${BUILD_NUMBER}"
    def helm_repo_url = "http://13.228.16.201:8081/repository/tcb-helm/"
    def helm_repo = "tcb-nexus"
    def app = "identity-integration"

    container('maven') {

        stage('Maven Cleanup') {
            sh "mvn clean"
        }
        
        stage('Execute Unit Testing') {
             sh "mvn test"
        }

        stage('SonarQube Analysis') {
          withSonarQubeEnv(credentialsId: 'sonarqube-token', installationName: 'SonarQube') {
            sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
          }
        }

        stage('Packaging Jar File') {
            sh "mvn package"
        }

    }

    container('docker') {
      stage('Build docker image') {
          sh """
            docker login ${registry} -u testuser -p testpassword
            docker build -t ${registry}/${image}:1.0.0 .
            docker push ${registry}/${image}:1.0.0

            docker tag ${registry}/${image}:1.0.0 ${registry}/${image}:latest
            docker push ${registry}/${image}:latest
            """
      }
    }

    container('helm') {
      stage('Release to helm repository') {
          sh """
          cd k8s
          helm repo add  ${helm_repo} \
          --username=testuser \
          --password=testpassword ${helm_repo_url}
          helm package ./${app}
          curl -u testuser:testpassword ${helm_repo_url} --upload-file ${app}-0.1.0.tgz
          """
      }
    }
  }
}

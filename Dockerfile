FROM registry.cn-hangzhou.aliyuncs.com/k8ops/jenkins:lts-jdk11

RUN jenkins-plugin-cli --plugins rebuild ws-cleanup pipeline-utility-steps blueocean ant
COPY --chown=jenkins:jenkins ./asl.hpi /usr/share/jenkins/ref/plugins/

ENV JENKINS_SLAVE_AGENT_PORT 50000

COPY init_scripts/ /usr/share/jenkins/ref/init.groovy.d/

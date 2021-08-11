FROM registry.cn-hangzhou.aliyuncs.com/k8ops/asl-steps:v0.1 as asl-steps

FROM registry.cn-hangzhou.aliyuncs.com/k8ops/asl-pipeline-library:v0.1 as apl

FROM registry.cn-hangzhou.aliyuncs.com/k8ops/jenkins:lts-jdk11

ENV JENKINS_SLAVE_AGENT_PORT 50000

RUN jenkins-plugin-cli --verbose --plugins rebuild \ 
  ws-cleanup pipeline-utility-steps blueocean ant \
  filesystem_scm:2.1 gitlab-oauth swarm \
  build-name-setter
COPY --chown=jenkins:jenkins ./asl.hpi /usr/share/jenkins/ref/plugins/

COPY --from=apl /opt/asl-pipeline-library /var/pipeline-libs/apl
COPY --from=asl-steps /opt/asl-steps /data/asl-steps

COPY init_scripts/src/main/groovy/ /usr/share/jenkins/ref/init.groovy.d/

FROM registry.cn-hangzhou.aliyuncs.com/k8ops-build/ant-asl:v0.1 as ant-asl

FROM registry.cn-hangzhou.aliyuncs.com/k8ops-build/asl-pipeline-library:v0.1 as apl

FROM registry.cn-hangzhou.aliyuncs.com/k8ops/jenkins:lts-jdk11

ENV JENKINS_SLAVE_AGENT_PORT 50000

RUN jenkins-plugin-cli --verbose --plugins rebuild ws-cleanup pipeline-utility-steps blueocean ant filesystem_scm:2.1 gitlab-oauth
COPY --chown=jenkins:jenkins ./asl.hpi /usr/share/jenkins/ref/plugins/

COPY --from=apl /opt/asl-pipeline-library /var/pipeline-libs/apl
COPY --from=ant-asl /opt/ant-asl /data/ant-asl

COPY init_scripts/src/main/groovy/ /usr/share/jenkins/ref/init.groovy.d/

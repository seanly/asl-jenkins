package org.jenkinsci.core

import hudson.security.csrf.DefaultCrumbIssuer
import jenkins.model.Jenkins
import jenkins.security.s2m.AdminWhitelistRule
import org.kohsuke.stapler.StaplerProxy

/**
 * Offers basic operations for Jenkins core.
 * @author Oleg Nenashev.
 * @since TODO
 */
class JenkinsHelper {

    static getHostname() {
        String host = System.getProperty("io.jenkins.dev.host")
        return host ?: "localhost"
    }

    static getUrl() {
        String url = System.getProperty("url");
        return url? url: "http://localhost:8080"
    }

    static getAllowRunsOnMaster() {
        return Boolean.getBoolean("io.jenkins.dev.security.allowRunsOnMaster")
    }

    /**
     * Initializes the default security settings on the instance
     */
    static setupDefaultSecurity() {
        println("--- Configuring Remoting and Slave2Master security")
        Jenkins.getInstanceOrNull().agentProtocols = new HashSet<String>(["JNLP4-connect"])
        Jenkins.getInstanceOrNull().getExtensionList(StaplerProxy.class)
                .get(AdminWhitelistRule.class)
                .masterKillSwitch = false

        println("--- Checking the CSRF protection")
        if (Jenkins.getInstanceOrNull().crumbIssuer == null) {
            println "CSRF protection is disabled, Enabling the default Crumb Issuer"
            Jenkins.getInstanceOrNull().crumbIssuer = new DefaultCrumbIssuer(true)
        }

        println("--- Configuring Master")
        if (!allowRunsOnMaster) {
            // Set executors to null
            // It still allows flyweight tasks, which should be protected separately if required
            Jenkins.getInstanceOrNull().numExecutors = 0;
        }
    }
}
import jenkins.model.Jenkins
import jenkins.model.JenkinsLocationConfiguration
import hudson.tasks.Mailer
import org.jenkinsci.plugins.workflow.libs.*
import jenkins.plugins.git.GitSCMSource

// TODO: Configure Job Restrictions, Script Security, Authorize Project, etc., etc.

println("--- Configuring Quiet Period")
// We do not wait for anything
Jenkins.instance.quietPeriod = 0

println("--- Configuring Email global settings")
JenkinsLocationConfiguration.get().adminAddress = "admin@non.existent.email"
Mailer.descriptor().defaultSuffix = "@non.existent.email"


// add scm link
GlobalLibraries descriptor = (GlobalLibraries) GlobalLibraries.get()

def pipelineLibrarySource = new GitSCMSource("apl", "https://github.com/seanly/asl-pipeline-library.git", null, null, null, false)
def lc = new LibraryConfiguration("apl", new SCMSourceRetriever(pipelineLibrarySource))
lc.with {
  implicit = true
  defaultVersion = "master"
}

descriptor.setLibraries(Arrays.asList(lc))
descriptor.save()



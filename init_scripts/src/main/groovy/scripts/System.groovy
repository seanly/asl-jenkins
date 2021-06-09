import cn.k8ops.ci.PipelineLibrary
import jenkins.model.Jenkins
import jenkins.model.JenkinsLocationConfiguration
import hudson.tasks.Mailer
import org.jenkinsci.plugins.workflow.libs.*
import groovy.io.FileType

// TODO: Configure Job Restrictions, Script Security, Authorize Project, etc., etc.

println("--- Configuring Quiet Period")
// We do not wait for anything
Jenkins.getInstanceOrNull().quietPeriod = 0

println("--- Configuring Email global settings")
JenkinsLocationConfiguration.get().adminAddress = "admin@non.existent.email"
Mailer.descriptor().defaultSuffix = "@non.existent.email"

ArrayList<LibraryConfiguration> customPipelineLibs = []
def pipelineLibsDir = new File("/var/pipeline-libs")
if (pipelineLibsDir.exists()) {
    println("==== Scanning the Pipeline Dev library")
    pipelineLibsDir.eachFile (FileType.DIRECTORIES) { directoryPath ->
        if (new File(directoryPath, "vars").exists()) {
            println("===== Adding Pipeline Library ${directoryPath}")
            customPipelineLibs.add(PipelineLibrary.fromFilesystem(directoryPath))
        }
    }
}

GlobalLibraries descriptor = (GlobalLibraries) GlobalLibraries.get()
descriptor.setLibraries(customPipelineLibs)
descriptor.save()



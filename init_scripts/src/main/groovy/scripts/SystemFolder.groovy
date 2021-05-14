import com.cloudbees.hudson.plugins.folder.Folder
import jenkins.model.Jenkins

def folder = Jenkins.getInstanceOrNull().createProject(Folder.class, "System")

// Include https://github.com/jenkins-infra/pipeline-library
folder.description = "This directory belongs to the Jenkins administrator. " +
        "By default he is eligible to run jobs from this Folder on the Master"
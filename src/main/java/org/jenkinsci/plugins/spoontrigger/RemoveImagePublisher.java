package org.jenkinsci.plugins.spoontrigger;

import com.google.common.reflect.TypeToken;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import org.jenkinsci.plugins.spoontrigger.client.RemoveImageCommand;
import org.jenkinsci.plugins.spoontrigger.client.SpoonClient;
import org.kohsuke.stapler.DataBoundConstructor;

public class RemoveImagePublisher extends SpoonBasePublisher {

    @DataBoundConstructor
    public RemoveImagePublisher() { }

    @Override
    public void publish(AbstractBuild<?, ?> abstractBuild, Launcher launcher, BuildListener listener) throws IllegalStateException {
        SpoonClient client = super.createClient(abstractBuild, launcher, listener);
        RemoveImageCommand removeImageCmd = this.createRemoveImageCommand();
        removeImageCmd.run(client);
    }

    private RemoveImageCommand createRemoveImageCommand() {
        return RemoveImageCommand.builder().image(super.getImageName().get()).build();
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return TypeToken.of(SpoonProject.class).isAssignableFrom(aClass);
        }

        @Override
        public String getDisplayName() {
            return "Remove local Turbo image";
        }
    }
}

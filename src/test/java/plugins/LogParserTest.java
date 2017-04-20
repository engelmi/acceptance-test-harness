package plugins;

import org.jenkinsci.test.acceptance.junit.AbstractJUnitTest;
import org.jenkinsci.test.acceptance.junit.Resource;
import org.jenkinsci.test.acceptance.junit.WithPlugins;
import org.jenkinsci.test.acceptance.plugins.logparser.LogParserGlobalConfig;
import org.jenkinsci.test.acceptance.po.*;
import org.junit.Before;
import org.junit.Test;

@WithPlugins("log-parser")
public class LogParserTest extends AbstractJUnitTest {

    private LogParserGlobalConfig config;

    @Before
    public void globalConfig() {
        jenkins.configure();
        config = new LogParserGlobalConfig(jenkins.getConfigPage());
        jenkins.save();
    }

    /**
     * Sample Test
     */
    @Test
    public void testing(){
        FreeStyleJob j = jenkins.jobs.create(FreeStyleJob.class, "simple-job");
        j.configure();
        Resource res = resource("/warnings_plugin/warningsAll.txt");
        if (res.asFile().isDirectory()) {
            j.copyDir(res);
        }
        else {
            j.copyResource(res);
        }
        catToConsole(j, "warningsAll.txt");
        j.save();

        j.startBuild().waitUntilFinished();
        String s = "";


    }

    /**
     * Adds a post-build-step to the job which prints out the content of the specified file. 
     *
     * @param job The job where the post-build-step is added.
     * @param str The name of the file which shall be printed out.
     */
    private void catToConsole(final Job job, final String str) {
        job.addShellStep("cat " + str);
    }

    /**
     * Adds a new rule to the existing config.
     * @param description The description of the new rule.
     * @param pathToFile The path to the rule file.
     */
    private void addLogParserRule(final String description, final String pathToFile){
        jenkins.configure();
        config.addParserConfig(description, pathToFile);
        jenkins.save();
    }
}

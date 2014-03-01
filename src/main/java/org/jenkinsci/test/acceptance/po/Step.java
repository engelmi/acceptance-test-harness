package org.jenkinsci.test.acceptance.po;

/**
 * Common part of {@link BuildStep} and {@link PostBuildStep}
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class Step extends PageArea {
    public final Job parent;

    public Step(Job parent, String path) {
        super(parent.injector,path);
        this.parent = parent;
    }
}

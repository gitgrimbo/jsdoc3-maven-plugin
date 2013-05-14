package com.github.phasebash.jsdoc3.maven.tasks;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.maven.plugin.logging.Log;

/**
 * TODO.
 */
final class PatchJsDocTask implements Task {
    final String[] PATCHED_RESOURCES = { "jsdoc.js", "lib/jsdoc/util/vm.js" };

    /**
     * TODO.
     * 
     * @param context
     *            The context containing any necessary bit of information for
     *            the task.
     * @throws TaskException
     */
    @Override
    public void execute(TaskContext context) throws TaskException {
        patch(context);
    }

    private void patch(TaskContext context) throws TaskException {
        for (int i = 0; i < PATCHED_RESOURCES.length; i++) {
            patch(context, i);
        }
    }

    private void patch(TaskContext context, int resourceIdx) throws TaskException {
        String resource = PATCHED_RESOURCES[resourceIdx];

        // Dest file uses the short resource name
        File f = new File(context.getJsDocDir(), resource);

        // Find the patched version using the full resource name
        String fullResource = "com/github/phasebash/jsdoc3/jsdoc/" + resource;
        URL url = getResource(fullResource);

        Log log = context.getLog();
        if (null != log && log.isInfoEnabled()) {
            log.info("Resource name:" + fullResource);
            log.info("Resource URL:" + url);
            log.info("Destination file:" + f);
        }

        if (null == url) {
            throw copyFailed(fullResource, url, f, null);
        }

        try {
            TaskUtils.copyTo(url, f.getParentFile(), f.getName());
        } catch (IOException e) {
            throw copyFailed(fullResource, url, f, e);
        }
    }

    private TaskException copyFailed(String resource, URL url, File f, Exception e) {
        String msg = "Failed to copy resource.";
        msg += "\nResource name=" + resource;
        msg += "\nClasspath URL=" + url + " (if null, indicates resource not found on classpath)";
        msg += "\nDestination file=" + f;
        return new TaskException(msg, e);
    }

    private URL getResource(String resource) {
        URL url = getClass().getClassLoader().getResource(resource);
        return url;
    }
}

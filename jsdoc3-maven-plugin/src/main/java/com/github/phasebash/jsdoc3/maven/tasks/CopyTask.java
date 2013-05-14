package com.github.phasebash.jsdoc3.maven.tasks;

import java.io.IOException;
import java.net.URL;

/**
 * A Task which copies the minimal jsdoc3 zip into a target directory.
 */
final class CopyTask implements Task {

    /** the jsdoc3 zip classpath reference */
    private static final String JSDOC_ARCHIVE_PATH = "com/github/jsdoc3/master.zip";

    /**
     * Copy the zip.
     *
     * @param context The context object which tells us where to put the zip.
     * @throws TaskException If we're unable to copy the zip.
     */
    @Override
    public void execute(final TaskContext context) throws TaskException {
        try {
            final URL resource = findJsDocArchive();
            TaskUtils.copyTo(resource, context.getTempDir(), "jsdoc.zip");
        } catch (IOException e) {
            throw new TaskException("Unable to copy jsdoc zip to temp dir.", e);
        }
    }

    /**
     * Returns the classpath URL of where the zip is located.
     *
     * @return The URL.
     * @throws IOException If we can't find the zip.
     */
    private URL findJsDocArchive() throws IOException {
        URL resource = getClass().getClassLoader().getResource(JSDOC_ARCHIVE_PATH);

        if (resource == null) {
            throw new IOException(JSDOC_ARCHIVE_PATH + " not found.");
        }

        return resource;
    }
}

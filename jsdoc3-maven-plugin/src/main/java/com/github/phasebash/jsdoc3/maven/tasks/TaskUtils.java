package com.github.phasebash.jsdoc3.maven.tasks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.codehaus.plexus.util.IOUtil;

public class TaskUtils {
    /**
     * Utility method to copy a URL to a directory with a file name.
     *
     * @param source The source URL
     * @param destinationDir The destination directory to hold the file.
     * @param fileName The file name.
     * @throws IOException If the file can't be written.
     */
    public static void copyTo(final URL source, final File destinationDir, final String fileName) throws IOException {
        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }

        final File file = new File(destinationDir, fileName);

        FileOutputStream fos = null;
        try {
            file.createNewFile();
            fos = new FileOutputStream(file);
            IOUtil.copy(source.openStream(), fos);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
}

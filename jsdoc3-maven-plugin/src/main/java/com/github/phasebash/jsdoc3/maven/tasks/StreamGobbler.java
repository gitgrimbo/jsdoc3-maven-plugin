package com.github.phasebash.jsdoc3.maven.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// Based on "http://www.javaworld.com/jw-12-2000/jw-1229-traps.html?page=4"
public class StreamGobbler extends Thread {
    InputStream in;
    OutputStream out;

    StreamGobbler(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void run() {
        byte[] buffer = new byte[1024 * 8];
        try {
            int read = -1;
            while ((read = in.read(buffer)) > -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException ioe) {
            // Not great exception handling.
            ioe.printStackTrace();
        }
    }
}
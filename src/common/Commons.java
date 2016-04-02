package common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public final class Commons {
    private Commons() {} // Util class

    /** Create the instance of a file writer and attach a shutdown hock for it */
    public static Optional<FileWriter> createFileWriter(String fileName) {
        FileWriter writerReturn = null;
        try {
            FileWriter writer = new FileWriter(new File(fileName));
            writerReturn = writer;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    writer.close();
                } catch (IOException error) {
                    System.out.println("Could not close the stream: " + error.getMessage());
                }
            }));
        } catch (IOException error) {
            System.out.println("Problems opening the file: " + error.getMessage());
        }
        return Optional.ofNullable(writerReturn);
    }
}

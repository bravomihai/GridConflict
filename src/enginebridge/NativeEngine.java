package enginebridge;

import java.io.*;
import java.util.concurrent.*;
import model.*;

public class NativeEngine {

    private final Process process;
    private final BufferedWriter writer;
    private final BufferedReader reader;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public NativeEngine(String exePath) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(exePath);
        pb.redirectErrorStream(true);
        process = pb.start();

        writer = new BufferedWriter(
                new OutputStreamWriter(process.getOutputStream())
        );

        reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        );
    }

    public CompletableFuture<String> computeMoveAsync(GameState state) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String input = state.toEngineString();

                writer.write(input);
                writer.newLine();
                writer.write("END");
                writer.newLine();
                writer.flush();

                return reader.readLine();

            } catch (Exception e) {
                return "p . 0";
            }
        }, executor);
    }

    public void shutdown() {
        try {
            writer.close();
            reader.close();
        } catch (Exception ignored) {}
        process.destroy();
        executor.shutdown();
    }
}
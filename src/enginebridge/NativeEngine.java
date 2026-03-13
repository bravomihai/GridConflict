package enginebridge;

import java.io.*;
import java.util.concurrent.*;
import model.*;

// a bridge to the move engine that implements minimax with alpha-beta pruning
public class NativeEngine {

    private final Process process;
    private final BufferedWriter writer;
    private final BufferedReader reader;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final int engineDepth;

    public NativeEngine(String exePath, int engineDepth) throws IOException {
        this.engineDepth = engineDepth;
        ProcessBuilder pb = new ProcessBuilder(exePath);
        process = pb.start();

        writer = new BufferedWriter(
                new OutputStreamWriter(process.getOutputStream())
        );

        reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        );
    }

    public String toEngineString(GameState gs) {
        StringBuilder sb = new StringBuilder();

        sb.append(gs.getHeight()).append(" ")
                .append(gs.getWidth()).append(" ")
                .append((gs.getPlayerIndex() == 0) ? 'A' : 'B').append(" ")
                .append(engineDepth).append("\n");

        Player p0 = gs.getPlayers().get(0);
        sb.append(p0.H).append(" ")
                .append(p0.A).append(" ")
                .append(p0.D).append(" ")
                .append(p0.s).append(" ")
                .append(p0.S).append("\n");

        Player p1 = gs.getPlayers().get(1);
        sb.append(p1.H).append(" ")
                .append(p1.A).append(" ")
                .append(p1.D).append(" ")
                .append(p1.s).append(" ")
                .append(p1.S).append("\n");

        sb.append(gs.getItems().size()).append("\n");

        for (Item it : gs.getItems()) {
            sb.append(it.dH).append(" ")
                    .append(it.dA).append(" ")
                    .append(it.dD).append(" ")
                    .append(it.dS).append("\n");
        }

        sb.append(gs.toEncodedMap());

        return sb.toString();
    }

    public CompletableFuture<EngineResult> computeMoveAsync(GameState gameState) {
        if(!gameState.isValid()){
            return CompletableFuture.completedFuture(
                    new EngineResult("c . 0 -536870911 0.0")
            );
        }

        return CompletableFuture.supplyAsync(() -> {
            try {

                writer.write(toEngineString(gameState));
                writer.newLine();
                writer.write("END");
                writer.newLine();
                writer.flush();

                String line = reader.readLine();
                if(line == null)
                    throw new IOException("Engine closed stream");

                return new EngineResult(line);

            } catch (Exception e) {
                return new EngineResult("p . 0 -536870911 0.0");
            }
        }, executor);
    }

    public void shutdown() {
        try {
            writer.close();
            reader.close();
        } catch (Exception ignored) {}
        process.destroyForcibly();
        executor.shutdown();
    }
}
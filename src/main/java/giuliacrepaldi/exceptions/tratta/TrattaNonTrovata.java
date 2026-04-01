package giuliacrepaldi.exceptions.tratta;

import java.util.UUID;

public class TrattaNonTrovata extends RuntimeException {
    public TrattaNonTrovata(UUID message) {
        super(String.valueOf(message));
    }
}

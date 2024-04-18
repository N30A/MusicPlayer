import java.io.File;
import java.util.Objects;

public class Song {
    private final String name;
    private final String type;
    private final String filePath;

    public Song(File file) {
        if(file == null) {
            throw new NullPointerException("File object may not be null");
        }

        int lastDot = file.getName().lastIndexOf(".");
        name = file.getName().substring(0, lastDot);
        type = file.getName().substring(lastDot + 1);
        filePath = file.getAbsolutePath();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Song song = (Song) object;
        return Objects.equals(name, song.name) && Objects.equals(type, song.type) && Objects.equals(filePath, song.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, filePath);
    }

    @Override
    public String toString() {
        return "Song(name=\"%s\", type=\"%s\", filePath=\"%s\")".formatted(name, type, filePath);
    }
}

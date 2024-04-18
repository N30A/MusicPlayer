import java.io.File;

public class Loader {

    private static final String[] EXTENSIONS = {"mp3", "wav", "flac", "ogg", "aac", "aiff", "aif", "wma", "m4a", "alac", "mid", "midi"};

    public static void loadFile(Playlist playlist, String filePath) {
        File file = new File(filePath);
        String extension = extractExtension(file);

        if(file.exists() && file.isFile() && isAudioFile(extension)) {
            Song song = new Song(file);
            if(!playlist.contains(song)) {
                playlist.add(song);
            }
        }
    }

    public static void loadFromDirectory(Playlist playlist, String directoryPath) {
        loadFromDirectoryMethod(playlist, new File(directoryPath));
    }

    private static void loadFromDirectoryMethod(Playlist playlist, File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    loadFromDirectoryMethod(playlist, file);
                } else {
                    loadFile(playlist, file.getAbsolutePath());
                }
            }
        }
    }

    private static String extractExtension(File file) {
        int lastDot = file.getName().lastIndexOf(".");
        return file.getName().substring(lastDot + 1);
    }

    private static boolean isAudioFile(String extension) {
       for (String ext : EXTENSIONS) {
           if (ext.equalsIgnoreCase(extension)) {
               return true;
           }
       }
       return false;
    }
}

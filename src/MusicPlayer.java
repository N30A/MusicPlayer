public class MusicPlayer {
    private final Player player;
    private final Playlist playlist;

    public MusicPlayer() {
        playlist = new Playlist();
        player = new Player(playlist);
    }

    public void start() {
        try {
            mainMenu();
        } finally {
            player.close();
        }
    }

    private void mainMenu() {
        do {
            System.out.println(mainMenuOptions());
            String option = Terminal.input("Choose an option");

            switch (option) {
                case "1":
                    if (player.isPlaying()) {
                        player.stop();
                    } else {
                        doIfNotEmpty(player::play);
                    }
                    System.out.println();
                    break;
                case "2":
                    songMenu();
                    break;
                case "3":
                    playlistMenu();
                    break;
                case "0":
                    return;
            }
        } while(true);
    }

    private void songMenu() {
        do {
            System.out.println("\n" + songMenuOptions());
            String option = Terminal.input("Choose an option");

            switch (option) {
                case "1":
                    doIfNotEmpty(player::next);
                    break;
                case "2":
                    doIfNotEmpty(player::previous);
                    break;
                case "3":
                    doIfNotEmptyAndHasNotFinished(() -> player.forward(10000));
                    break;
                case "4":
                    doIfNotEmptyAndHasNotFinished(() -> player.rewind(-10000));
                    break;
                case "5":
                    doIfNotEmptyAndHasNotFinished(() -> {
                        if (player.isPlaying()) {
                            player.pause();
                        } else {
                            player.unpause();
                        }
                    });

                    break;
                case "6":
                    doIfNotEmptyAndHasNotFinished(() -> System.out.println(songInfo()));
                    break;
                case "0":
                    System.out.println();
                    return;
            }
        } while(true);
    }

    private void playlistMenu() {
        do {
            System.out.println("\n" + playlistMenuOptions());
            String option = Terminal.input("Choose an option");

            switch (option) {
                case "1":
                    String filePath = Terminal.input("\nSong path");
                    Loader.loadFile(playlist, filePath);
                    System.out.println("\nsong have been loaded\n");
                    break;
                case "2":
                    String directoryPath = Terminal.input("\nSong directory path");
                    int songCount = playlist.numberOfSongs();
                    Loader.loadFromDirectory(playlist, directoryPath);
                    System.out.printf("%n%d songs have been loaded%n", playlist.numberOfSongs() - songCount);
                    break;
                case "3":
                    playlist.shuffle();
                    break;
                case "4":
                    playlist.clear();
                    break;
                case "0":
                    System.out.println();
                    return;
            }
        } while(true);
    }

    private String mainMenuOptions() {
        return
            """
            1. Start/Stop
            2. Song menu
            3. Playlist menu
            0. Exit
            """;
    }

    private String songMenuOptions() {
        return
            """
            1. Next song
            2. Previous song
            3. Fast forward
            4. Rewind
            5. Pause/Unpause
            6. Show playing song
            0. Go back
            """;
    }

    private String playlistMenuOptions() {
        return
            """
            1. Load a single song
            2. Load songs from a directory
            3. Shuffle the playlist
            4. Empty the playlist
            0. Go back
            """;
    }

    private String songInfo() {
        String text = "\nPlaying: " + player.currentSong().getName();

        if(!player.isPlaying()) {
            text += " (Paused)";
        }

        return text;
    }

    private String noSongsMessage() {
        return "\nNo songs are loaded";
    }

    private void doIfNotEmpty(Runnable action) {
        if (playlist.isEmpty()) {
            System.out.println(noSongsMessage());
        } else {
            action.run();
        }
    }

    private void doIfNotEmptyAndHasNotFinished(Runnable action) {
        if (playlist.isEmpty() && player.hasFinished()) {
            System.out.println(noSongsMessage());
        } else {
            action.run();
        }
    }
}
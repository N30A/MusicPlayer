import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

public class Player {
    private final AudioPlayerComponent playerComponent;
    private final Playlist playlist;
    private int currentIndex;
    private Song currentSong;

    public Player(Playlist playlist) {
        playerComponent = new AudioPlayerComponent();
        this.playlist = playlist;
        currentIndex = 0;
        currentSong = null;

        playerComponent.mediaPlayer().audio().setVolume(75);
        playerComponent.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                mediaPlayer.submit(() -> handleSong());
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                mediaPlayer.submit(() -> {
                    playlist.remove(currentSong);
                    currentIndex--;
                    handleSong();
                });
            }
        });
    }

    private boolean isLastSong() {
        return currentIndex == playlist.numberOfSongs() - 1;
    }

    private void handleSong() {
        if(isLastSong()) {
            currentIndex = 0;
            play();
        } else {
            next();
        }
    }

    public void play() {
        if(currentIndex >= 0 && currentIndex < playlist.numberOfSongs() && !playlist.isEmpty()) {
            currentSong = playlist.get(currentIndex);
            playerComponent.mediaPlayer().media().play(currentSong.getFilePath());
        }
    }

    public void stop() {
        playerComponent.mediaPlayer().controls().stop();
    }

    public void next() {
        if(currentIndex < playlist.numberOfSongs() - 1) {
            currentIndex++;
            play();
        }
    }

    public void previous() {
        if(currentIndex > 0)  {
            currentIndex--;
            play();
        }
    }

    public void pause() {
        playerComponent.mediaPlayer().controls().setPause(true);
    }

    public void unpause() {
        playerComponent.mediaPlayer().controls().setPause(false);
    }

    public void forward(long milliseconds) {
        if(milliseconds > 0L) {
            playerComponent.mediaPlayer().submit(() -> playerComponent.mediaPlayer().controls().skipTime(milliseconds));
        }
    }

    public void rewind(long milliseconds) {
        if(milliseconds < 0L) {
            playerComponent.mediaPlayer().submit(() -> playerComponent.mediaPlayer().controls().skipTime(milliseconds));
        }
    }

    public boolean isPlaying() {
        return playerComponent.mediaPlayer().status().isPlaying();
    }

    public boolean hasFinished() {
        long length = playerComponent.mediaPlayer().status().length();
        long position = playerComponent.mediaPlayer().status().time();

        return length - position < 500L;
    }

    public Song currentSong() {
        return currentSong;
    }

    public void close() {
        stop();
        playlist.clear();
        playerComponent.mediaPlayer().release();
    }
}

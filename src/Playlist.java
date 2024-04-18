import java.util.*;

public class Playlist {

    private final List<Song> songs;

    public Playlist() {
        songs = new ArrayList<>();
    }

    public Song get(int index) {
        if(index >= 0 && index < songs.size()) {
            return songs.get(index);
        } else {
            throw new IndexOutOfBoundsException(index);
        }
    }

    public ArrayList<Song> list() {
        return new ArrayList<>(songs);
    }

    public int numberOfSongs() {
        return songs.size();
    }

    public boolean isEmpty() {
       return songs.isEmpty();
    }

    public void shuffle() {
        Collections.shuffle(songs);
    }

    public boolean contains(Song song) {
        for(Song currentSong: songs) {
            if(song.equals(currentSong)) {
                return true;
            }
        }
        return false;
    }

    private void add(Song song) {
        if(song != null) {
            songs.add(song);
        } else {
            throw new NullPointerException();
        }
    }

    public void add(List<Song> songs) {
        for(Song song : songs) {
            add(song);
        }
    }

    public void add(Song ...songs) {
        add(Arrays.asList(songs));
    }

    private void remove(Song song) {
        if(song == null) {
            return;
        }

        for(int i = songs.size() - 1; i >= 0; i--) {
            Song currentSong = songs.get(i);

            if(song.equals(currentSong)) {
                songs.remove(i);
            }
        }
    }

    public void remove(List<Song> songs) {
        for(Song song: songs) {
            remove(song);
        }
    }

    public void remove(Song ...songs) {
        remove(Arrays.asList(songs));
    }

    public void clear() {
        songs.clear();
    }

    @Override
    public String toString() {
        return songs.toString();
    }
}

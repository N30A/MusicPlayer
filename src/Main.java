public class Main {
    public static void main(String[] args) {
        try {
            MusicPlayer musicPlayer = new MusicPlayer();
            musicPlayer.start();
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Unable to load \"libvlc\"");
            System.exit(-1);
        }
    }
}

package OopsConcepts;

interface Camera {

    void takePhoto();
}

interface MusicPlayer {

    void playMusic();
}

class SmartPhone implements Camera, MusicPlayer {

    @Override
    public void takePhoto() {
        System.out.println("Photo captured");
    }

    @Override
    public void playMusic() {
        System.out.println("Music playing");
    }
}

public class MultipleInheritanceUsingInterface {

    public static void main(String[] args) {

        SmartPhone phone = new SmartPhone();

        phone.takePhoto();
        phone.playMusic();
    }
}
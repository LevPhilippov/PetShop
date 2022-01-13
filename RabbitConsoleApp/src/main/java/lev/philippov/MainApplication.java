package lev.philippov;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MainApplication {
    public static void main(String[] args) {
        try {
            new Server().start();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}

package todo;

import static todo.Helper.*;
import static todo.Helper.getFile;

public class Main {
    public static void main(String[] args) {
        loadFile(getFile());
        mainStarter();
    }
}
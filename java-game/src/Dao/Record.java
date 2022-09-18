package Dao;

public class Record {
    private static String Username;
    private static String password;
    private static boolean save=false;

    public static String getUsername() {
        return Username;
    }

    public static void setUsername(String username) {
        Username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Record.password = password;
    }

    public static boolean isSave() {
        return save;
    }

    public static void setSave(boolean save) {
        Record.save = save;
    }
}

package color;

public enum ConsoleColor {
    Reset("\033[0m"),

    Black("\033[0;30m"),
    White("\033[0;37m"),
    Red("\033[0;31m"),
    Yellow("\033[0;33m"),
    Green("\033[0;32m"),
    Blue("\033[0;34m");


    private final String code;

    ConsoleColor(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}

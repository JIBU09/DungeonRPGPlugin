package de.jibu.dungeonrpgplugin.JUtils;

public class ColorCodeConverter {

    public static String convertChattingToCoding(String input) {
        return input.replace('&', '§');
    }

    public static String convertCodingToChatting(String input) {
        return input.replace('§', '&');
    }

}

package ua.juniffiro.jssutils;

/**
 * +-+-+-+-+-+ +-+-+ +-+-+-+-+-+-+-+-+-+
 * ( Created ) ( by ) ( @juniffiro )
 * 27/02/2023
 * +-+-+-+-+-+ +-+-+ +-+-+-+-+-+-+-+-+-+
 */
public class CodeMaker {

    public static StringBuilder makeCode(String... args) {
        StringBuilder builder = new StringBuilder();
        for (String param : args) {

            builder.append(param);


        }
        return builder;
    }
}

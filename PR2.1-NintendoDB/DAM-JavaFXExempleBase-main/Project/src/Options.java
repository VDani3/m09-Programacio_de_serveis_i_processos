public class Options {
    private static Options instance;
    public static String categorySel = "Jocs";
    public static int charSel = 0;

    private Options() {}

    public static Options getInstance() {
        if (instance == null) {
            instance = new Options();
        }

        return instance;
    }

    public static void changeCat(String nom){
        categorySel = nom;
    }

    public static void changeSel(int id) {
        charSel = id;
    }


}

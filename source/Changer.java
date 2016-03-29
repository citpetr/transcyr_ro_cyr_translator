import java.util.Arrays;

/**
 * Created by Petr on 26.03.2016.
 */
public class Changer {

    private char[] transchars =
            {'@','a','b','v','g','d','e','\'','z','i','j','k','l','m','n','o','p','r','s','t','u','f','x','c','h','w',']','y',';','=','/','[','q','A','B','V','G','D','E','"','Z','I','J','K','L','M','N','O','P','R','S','T','U','F','X','C','H','W','$','?','$','$','%','>'};
    private char[] cyrchars =
            {'!','а','б','в','г','д','е','ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х','ц','ч','ш','щ','ы','ь','ъ','э','ю','я','А','Б','В','Г','Д','Е','Ж','З','И','Й','К','Л','М','Н','О','П','Р','С','Т','У','Ф','Х','Ц','Ч','Ш','Щ','Э','Ю','Я',':',';'};

    public String convert (String translit) {
        char[] symbols = translit.toCharArray();

        for (int i = 0; i<symbols.length;i++) {

            for (int a = 0; a<transchars.length;a++) {
                if (transchars[a] == symbols[i]) {
                    symbols[i] = cyrchars[a];
                }
            }
        }
        String res1 = new String(symbols);
        return res1;
    }
}

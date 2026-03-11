import java.io.*;
import java.util.*;

public class Anagrams {

    public static String signature(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static void main(String[] args) {

        String inputFile = "joyce1922_ulysses-1.text";
        System.out.println("Data file: " + inputFile);

        HashMap<String, ArrayList<String>> D = new HashMap<>();

        try {
            Scanner sc = new Scanner(new File(inputFile), "ISO-8859-1");

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                // Use Scanner again to split words in the line
                Scanner wordScanner = new Scanner(line);
                while (wordScanner.hasNext()) {
                    String w = wordScanner.next();

                    // Clean punctuation but keep apostrophes
                    w = w.replaceAll("[.,;:_!\\-()\\[\\]0-9]", "");

                    w = w.toLowerCase();

                    if (w.length() == 0)
                        continue;

                    String key = signature(w);

                    D.computeIfAbsent(key, k -> new ArrayList<>()).add(w);
                }
                wordScanner.close();
            }

            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("The file " + inputFile + " was not found.");
        }

        //printing dictinoary contents
        for (String key : D.keySet()) {
            System.out.println(key + " " + D.get(key));
        }

        // Write LaTeX output
        try {
            PrintWriter tex = new PrintWriter(new FileWriter("anagrams-1.tex"));

            char letter = 'X';

            for (String key : D.keySet()) {
                ArrayList<String> list = D.get(key);

                if (list.size() > 1) { // only actual anagrams
                    String firstWord = list.get(0);
                    char initial = firstWord.charAt(0);

                    if (Character.toLowerCase(initial) != Character.toLowerCase(letter)) {
                        letter = initial;
                        tex.println("\n\\vspace{14pt}");
                        tex.println("\\noindent\\textbf{\\Large " +
                                Character.toUpperCase(initial) +
                                "}\\\\*[+12pt]");
                    }

                    for (String w : list) {
                        tex.print(w + " ");
                    }
                    tex.println("\\\\");
                }
            }
            tex.close();
        } catch (IOException e) {
            System.out.println("An error occured writting to the anagrams file");
        }
        System.out.println("Anagram dictionary generated in anagrams-1.tex");
    }
}
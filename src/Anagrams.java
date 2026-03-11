public class Anagrams {
}

        String inputfile = args[0];
        System.out.println("Data file: " + inputfile);

        // HashMap: signature -> list of words
        HashMap<String, ArrayList<String>> D = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputfile));
            String line;

            while ((line = br.readLine()) != null) {

                String[] words = line.split("\\s+");

                for (String w : words) {

                    // Clean punctuation but keep apostrophes
                    w = w.replaceAll("[.,;:_!\\-()\\[\\]0-9]", "");

                    w = w.toLowerCase();

                    if (w.length() == 0)
                        continue;

                    String key = signature(w);

                    if (!D.containsKey(key)) {
                        ArrayList<String> list = new ArrayList<>();
                        list.add(w);
                        D.put(key, list);
                    } else {
                        D.get(key).add(w);
                    }
                }
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error reading file.");
            e.printStackTrace();
        }

        // Print dictionary contents
        for (String key : D.keySet()) {
            System.out.println(key + " " + D.get(key));
        }

        // Write LaTeX output
        try {
            PrintWriter tex = new PrintWriter(new FileWriter("theAnagrams.tex"));

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
            System.out.println("Error writing LaTeX file.");
        }

        System.out.println("Anagram dictionary generated.");
    }
}
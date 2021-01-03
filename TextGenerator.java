public class TextGenerator {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]); // order k
        int t = Integer.parseInt(args[1]); // trajectory length T
        // Read input text from StdIn
        String text = "";
        while (!StdIn.isEmpty()) {
            text = StdIn.readAll();
        }

        // Create MarkovModel of order k using input text
        MarkovModel model = new MarkovModel(text, k);

        // Print initial k-gram
        String kgram = text.substring(0, k);
        StdOut.print(kgram);

        // Repeatedly generate and print a random character using the Markov
        // model:
        // for k = 0, generate a string of length T where each character
        // appears with probability proportional to its freq in input text;
        // for all other k update the k-gram each time by discarding the first
        //  character and appending the randomly generated character to the end
        for (int i = 0; i < t - k; i++) {
            char random = model.random(kgram);
            StdOut.print(random);
            if (k == 0) // update the kgram unless k = 0
                kgram = text.substring(0, k);
            else kgram = kgram.substring(1, k) + random;
        }

        StdOut.println();
    }

}

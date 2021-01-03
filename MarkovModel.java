public class MarkovModel {

    // Constant for num of ASCII codes
    private static final int ASCII = 128;
    // Symbol table storing frequencies of each k-gram
    private final ST<String, Integer> kgrams;
    // Symbol table storing frequencies of ASCII characters following
    // k-gram
    private final ST<String, int[]> nextchar;
    // Order k
    private final int order;

    // creates a Markov model of order k based on the specified text
    public MarkovModel(String text, int k) {
        // Create circular version of input text by appending the "wrap-around"
        // part from beginning of input text to complete the last k-gram
        order = k;
        String circText = text + text.substring(0, k);

        // Initialize and populate freq table of kgrams; the loop iteration
        // does not go past the length of the original input text
        kgrams = new ST<String, Integer>();

        for (int i = 0; i <= circText.length() - k - 1; i++) {
            String key = circText.substring(i, i + k);
            if (kgrams.contains(key))
                kgrams.put(key, kgrams.get(key) + 1);
            else kgrams.put(key, 1);
        }

        // Initialize and populate freq table of ASCII characters following
        // each k-gram; array indices of values correspond to ASCII code
        nextchar = new ST<String, int[]>();

        for (int i = 0; i <= circText.length() - k - 1; i++) {
            String key = circText.substring(i, i + k);
            char next = circText.charAt(i + k); // character following k-gram

            // If the key is already associated with a value, increment the
            // element indexed at next by 1; if not, create new key-value
            // pair with the value array initialized to 0, and set the freq
            // at index next to 1
            if (nextchar.contains(key))
                nextchar.get(key)[next] += 1;
            else {
                int[] chars = new int[ASCII];
                for (int j = 0; j < ASCII; j++) {
                    chars[j] = 0;
                }
                nextchar.put(key, chars);
                nextchar.get(key)[next] = 1;
            }
        }
    }

    // returns the order of the model (also known as k)
    public int order() {
        return order;
    }

    // returns a String representation of the model
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Loop through k-grams
        for (String key : nextchar.keys()) {
            sb.append(key + ": ");

            // Loop through next character frequencies
            for (int i = 0; i < ASCII; i++) {
                if (nextchar.get(key)[i] != 0) // if freq is not 0
                    sb.append((char) i + " " + nextchar.get(key)[i] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // returns the # of times 'kgram' appeared in the input text
    public int freq(String kgram) {
        if (kgram.length() != order)
            throw new IllegalArgumentException("argument must be of length k");
        if (!kgrams.contains(kgram))
            return 0;

        return kgrams.get(kgram);
    }

    // returns the # of times 'c' followed 'kgram' in the input text
    public int freq(String kgram, char c) {
        if (kgram.length() != order)
            throw new IllegalArgumentException("argument must be of length k");
        if (!nextchar.contains(kgram))
            return 0;

        return nextchar.get(kgram)[c];
    }

    // returns a random character, chosen with weight proportional to the
    // number of times each character followed 'kgram' in the input text
    public char random(String kgram) {
        if (kgram.length() != order())
            throw new IllegalArgumentException("argument must be of length k");
        if (!nextchar.contains(kgram))
            throw new IllegalArgumentException("argument not in text");

        int[] freq = nextchar.get(kgram);
        
        return (char) StdRandom.discrete(freq);
    }

    // tests all instance methods to make sure they're working as expected
    public static void main(String[] args) {
        // Create and print two MarkovModel objects
        String text1 = "banana";
        MarkovModel model1 = new MarkovModel(text1, 2);
        StdOut.println("k = " + model1.order());
        StdOut.println(model1);

        String text2 = "gagggagaggcgagaaa";
        MarkovModel model2 = new MarkovModel(text2, 2);
        StdOut.println("k = " + model2.order());
        StdOut.println(model2);

        // Test freq() methods on model2
        StdOut.println(model2.freq("ag")); // should print 5
        StdOut.println(model2.freq("ag", 'g')); // should print 2

        // Test random() on model2 given "ag";
        // generate a large number of random characters
        int trials = 10000;
        char[] randomChars = new char[trials];
        for (int i = 0; i < randomChars.length; i++) {
            randomChars[i] = model2.random("ag");
        }

        // Compare to expected ratio of 'a' to 'g' generated, which should be
        // about 3 to 2, or 1.5
        int aCount = 0;
        int gCount = 0;
        for (int i = 0; i < randomChars.length; i++) {
            if (randomChars[i] == 'a')
                aCount++;
            else gCount++;
        }
        StdOut.println((double) (aCount) / gCount);
    }

}


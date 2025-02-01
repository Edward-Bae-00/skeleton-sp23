package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        //make a map that will be returned
        Map<Character, Integer> ans = new TreeMap<>();
        //goes through all the words in the list
        for (int a = 0; a < words.size(); a++) {
            //makes a temporary string to hold the current word
            String temp = words.get(a);
            //loop to go through the characters of the string
            for (int b = 0; b < temp.length(); b++) {
                //if the map contains the character, then add 1 to the value it has
                //else it will add the character to the map with a value of 1
                if (ans.containsKey(temp.charAt(b))){
                    ans.put(temp.charAt(b), ans.get(temp.charAt(b)) + 1);
                }else{
                    ans.put(temp.charAt(b), 1);
                }
            }
        }
        return ans;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        //the max of the most common letter
        int max = 0;
        //the character of the most common letter
        Character ans = ' ';
        //make a temporary map to traverse
        Map<Character, Integer> thing = getFrequencyMap();
        //goes through all the keys in the map
        for (Character temp: thing.keySet()) {
            //checks if the specific key is not in the list of quesses made already
            if (!guesses.contains(temp)) {
                //if the character that is not already in guesses is larger than it will
                //get the character and how many times that character was spotted
                if (thing.get(temp) > max) {
                    max = thing.get(temp);
                    ans = temp;
                }
            }
        }
        //returns the most common character
        return ans;
    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}

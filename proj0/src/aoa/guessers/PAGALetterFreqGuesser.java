package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        //a map to hold the characters and how many times they have been spotted
        Map<Character, Integer> pos = new TreeMap<>();
        //a loop to go through all the words
        for (int b = 0; b < words.size(); b++) {
            //checks if the length of the current word is the same as the pattern given
            if (words.get(b).length() == pattern.length()){
                //a boolean to make sure that the current word follows the pattern
                Boolean match = true;
                //loop to go through all the characters of the word
                for (int c = 0; c < pattern.length(); c++) {
                    //checks if the character at the pattern is an actual significant character
                    if (pattern.charAt(c) != '-'){
                        //if the significant character given is not the same then the characters of the
                        //word are not added to the map
                        if (pattern.charAt(c) != words.get(b).charAt(c)){
                            match = false;
                        }
                    }else{
                        //also checks if the pattern's character is -, but the word's character is found in the list of what was guessed.
                        //we know in this case that this is impossible
                        if (pattern.charAt(c) == '-' && guesses.contains(words.get(b).charAt(c))){
                            match = false;
                        }
                    }
                }
                //segment that adds the characters to the map if the word matches the pattern
                if (match){
                    System.out.println(words.get(b));
                    //loop that goes all the characters of the word and ensures the key value pair is all correct
                    for (int d = 0; d < words.get(b).length(); d++) {
                        if (pos.containsKey(words.get(b).charAt(d))) {
                            pos.put(words.get(b).charAt(d), pos.get(words.get(b).charAt(d)) + 1);
                        } else {
                            pos.put(words.get(b).charAt(d), 1);
                        }
                    }
                }
            }
        }
        //print statements for checking
//        System.out.println(pattern);
//        for (Character temp: pos.keySet()) {
//            System.out.println(temp + ": " + pos.get(temp));
//        }
        //the same code segment as the naiveletterguesser's getGuess
        int max = 0;
        Character ans = ' ';
        for (Character temp: pos.keySet()) {
            if (!guesses.contains(temp)) {
                if (pos.get(temp) > max) {
                    max = pos.get(temp);
                    ans = temp;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}

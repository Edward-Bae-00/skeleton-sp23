package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {
        //checks the value of the wordlength and throws the right error is there is an issue
        if (wordLength < 0) {
            throw new IllegalArgumentException("wordLength must be greater than 1");
        }else if (wordLength < 1) {
            throw new IllegalStateException("wordLength must be greater than 1");
        }else if (wordLength >= Integer.MAX_VALUE) {
            throw new IllegalStateException("wordLength must be less than Integer.MAX_VALUE");
        }
        //makes an arraylist that will hold all the values in the dictionary and throws the right error if there is an issue
        ArrayList<String> ans = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(dictionaryFile))){
            String temp;
            while ((temp = br.readLine()) != null) {
                ans.add(temp);
            }
        } catch (FileNotFoundException e) {
           e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        //makes an arraylist that holds all value of the same length to the wordlength
        ArrayList<String> words = new ArrayList<String>();
        for (int a = 0; a < ans.size(); a++) {
            if (ans.get(a).length() == wordLength) {
                words.add(ans.get(a));
            }
        }
        //checks if the word size if 0 and returns an error
        if (words.size() == 0) {
            throw new IllegalStateException("wordLength not found");
        }
        //given function
        int numWords = words.size();
        int randomlyChosenWordNumber = StdRandom.uniform(numWords);
        chosenWord = words.get(randomlyChosenWordNumber);
        //this part is to set the pattern value to all "-"
        for (int i = 0; i < chosenWord.length(); i++) {
            if (this.pattern == null){
                this.pattern = "-";
            }else {
                this.pattern += "-";
            }
        }
        //System.out.println(this.pattern);
    }

    @Override
    public int makeGuess(char letter) {
        //the number that is to be returned
        int ans = 0;
        //the string that will update the pattern (holder value)
        String word = "";
        //goes through the entire length of the word
        for (int a = 0; a < chosenWord.length(); a++) {
            //if the pattern is not "-" then it is added straight to the holder value
            if (this.pattern.charAt(a) != '-') {
                word += this.pattern.charAt(a);
            //if the character at the chosenword is the same as the guessed letter add it to the holder value
            }else if (chosenWord.charAt(a) == letter) {
                word += letter;
                ans++;
            //in all other cases add "-" to the holder value
            }else{
                word += "-";
            }
        }
        //sets the pattern to word
        this.pattern = word;
        //returns the number of letters that were guessed that were found
        return ans;
    }

    @Override
    public String getPattern() {
        //returns the current pattern
        return this.pattern;
    }

    @Override
    public String getWord() {
        //returns the current word chosen
        return chosenWord;
    }
}

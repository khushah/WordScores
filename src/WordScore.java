/**
 * Created by test on 7/23/2015.
 */

import java.io.BufferedReader;
import java.io.*;
import java.util.*;

public class WordScore {

    private Map<Integer, ArrayList> map = new TreeMap(Collections.reverseOrder());

    public WordScore(String path){
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));
            while ((sCurrentLine = br.readLine()) != null) {
                addToHash(computeWordScore(sCurrentLine), sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int getLetterScore(char letter) {
        return letter + 'a' - 1;
    }

    public int computeWordScore(String word){
        int score = 0;
        char[] wordChars = word.toCharArray();
        for (int pos = 0; pos < wordChars.length; pos++) {
            score += getLetterScore(wordChars[pos]);
        }
        return score;
    }

    public void addToHash(Integer key, String val) {
        if (map.containsKey(key)) {
            ArrayList existingValues = map.get(key);
            existingValues.add(val);
            map.put(key, existingValues);
        } else {
            ArrayList<String> values = new ArrayList();
            values.add(val);
            map.put(key, values);
        }
    }

    public String printList() {
        String outputString = "";
        Set setOfScores = map.entrySet();
        Iterator iterator2 = setOfScores.iterator();
        while(iterator2.hasNext()) {
            Map.Entry wordScorePair = (Map.Entry) iterator2.next();
            outputString += (wordScorePair.getKey() + ": ");
            outputString += (wordScorePair.getValue() + "\n");
        }
        return outputString;
    }

    public void writeToFile(String path) {
        try {
            String content = printList();
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            System.out.println("Write complete!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WordScore wordscores = new WordScore(args[0]);
        wordscores.printList();
        wordscores.writeToFile(args[1]);
    }
}

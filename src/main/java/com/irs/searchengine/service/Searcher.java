package com.irs.searchengine.service;

/*
Description of question:
1.	Inverted index: Write a program that using the Web pages given in the resources, it constructs an inverted index, as explained in class. The words (keys) are stored in a trie. The value of each word is the index to a list of occurrences of that word in each Web page. You can keep the lists of occurrences in a two-dimensional array as explained in class.
2.	Web search engine: Write a program that given a keyword (a single word), it shows a list of Web pages in a ranking from high to low (sorted in decreasing order of occurrence of that word).
3.	String matching: Write another program that implements the search index from another perspective. Given a keyword and a web page, the program should use string matching to find how many occurrences of that keyword are in a particular web page. The program should do this for all Web pages, and rank the pages based on then number of occurrences. Note that the programs for string matching weâ€™ve seen in class find the first occurrence only. You will have to modify the matching algorithm to find all occurrences (or at least to count them).
*  @version November 20, 2020
*/

import com.irs.searchengine.dto.docInfo;
import com.irs.searchengine.ref.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Searcher {

    //scan file and get frequency store in hashmap
    public static void scanFile(String fileName, TST<Integer> tst)  throws IOException{
        StringBuffer sb = new StringBuffer();
        FileReader file = new FileReader(fileName);
        BufferedReader br = new BufferedReader(file);
//		TST<Integer> tst = new TST<Integer>();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        String article = sb.toString();
        // split  ,*)?;/&#<-.!:\"\"''\n to get word by use Tokenizer
        StringTokenizer st = new StringTokenizer(article, " ,`*$|~(){}_@><=+[]\\?;/&#<-.!:\"\"''\n");
        while(st.hasMoreTokens()) {
            String word = st.nextToken();  //return string until next token;
            if (tst.contains(word)) {
                int count = tst.get(word);
                tst.put(word, count + 1);
            } else {
                tst.put(word, 1);
            }
        }
    }

    public static PriorityQueue<Integer,String> occurrences(String scan) throws IOException {
        String txtPath = "src/main/resources/static/dat/text/";
        String htmlPath = "src/main/resources/static/dat/html/";

        File txt = new File(txtPath);
        File[] Files = txt.listFiles();
        File html = new File(htmlPath);
        File[] Webs = html.listFiles();

        PriorityQueue<Integer,String> pq = new SortedPriorityQueue<>();
        // scan new files
        for (int i = 0; i < Files.length; i++) {
            if (Files[i].isFile()) {
                TST<Integer> tst = new TST<Integer>();
                String path = (txtPath + Files[i].getName());
                scanFile(path, tst);// get occurrence from matching given word
                if (tst.get(scan) != null) {
                    //store occurrence and web name in priority queue
                    pq.insert(tst.get(scan), Webs[i].getName());
                }
            }
        }
        return pq;
    }

    public docInfo[] queue2List(PriorityQueue<Integer, String> pq) throws IOException{
        docInfo[] queryResults = new docInfo[pq.size()];
        Iterator<Entry<Integer, String>> s = pq.iterator();
        int flag = 0;
        while(s.hasNext()) {
            Entry<Integer, String> tmp = s.next();
            docInfo doc = new docInfo(tmp.getKey(), tmp.getValue());
            queryResults[(pq.size() - 1) - flag] = doc;
            flag++;
        }
        return queryResults;
    }

    public static void search() {
        Scanner scan = new Scanner(System.in);
        while(true) {
            System.out.println();
            System.out.println("Please input word or quit: ");
            String s = scan.next();
            if(s.equals("quit")) {
                break;
            } else {
                try {
                    occurrences(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
//		occurrences();
//		TST<Integer> tst = new TST<Integer>();
//		String src = "src/Protein.txt";
//		scanFile(src, tst);
        Scanner scan = new Scanner(System.in);
//		System.out.println("Please input word or quit: ");
        while(true) {
            System.out.println("Please input word or quit: ");
            String s = scan.next();
            if(s.equals("quit")) {
                break;
            } else {
                occurrences(s);
            }

        }
    }
}



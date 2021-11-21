package com.irs.searchengine.service;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpellChecking {
	
	public static ArrayList<String> key = new ArrayList<String>();
	public static Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();
	public static Scanner sc = new Scanner(System.in);



	/*using regex to find similar string to pattern */
	public static String[] alternativeWord(String s1) throws FileNotFoundException {
		ArrayList<String> altWords = new ArrayList<>();
		String line = " ";
		String pattern3 = "[a-zA-Z0-9]+";
		Pattern r3 = Pattern.compile(pattern3);
		Matcher m3 = r3.matcher(line);

		// Search new text
		File txts = new File("src/main/resources/static/dat/text/");
		File[] files = txts.listFiles();
		for (File item : files) {
			findKeyword(item, m3, s1);
		}

		// System.out.print("There are some alternative keyword: ");
		for(Map.Entry<String, Integer> entry: numbers.entrySet()){
			if(entry.getValue() == 0) {
				break;
			}
			else if(entry.getValue() == 1) {
				altWords.add(entry.getKey());
			}
		}

		String[] alt = new String[altWords.size()];
		for ( int i = 0; i < altWords.size(); i++){
			alt[i] = altWords.get(i);
		}
		return alt;
	}

	//finds strings with similar pattern and calls edit distance() on different strings
	public static void findKeyword(File sourceFile, Matcher m3, String p1) throws FileNotFoundException, ArrayIndexOutOfBoundsException
	{
		try	{
			BufferedReader br = new BufferedReader(new FileReader(sourceFile));
			String line = null;

			while ((line = br.readLine()) != null){
				m3.reset(line);
				while (m3.find()) {
					key.add(m3.group());
				}
			}
			br.close();
			for(int p = 0; p < key.size(); p++) {
				numbers.put(key.get(p), editDistance(p1.toLowerCase(), key.get(p).toLowerCase()));
			}
		} catch(Exception e) {
			System.out.println(">> Exception on findData:"+e);
		}
	}

	public static int editDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
	 
		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];
	 
		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}
	 
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}
	 
		//iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);
	 
				//if last two chars equal
				if (c1 == c2) {
					//update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;
	 
					int min = Math.min(replace, insert);
					min = Math.min(delete, min);
					dp[i + 1][j + 1] = min;
				}
			}
		}
	 
		return dp[len1][len2];
	}

	public static void main(String[] args) throws IOException{

		String s = sc.nextLine();
		alternativeWord(s);
	}
}

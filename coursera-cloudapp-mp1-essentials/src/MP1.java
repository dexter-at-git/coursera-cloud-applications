import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;

public class MP1 {
	Random generator;
	String userName;
	String inputFileName;
	String delimiters = " \t,;.?!-:@[](){}_*/";
	String[] stopWordsArray = { "i", "me", "my", "myself", "we", "our", "ours",
			"ourselves", "you", "your", "yours", "yourself", "yourselves",
			"he", "him", "his", "himself", "she", "her", "hers", "herself",
			"it", "its", "itself", "they", "them", "their", "theirs",
			"themselves", "what", "which", "who", "whom", "this", "that",
			"these", "those", "am", "is", "are", "was", "were", "be", "been",
			"being", "have", "has", "had", "having", "do", "does", "did",
			"doing", "a", "an", "the", "and", "but", "if", "or", "because",
			"as", "until", "while", "of", "at", "by", "for", "with", "about",
			"against", "between", "into", "through", "during", "before",
			"after", "above", "below", "to", "from", "up", "down", "in", "out",
			"on", "off", "over", "under", "again", "further", "then", "once",
			"here", "there", "when", "where", "why", "how", "all", "any",
			"both", "each", "few", "more", "most", "other", "some", "such",
			"no", "nor", "not", "only", "own", "same", "so", "than", "too",
			"very", "s", "t", "can", "will", "just", "don", "should", "now" };

	void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA");
		messageDigest.update(seed.toLowerCase().trim().getBytes());
		byte[] seedMD5 = messageDigest.digest();

		long longSeed = 0;
		for (int i = 0; i < seedMD5.length; i++) {
			longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
		}

		this.generator = new Random(longSeed);
	}

	Integer[] getIndexes() throws NoSuchAlgorithmException {
		Integer n = 10000;
		Integer number_of_lines = 50000;
		Integer[] ret = new Integer[n];
		for (int i = 0; i < n; i++) {
			ret[i] = generator.nextInt(number_of_lines);
		}
		return ret;
	}

	public MP1(String userName, String inputFileName) {
		this.userName = userName;
		this.inputFileName = inputFileName;
	}

	public String[] process() throws Exception {
		String[] ret = new String[20];

		List<String> titles = new ArrayList<String>();
		Map<String, Integer> dictionary = new HashMap<String, Integer>();

		initialRandomGenerator(this.userName);
		Integer[] indexes = getIndexes();

		try (BufferedReader br = new BufferedReader(new FileReader(
				this.inputFileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				titles.add(line);
			}
		}

		for (Integer index : indexes) {

			StringTokenizer stringTokenizer = new StringTokenizer(
					titles.get(index), delimiters);
			while (stringTokenizer.hasMoreTokens()) {

				String word = stringTokenizer.nextToken().trim().toLowerCase();

				if (Arrays.asList(stopWordsArray).contains(word)) {
					continue;
				}

				if (dictionary.containsKey(word)) {
					Integer count = dictionary.get(word);
					dictionary.put(word, count + 1);
				} else {
					dictionary.put(word, 1);
				}

			}
		}

		SortedSet<Map.Entry<String, Integer>> sortedSet = new TreeSet<>(new Comparator<Map.Entry<String, Integer>>() {
		    @Override
		    public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
		        int res = e1.getValue().compareTo(e2.getValue());
		        if(res == 0)
		            return e1.getKey().compareTo(e2.getKey());
		        return res * -1;
		    }
		});
		sortedSet.addAll(dictionary.entrySet());
		
		for(Map.Entry<String, Integer> e: sortedSet)
		{
		//	System.out.println(e.getKey() + " : " 					+ e.getValue());
		}
		
		Object[] arrayView = sortedSet.toArray();

		for (int i = 0; i < 20; i++) {

			ret[i] = ((Map.Entry<String, Integer>)arrayView[i]).getKey();
		}

		return ret;
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.out.println("MP1 <User ID>");
		} else {
			String userName = args[0];
			String inputFileName = "./input.txt";
			MP1 mp = new MP1(userName, inputFileName);
			String[] topItems = mp.process();
			for (String item : topItems) {
				System.out.println(item);
			}
		}
	}

	public class ValueThenKeyComparator<K extends Comparable<? super K>, V extends Comparable<? super V>>
			implements Comparator<Map.Entry<K, V>> {

		public int compare(Map.Entry<K, V> a, Map.Entry<K, V> b) {
			int cmp1 = a.getValue().compareTo(b.getValue());
			if (cmp1 != 0) {
				return cmp1;
			} else {
				return a.getKey().compareTo(b.getKey());
			}
		}

	}
}

package prog11;

import java.util.*;
import prog02.*;

public class Trie<V> extends AbstractMap<String, V> {

	private class Entry<V> implements Map.Entry<String, V> {
		String key;
		V value;

		Entry(String key, V value) {
			this.key = key;
			this.value = value;
			this.sub = key;
		}

		Entry(String sub, String key, V value) {
			this.key = key;
			this.value = value;
			this.sub = sub;
		}

		Entry(String sub, String key, V value, List<Entry<V>> list) {
			this.key = key;
			this.value = value;
			this.sub = sub;
			this.list = list;
		}

		public String getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public V setValue(V newValue) {
			V oldValue = value;
			value = newValue;
			return oldValue;
		}

		String sub;
		List<Entry<V>> list = new ArrayList<Entry<V>>();
	}

	private List<Entry<V>> list = new ArrayList<Entry<V>>();
	private int size;

	public int size() {
		return size;
	}

	/**
	 * Find the entry with the given key.
	 * 
	 * @param key  The key to be found.
	 * @param iKey The current starting character index in the key.
	 * @param list The list of entrys to search for the key.
	 * @return The entry with that key or null if not there.
	 */
	private Entry<V> find(String key, int iKey, List<Entry<V>> list) {
		// EXERCISE:
		int iEntry = 0;

		// iKey is the index of the string!!

		// Set iEntry to the index of the entry in list such that the first
		// character (charAt(0)) of its sub (NOT key) is the same as the
		// character at index iKey in key.
		// If no such entry, return null.
		while (iEntry < list.size() && key.charAt(iKey) > list.get(iEntry).sub.charAt(0)) {
			// set iEntry to index of entry in the list
			iEntry++;
			// only thing updated here is iEntry!
		}
		// check where iEntry went (return null if it's the opposite of the if statement
		// in while)
		if (iEntry == list.size() || key.charAt(iKey) < list.get(iEntry).sub.charAt(0)) {
			return null;
		}

		Entry<V> entry = list.get(iEntry);
		int iSub = 0;
		// index of the sub prefix

		// keep incrementing iKey and iSub until one of them doesn't find a letter
		// If we are here, then character iKey of key and iSub of entry.sub
		// must match. Increment both iKey and iSum until that is not true.

		do {
			iKey++;
			iSub++;
		} while (iSub < entry.sub.length() && iKey < key.length() && key.charAt(iKey) == entry.sub.charAt(iSub));
		// 3 conditions

		// If we have not matched all the characters of entry.sub, the key
		// is not in the Trie.

		if (iSub > entry.sub.length()) {
			return null;
		}

		// If we have matched all the characters of key, then entry might
		// be the one we want (to return). But only if its key is not
		// null. If it is null, the key is not in the Trie.
		///

		if (iKey == key.length()) {
			if (entry.key == null) {
				return null;
			} else {
				return entry;
			}
		}

		// If we have not returned yet, we need to recurse, but if
		// entry.list is null, we cannot so the key is not in the Trie.

		if (entry.list != null)
			return find(key, iKey, entry.list);

		else
			return null;

	}

	public boolean containsKey(Object key) {
		Entry<V> entry = find((String) key, 0, list);
		return entry != null && entry.key != null;
	}

	public V get(Object key) {
		Entry<V> entry = find((String) key, 0, list);
		if (entry != null && entry.key != null)
			return entry.value;
		return null;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private V put(String key, int iKey, V value, List<Entry<V>> list) {
		// EXERCISE:
		int iEntry = 0;
		// Set iEntry to the index of the entry in list such that the first
		// character (charAt(0)) of its sub (NOT key) is the same as the
		// character at index iKey in key.
		// If no such entry, set iEntry to the index where it should be.
		///

		while (iEntry < list.size() && key.charAt(iKey) > list.get(iEntry).sub.charAt(0)) {
			iEntry++;
		}
		
		// If no such entry, create an entry whose sub is the unmatched part
				// of key, key is key, and value is value. Add it at index
				// iEntry. Increment size. Return null.
				
					//trying to put in Goat when there are no G's .. good thing! just add it
					//use the add method of the array list! (already written)
					//no need for a loop
					//increment size and return null 
				///
		
		//if there is no entry like above (not found) --> try to put in Goat when there are no G's .. good thing! just add it

		if (iEntry == list.size() || key.charAt(iKey) < list.get(iEntry).sub.charAt(0)) {
			//iEntry = where should it be if its out of bounds??
		    list.add(iEntry, new Entry<V>(key.substring(iKey),key,value));
		    size++;
		    return null;
		}

		Entry<V> entry = list.get(iEntry);
		int iSub = 0;
		// If we are here, then character iKey of key and iSub of entry.sub
		// must match. Increment both iKey and iSum until that is not true.
		///
		
		do {
			iKey++;
			iSub++;
		} while (iSub < entry.sub.length() && iKey < key.length() && key.charAt(iKey) == entry.sub.charAt(iSub));

		///

		// If we have not matched all the characters of entry.sub, we need
		// to split entry. Create a second entry with everything (key,
		// value, list) the same except sub is the unmatched part of
		// entry.sub. Set entry.sub (of the original entry) to the matched
		// part. Set its key and value to null. Give a new list. Add
		// the second entry to that list.
		
			//hard part :(
			//bob null null
			//we are trying to but in key = bottle
			//we're only going to match 2 (iSub = 2) only 2 letters of bob are matched
			//so bob is no longer a common prefix because we have bot --> 
			//create a sub (b null null) --> node.sub.substring(iSub) = "b" 
			//it should actually be (bo null null) = set the key and the value to null
			//extract the bo from bob by doing not.sub.substring(0, iSub) = "bo"
			//put the new node for bob = create a list "new List" and put "bo" inside that list

		if(iSub < entry.sub.length()) {
			String oldSub = entry.sub.substring(iSub); //this is "b" (end of bob)
			Entry<V> newEntry = new Entry<V>(oldSub, entry.key, entry.value, entry.list); //inheriting the old list of entries
			String newSub = entry.sub.substring(0,iSub); //this is "bo" (beginning of bob)
			List<Entry<V>> newList = new ArrayList<Entry<V>>(); 
			entry.sub = newSub; 
			entry.key = null;
			entry.value = null;
			entry.list = newList;
			entry.list.add(newEntry);
		}
		
		
		///

		// If we have matched all the characters of key, then we will use
		// the current entry. If its key is null, set its key and value,
		// increment size and return null. Otherwise, just return
		// entry.setValue(value).
		///
			//entry.setValue will set the value of bob and the return value is the old value
		
		if(iKey == key.length()) {
			if (entry.key == null){
				entry.key = key;
				entry.value = value;
				size++;
				return null;
			}
			else {
				return entry.setValue(value);
			}
		}
		
		///

		// If we have not returned yet, we need to recurse, but first if
		// entry.list is null, set it to a new list. Then recurse.
		///
			//recurse looks almost the same as the notes --> put returns something so put that into a value
		if (entry.list != null) {
			return put(key, iKey, value, entry.list);
			
		}
		else {
			entry.list = new ArrayList<Entry<V>>(); 
			return put(key,iKey,value,entry.list);
		}
	}

	public V put(String key, V value) {
		return put(key, 0, value, list);
	}

	public V remove(Object keyAsObject) {
		return null;
	}

	private V remove(String key, int iKey, List<Entry<V>> list) {
		return null;
	}

	private Iterator<Map.Entry<String, V>> entryIterator() {
		return new EntryIterator();
	}

	private class EntryIterator implements Iterator<Map.Entry<String, V>> {
		// EXERCISE
		Stack<Iterator<Entry<V>>> stack = new Stack<Iterator<Entry<V>>>();

		EntryIterator() {
			stack.push(list.iterator());
			//push the iterator of the top level list
		}

		public boolean hasNext() {
			// EXERCISE
			// While the iterator at the top of the stack has not next, pop it.
			// Return true if you have not popped all the iterators.
			///	
		    while(stack.isEmpty() == false && !stack.peek().hasNext()) {
		    	stack.pop();
		    }
		    if(!stack.empty()) {
		    	return true;
		    }
		    
			return false;
		}

		public Map.Entry<String, V> next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Entry<V> entry = null;
			// EXERCISE
			// Set entry to the next of the top of the stack.
			// If its list is not null, push its iterator.
			// Repeat those two steps if entry.key is null.
			///
			do {
				entry = stack.peek().next();
				if(entry.list != null) {
					stack.push(entry.list.iterator());
				}
			} while(entry.key == null);

			return entry;
		}

		public void remove() {
		}
	}

	public Set<Map.Entry<String, V>> entrySet() {
		return new EntrySet();
	}

	private class EntrySet extends AbstractSet<Map.Entry<String, V>> {
		public int size() {
			return size;
		}

		public Iterator<Map.Entry<String, V>> iterator() {
			return entryIterator();
		}

		public void remove() {
		}
	}

	public String toString() {
		return toString(list, 0);
	}

	private String toString(List<Entry<V>> list, int indent) {
		String ind = "";
		// Add indent number of spaces to ind:
		for (int i = 0; i < indent; i++) {
			ind += " ";
		}

		String s = "";
		for (Entry<V> entry : list) {
			// Append indented entry (sub, key, and value) and newline ("\n") to s.
			s += ind + entry.sub + " " + entry.key + " " + entry.value + "\n";

			// If there is a sublist, append its toString to s.
			if (entry.list != null) {
				s += toString(entry.list, (entry.sub.length() + indent));
				//add indent on top of entry since its recursive it needs
				//to add on the indent it had before!
			}
		}
		return s;
	}

	void test() {
		Entry<Integer> bob = new Entry<Integer>("bob", null, null);
		list.add((Entry<V>) bob);
		Entry<Integer> by = new Entry<Integer>("by", "bobby", 0);
		bob.list.add(by);
		Entry<Integer> ca = new Entry<Integer>("ca", null, null);
		bob.list.add(ca);
		Entry<Integer> lf = new Entry<Integer>("lf", "bobcalf", 1);
		ca.list.add(lf);
		Entry<Integer> t = new Entry<Integer>("t", "bobcat", 2);
		ca.list.add(t);
		Entry<Integer> catdog = new Entry<Integer>("catdog", "catdog", 3);
		list.add((Entry<V>) catdog);
		size = 4;
	}

	public static void main(String[] args) {
		Trie<Integer> trie = new Trie<Integer>();
		trie.test();
		System.out.println(trie);

		UserInterface ui = new ConsoleUI();

		String[] commands = { "toString", "containsKey", "get", "put", "size", "entrySet", "remove", "quit" };
		String key = null;
		int value = -1;

		while (true) {
			int command = ui.getCommand(commands);
			switch (command) {
			case 0:
				ui.sendMessage(trie.toString());
				break;
			case 1:
				key = ui.getInfo("key: ");
				if (key == null) {
					ui.sendMessage("null key");
					break;
				}
				ui.sendMessage("containsKey(" + key + ") = " + trie.containsKey(key));
				break;
			case 2:
				key = ui.getInfo("key: ");
				if (key == null) {
					ui.sendMessage("null key");
					break;
				}
				ui.sendMessage("get(" + key + ") = " + trie.get(key));
				break;
			case 3:
				key = ui.getInfo("key: ");
				if (key == null) {
					ui.sendMessage("null key");
					break;
				}
				try {
					value = Integer.parseInt(ui.getInfo("value: "));
				} catch (Exception e) {
					ui.sendMessage(value + "not an integer");
					break;
				}
				ui.sendMessage("put(" + key + "," + value + ") = " + trie.put(key, value));
				break;
			case 4:
				ui.sendMessage("size() = " + trie.size());
				break;
			case 5: {
				String s = "";
				for (Map.Entry<String, Integer> entry : trie.entrySet())
					s += entry.getKey() + " " + entry.getValue() + "\n";
				ui.sendMessage(s);
				break;
			}
			case 6:
				break;
			case 7:
				return;
			}
		}
	}
}

package Ex2;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Ex2.*;
public class BandsHashMap implements Map<String, Band> {

	private BandEntry[] table;
	private int size;

	private  int getSize() {
		return size;
	}

	private void setSize(int size) {
		this.size = size;
	}

	private int threshold;
	private double loadFactor;
	private int capacity;

	public BandsHashMap() {
		this(16, 0.75);
	}

	public BandsHashMap(int capacity, double loadFactor) {
		this.capacity = capacity;
		this.loadFactor = loadFactor;
		table = new BandEntry[capacity];
		threshold = (int) (capacity * loadFactor);
		this.size = 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < getSize(); i++) {
			table[i] = null;
		}
		setSize(0);
	}

	@Override
	public boolean containsKey(Object key) {
		for (int i = 0; i < getSize(); i++) {
			if (table[i].getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		for (int i = 0; i < getSize(); i++) {
			if (table[i].getValue().equals((Band) value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<Entry<String, Band>> entrySet() {
		Set<Entry<String, Band>> set = new HashSet<Entry<String, Band>>();
		for (int i = 0; i < getSize(); i++) {
			set.add(table[i]);
		}
		return set;
	}

	@Override
	public Band get(Object key) {
		for (int i = 0; i < getSize(); i++) {
			if (table[i].getKey().equals(key)) {
				return table[i].getValue();
			}
		}
		return null;

	}

	@Override
	public boolean isEmpty() {

		return getSize() == 0;
	}

	@Override
	public Set<String> keySet() {
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < getSize(); i++) {
			set.add(table[i].getKey());
		}
		return set;
	}

	@Override
	public Band put(String key, Band value) {

		if (containsKey(key)) {
			return table[getIndexOfKey(key)].setValue(value);
		} else {
			if (shouldResize()) {
				resize();
			}

			BandEntry entry = new BandEntry(key, value);
			table[getSize()] = entry;
			setSize(getSize() + 1);
		}

		return null;
	}

	private boolean shouldResize() {
		return getSize() >= threshold;
	}

	private void resize() {
		this.capacity = this.capacity * 2;
		BandEntry[] newTable = new BandEntry[this.capacity];
		transfer(newTable);
		this.table = newTable;
		this.threshold = (int) (this.capacity * loadFactor);
	}

	private void transfer(BandEntry[] newTable) {

		System.arraycopy(table, 0, newTable, 0, getSize());

	}

	private int getIndexOfKey(String key) {
		for (int i = 0; i < getSize(); i++) {
			if (table[i].getKey().equals(key))
				return i;
		}

		return -1;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Band> m) {
		for (String currKey : m.keySet()) {
			put(currKey, m.get(currKey));
		}

	}

	@Override
	public Band remove(Object key) {
		if (containsKey(key)) {
			int index = getIndexOfKey((String) key);
			Band oldValue = table[index].getValue();

			if (index != getSize() - 1) {
				table[index] = table[getSize() - 1];
				table[getSize() - 1] = null;
			} else {
				table[index] = null;
			}

			setSize(getSize() - 1);
			return oldValue;
		}

		return null;
	}

	@Override
	public int size() {
		return getSize();
	}

	@Override
	public Collection<Band> values() {
		Collection<Band> values = new BandsArrayList();
		
		for(String currKey : keySet()) {
			values.add(this.get(currKey));
		}
		
		return values;
	}

	private static class BandEntry implements Map.Entry<String, Band> {

		private String key;
		private Band value;

		private BandEntry(String key, Band value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public String getKey() {
			return key;
		}

		@Override
		public Band getValue() {
			return value;
		}

		@Override
		public Band setValue(Band newValue) {
			Band oldValue = this.value;
			this.value = newValue;
			return oldValue;
		}

	}

}

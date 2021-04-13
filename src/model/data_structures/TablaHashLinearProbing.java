package model.data_structures;

public class TablaHashLinearProbing<K extends Comparable<K>, V extends Comparable<V>> implements ITablaSimbolos<K, V> {

	private int maxSize;

	public int getMaxSize() {
		return maxSize;
	}

	private int size;
	private int a;
	private int b;
	private int p;
	ILista<NodoTS<K, V>> elements;
	ILista<K> keys;
	ILista<V> values;

	public TablaHashLinearProbing(int maxSize) {
		elements = new ArregloDinamico<>(maxSize);
		p = nextPrime(maxSize * 7);
		this.maxSize = maxSize;
		this.size = 0;
		a = (int) (Math.random() * (maxSize + 1));
		b = (int) (Math.random() * (maxSize + 1));
	}

	public int hash(K key) {
		int hash = key.hashCode();
		return (Math.abs((a * hash + b) % p) % maxSize);
	}

	static int nextPrime(int N) {
		// Base case
		if (N <= 1)
			return 2;
		int prime = N;
		boolean found = false;
		while (!found) {
			prime++;
			if (isPrime(prime))
				found = true;
		}
		return prime;
	}

	static boolean isPrime(int n) {
		// Corner cases
		if (n <= 1)
			return false;
		if (n <= 3)
			return true;
		if (n % 2 == 0 || n % 3 == 0)
			return false;
		for (int i = 5; i * i <= n; i = i + 6)
			if (n % i == 0 || n % (i + 2) == 0)
				return false;
		return true;

	}

	@Override
	public void put(K key, V value) {
		NodoTS<K, V> newNode = new NodoTS<>(key, value);
		int pos = hash(key);
		NodoTS<K, V> nodo = elements.getElement(pos);
		while (nodo != null && !nodo.isEmpty()) {
			pos = (pos == this.size) ? 0 : pos + 1;
			nodo = elements.getElement(pos);
		}
		elements.insertElement(newNode, pos);
		size++;
	}

	@Override
	public V get(K key) {
		NodoTS<K, V> newNode = new NodoTS<>(key, null);
		int pos = hash(key);
		NodoTS<K, V> nodo = elements.getElement(pos);
		if (nodo == null || nodo.isEmpty()) {
			return null;
		}
		while (nodo.compareTo(newNode) != 0) {
			pos = (pos == this.size) ? 0 : pos + 1;
			nodo = elements.getElement(pos);
			if (nodo == null) {
				return null;
			}
		}
		return nodo.getValue();
	}

	@Override
	public V remove(K key) {
		NodoTS<K, V> newNode = new NodoTS<>(key, null);
		int pos = hash(key);
		NodoTS<K, V> nodo = elements.getElement(pos);
		if (nodo == null || nodo.isEmpty()) {
			return null;
		}
		while (nodo.compareTo(newNode) != 0) {
			pos = (pos == this.size) ? 0 : pos + 1;
			nodo = elements.getElement(pos);
			if (nodo == null) {
				return null;
			}
		}
		NodoTS<K, V> temp = nodo;
		nodo.setEmpty();
		keys.deleteElement(key);
		values.deleteElement(temp.getValue());
		size--;
		return temp.getValue();
	}

	@Override
	public boolean contains(K key) {
		NodoTS<K, V> newNode = new NodoTS<>(key, null);
		int pos = hash(key);
		NodoTS<K, V> nodo = elements.getElement(pos);
		if (nodo == null || nodo.isEmpty()) {
			return false;
		}
		while (nodo.compareTo(newNode) != 0) {
			pos = (pos == this.size) ? 0 : pos + 1;
			nodo = elements.getElement(pos);
			if (nodo == null) {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public ILista<K> keySet() {
		return keys;
	}

	@Override
	public ILista<V> valueSet() {
		return values;
	}
	
	@Override
	public String toString() {
		String res = "";
		for (int i = 0; i < elements.size(); i++) {
			if (elements.getElement(i).toString() != "") {
				res += "" + i + " " + elements.getElement(i);
			}
		}
		return res;
	}

}
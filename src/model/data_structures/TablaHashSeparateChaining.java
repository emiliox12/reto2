package model.data_structures;

import java.util.Comparator;

import model.utils.Ordenamiento;

public class TablaHashSeparateChaining<K extends Comparable<K>, V extends Comparable<V>>
		implements ITablaSimbolos<K, V> {

	private int maxSize;

	public int getMaxSize() {
		return maxSize;
	}

	private int size;
	private int a;
	private int b;
	private int p;
	ILista<ILista<NodoTS<K, V>>> elements;
	ILista<K> keys;
	ILista<V> values;

	public TablaHashSeparateChaining(int maxSize) {
		elements = new ArregloDinamico<>(maxSize);
		keys = new ArregloDinamico<>(maxSize);
		values = new ArregloDinamico<>(maxSize);
		p = nextPrime(maxSize * 5);
		this.maxSize = maxSize;
		this.size = 0;
		a = (int) (Math.random() * (maxSize + 1));
		b = (int) (Math.random() * (maxSize + 1));
		for (int i = 0; i <= maxSize; i++) {
			elements.addLast(new ArregloDinamico<NodoTS<K, V>>(maxSize));
		}
	}

	public int hash(K key) {
		int hash = key.hashCode();
		return (Math.abs((a * hash + b) % p) % maxSize) -1;
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
		ILista<NodoTS<K, V>> subList = elements.getElement(pos);
		if (subList == null) {return;}
		subList.addLast(newNode);
		size++;
	}

	@Override
	public V get(K key) {
		int pos = hash(key);
		ILista<NodoTS<K, V>> subList = elements.getElement(pos);
		if (subList == null) {
			return null;
		}
		NodoTS<K, V> elem = subList.find(new NodoTS<K, V>(key, null));
		return (elem != null) ? elem.getValue() : null;
	}

	@Override
	public V remove(K key) {
		int pos = hash(key);
		ILista<NodoTS<K, V>> subList = elements.getElement(pos);
		NodoTS<K, V> node = subList.deleteElement(new NodoTS<K, V>(key, null));
		keys.deleteElement(key);
		values.deleteElement(node.getValue());
		size--;
		return node.getValue();
	}

	@Override
	public boolean contains(K key) {
		int pos = hash(key);
		ILista<NodoTS<K, V>> subList = elements.getElement(pos);
		return subList.find(new NodoTS<K, V>(key, null)) != null;
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
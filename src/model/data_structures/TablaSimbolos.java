package model.data_structures;

public class TablaSimbolos<K extends Comparable<K>, V extends Comparable<V>> implements ITablaSimbolos<K, V> {

	private int size;
	ILista<NodoTS<K, V>> elements;
	ILista<K> keys;
	ILista<V> values;

	public TablaSimbolos() {
		this.size = 0;
		keys = new ArregloDinamico<K>(10);
		values = new ArregloDinamico<V>(10);
		elements = new ArregloDinamico<NodoTS<K, V>>(10);
	}

	@Override
	public void put(K key, V value) {
		NodoTS<K, V> newNode = new NodoTS<>(key, value);
		keys.addLast(key);
		values.addLast(value);
		elements.addLast(newNode);
		size++;
	}

	@Override
	public V get(K key) {
		return elements.find(new NodoTS<K, V>(key, null)).getValue();
	}

	@Override
	public V remove(K key) {
		NodoTS<K, V> node = elements.deleteElement(new NodoTS<K, V>(key, null));
		keys.deleteElement(node.getKey());
		values.deleteElement(node.getValue());
		size--;
		return node.getValue();
	}

	@Override
	public boolean contains(K key) {
		return elements.find(new NodoTS<K, V>(key, null)) != null;
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
	public int getMaxSize() {
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

}
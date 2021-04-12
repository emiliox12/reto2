package model.data_structures;

public class NodoTS<K extends Comparable<K>, V> implements Comparable<NodoTS<K, V>> {

	private K key; // objeto llave (Comparable)
	private V value; // objeto de informacion asociado


	public NodoTS(K key, V value) {
		this.key = key;
		this.value = value;
	}

	/** La comparación de dos NodoTS depende de sus llaves */
	public int compareTo(NodoTS<K, V> otro) {
		return this.key.compareTo(otro.key);
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public void setEmpty() {
		this.key = null;
		this.value = null;
	}
	
	public boolean isEmpty() {
		return key == null && value == null;
	}
	
	@Override
	public String toString() {
		return "" + key;
	}
}

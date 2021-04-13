package model.data_structures;

import java.util.function.Predicate;

/**
 * 2019-01-23 Estructura de Datos Arreglo Dinamico de T. El arreglo al llenarse
 * (llegar a su maxima capacidad) debe aumentar su capacidad.
 * 
 * @author Fernando De la Rosa
 *
 */
public class ArregloDinamico<T extends Comparable<T>> implements ILista<T>, Comparable<ILista<T>>{
	/**
	 * Capacidad maxima del arreglo
	 */
	private int maxSize;
	/**
	 * Numero de elementos presentes en el arreglo (de forma compacta desde la
	 * posicion 0)
	 */
	private int currentSize;
	/**
	 * Arreglo de elementos de tamaNo maximo
	 */
	private T[] elements;

	/**
	 * Construir un arreglo con la capacidad maxima inicial.
	 * 
	 * @param max Capacidad maxima inicial
	 */
	public ArregloDinamico(int max) {
		maxSize = max;
		elements = (T[]) new Comparable[maxSize];
		currentSize = 0;
	}

	public T buscar(T dato) {
		T rta = null;
		int i = 0;
		while (elements.length > i) {
			if (elements[i].equals(dato)) {
				rta = elements[i];
			}
			i++;
		}
		return rta;
	}

	public T eliminar(T dato) {
		T toEliminate = null;
		boolean eliminated = false;
		for (int i = 0; i < currentSize; i++) {
			if (dato.compareTo(elements[i]) == 0) {
				toEliminate = elements[i];
				elements[i] = (i < maxSize - 2) ? elements[i + 1] : null;
				eliminated = true;
			} else if (eliminated) {
				elements[i] = (i < maxSize - 2) ? elements[i + 1] : null;
			}
		}
		currentSize--;
		return toEliminate;
	}

	public void invertir() {
		T[] nuevoArreglo = (T[]) new Comparable[maxSize];
		for (int i = 0; i < currentSize; i++) {
			nuevoArreglo[i] = elements[maxSize - i - 1];
		}
		elements = nuevoArreglo;
	}

	@Override
	public void addFirst(T element) {
		if (currentSize == maxSize) {
			maxSize = 2 * maxSize;
			T[] copia = elements;
			elements = (T[]) new Comparable[maxSize];
			copia[0] = element;
			for (int i = 1; i < currentSize; i++) {
				elements[i] = copia[i];
			}
		} else {
			T[] copia = elements;
			elements = (T[]) new Comparable[maxSize];
			copia[0] = element;
			for (int i = 1; i < currentSize; i++) {
				elements[i] = copia[i];
			}
		}
		currentSize++;
	}

	@Override
	public void addLast(T element) {
		if (currentSize == maxSize) {
			maxSize = 2 * maxSize;
			T[] copia = elements;
			elements = (T[]) new Comparable[maxSize];
			for (int i = 0; i < currentSize; i++) {
				elements[i] = copia[i];
			}
		}
		elements[currentSize] = element;
		currentSize++;
	}

	@Override
	public void insertElement(T element, int pos) {
		int position = (pos < maxSize) ? pos : maxSize -1;
		elements[position] = element;
	}

	@Override
	public T removeFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T removeLast() {
		T elem = elements[currentSize];
		elements[currentSize] = null;
		currentSize--;
		return elem;
	}

	@Override
	public T deleteElement(T e) {
		int p = isPresent(e);
		return deleteElementPos(p);
	}

	@Override
	public T firstElement() {
		return elements[0];
	}

	@Override
	public T lastElement() {
		return elements[currentSize - 1];
	}

	@Override
	public T getElement(int pos) {
		if (pos >= maxSize || pos < 0) {
			return null;
		} else {
			return elements[pos];
		}
	}

	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public boolean isEmpty() {
		return currentSize == 0;
	}

	@Override
	public int isPresent(T element) {
		for (int i = 0; i < currentSize; i++) {
			if (element.compareTo(elements[i]) == 0) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void exchange(int pos1, int pos2) {
		T temp1 = elements[pos1];
		T temp2 = elements[pos2];
		elements[pos1] = temp2;
		elements[pos2] = temp1;
	}

	@Override
	public void changeInfo(int pos, T elem) {
		elements[pos] = elem;
	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < currentSize; i++) {
			result += " " + elements[i] + "\n";
		}
		return result;
	}

	@Override
	public ArregloDinamico<T> sublista(int numElementos) {
		if (numElementos == 0) {
			return null;
		}
		int elemCount = (numElementos < currentSize) ? numElementos : currentSize;
		ArregloDinamico<T> newArray = new ArregloDinamico<>(numElementos + 20);
		for (int i = 0; i < elemCount; i++) {
			newArray.addLast(elements[i]);
		}
		return newArray;
	}

	@Override
	public ILista<T> subList(int pos, int size) {
		if (size == 0) {
			return null;
		}
		int elemCount = (size - pos < currentSize) ? size : currentSize;
		ArregloDinamico<T> newArray = new ArregloDinamico<>(size + 20);
		for (int i = pos - 1; i < elemCount; i++) {
			newArray.insertElement(elements[i], i);
		}
		return newArray;
	}

	@Override
	public ILista<T> filetr(Predicate<T> p) {
		ArregloDinamico<T> newArray = new ArregloDinamico<>(currentSize);
		for (int i = 0; i < currentSize; i++) {
			if (p.test(elements[i])) {
				newArray.addLast(elements[i]);
			}
		}
		return newArray;
	}

	public void addOrdered(T element) {
		// TODO: Completar añadir organizado
		T[] copia = elements;
		if (currentSize == maxSize) {
			maxSize = 2 * maxSize;
		}
		currentSize++;
		elements = (T[]) new Comparable[maxSize];
		copia[0] = element;
		for (int i = 1; i < currentSize; i++) {
			elements[i] = copia[i];
		}
	}

	@Override
	public T find(T element) {
		for (int i = 0; i < currentSize; i++) {
			if (element.compareTo(elements[i]) == 0) {
				return elements[i];
			}
		}
		return null;

	}

	@Override
	public T deleteElementPos(int pos) {
		T toEliminate = elements[pos];
		boolean eliminated = false;
		for (int i = pos; i < currentSize; i++) {
			elements[i] = (i < maxSize - 2) ? elements[i + 1] : null;
		}
		currentSize--;
		return toEliminate;
	}

	@Override
	public int compareTo(ILista<T> otherList) {
		return this.firstElement().compareTo(otherList.firstElement());
	}
}

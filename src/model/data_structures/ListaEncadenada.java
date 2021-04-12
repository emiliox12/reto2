package model.data_structures;

import java.util.function.Predicate;

public class ListaEncadenada<T extends Comparable<T>> implements ILista<T> {

	private Nodo<T> raiz;
	private Nodo<T> end;
	private int size;
	private boolean ascending;

	public ListaEncadenada() {
		this.size = 0;
	}

	@Override
	public void addFirst(T element) {
		Nodo<T> primeroActual = raiz;
		Nodo<T> newNode = new Nodo<T>(element);
		if (primeroActual == null) {
			raiz = newNode;
			end = newNode;
		}

		else {
			newNode.setNext(primeroActual);
			raiz = newNode;
		}
		size++;
	}

	@Override
	public void addLast(T element) {
		Nodo<T> actual = raiz;
		Nodo<T> newNode = new Nodo<T>(element);
		end = newNode;
		if (raiz == null) {
			raiz = newNode;
			size++;
			return;
		}
		while (actual.getNext() != null) {
			actual = actual.getNext();
		}
		actual.setNext(newNode);
		size++;
	}

	@Override
	public void insertElement(T element, int pos) {
		Nodo<T> nodoActual = raiz.getNext();
		Nodo<T> newNode = new Nodo<T>(element);
		int posit = 0;
		Nodo<T> anterior = raiz;

		if (posit == pos - 1) {
			addFirst(element);
		}
		posit++;
		while (nodoActual.getNext() != null && posit <= pos) {

			if (posit == pos) {
				anterior.setNext(newNode);
				newNode.setNext(nodoActual);
			}
			anterior = anterior.getNext();
			nodoActual = nodoActual.getNext();
			posit++;
		}
		size++;
	}

	@Override
	public T removeFirst() {
		Nodo<T> eliminado = raiz;

		if (raiz.getNext() != null) {
			raiz = raiz.getNext();
		} else {
			raiz = null;
		}
		size--;
		return eliminado.getElement();
	}

	@Override
	public T removeLast() {
		Nodo<T> actual = raiz;
		Nodo<T> rta = null;
		while (actual.getNext() != null) {
			rta = actual;
			if (actual.getNext() == null) {
				actual = null;
			}
			actual = actual.getNext();
		}
		size--;
		return rta.getElement();
	}

	@Override
	public T deleteElementPos(int pos) {
		Nodo<T> actual = raiz.getNext();
		Nodo<T> anterior = raiz;
		Nodo<T> siguiente;
		int posit = 0;
		if (posit == pos - 1) {
			removeFirst();
		}

		while (actual.getNext() != null && posit <= pos) {

			if (posit == pos) {
				siguiente = actual.getNext();
				anterior.setNext(siguiente);
				actual.setNext(null);
			}
			anterior = anterior.getNext();
			actual = actual.getNext();
			posit++;
		}
		size--;
		return actual.getElement();
	}

	@Override
	public T firstElement() {
		return raiz.getElement();
	}

	@Override
	public T lastElement() {
		return end.getElement();
	}

	@Override
	public T getElement(int pos) {
		Nodo<T> actual = raiz;
		int posit = 0;
		if (pos > size) {
			return null;
		}
		while (actual != null) {
			if (posit == pos) {
				return actual.getElement();
			}
			actual = actual.getNext();
			posit++;
		}

		return null;

	}

	@Override
	public int size() {
		Nodo<T> actual = raiz;
		int rta = 0;
		if (raiz != null) {
			while (actual != null) {
				rta++;
				actual = actual.getNext();
			}
		}

		return rta;
	}

	@Override
	public boolean isEmpty() {
		boolean rta = true;
		if (size() > 0) {
			rta = false;
		}
		return rta;
	}

	@Override
	public int isPresent(T element) {
		int cont = 0;
		Nodo<T> actual = raiz;
		if (actual == null) {
			return -1;
		}
		while (actual != null) {
			if (actual.getElement().compareTo(element) == 0) {
				return cont;
			}
			actual = actual.getNext();
			cont++;
		}

		return -1;
	}

	@Override
	public void exchange(int pos1, int pos2) {
		T elem1 = getElement(pos1);
		T elem2 = getElement(pos2);
		changeInfo(pos1, elem2);
		changeInfo(pos2, elem1);
	}

	@Override
	public void changeInfo(int pos, T elem) {
		Nodo<T> actual = raiz;
		int posit = 0;
		while (actual != null) {
			if (posit == pos) {
				actual.setElement(elem);
			}
			actual = actual.getNext();
			posit++;
		}
	}

	@Override
	public String toString() {
		Nodo<T> actual = raiz;
		String result = "";
		while (actual != null) {
			result += " " + actual + "\n";
			actual = actual.getNext();
		}
		return result;
	}

	@Override
	public ListaEncadenada<T> sublista(int numElementos) {
		ListaEncadenada<T> rta = new ListaEncadenada<>();
		if (numElementos == 0 || this.size == 0) {
			return rta;
		}
		Nodo<T> actual = raiz;
		ListaEncadenada<T> newLinkedlist = new ListaEncadenada<>();
		int elemNum = (numElementos >= size) ? size : numElementos;
		while (actual != null && newLinkedlist.size() < elemNum) {
			newLinkedlist.addLast(actual.getElement());
			actual = actual.getNext();
		}
		rta = newLinkedlist;
		return rta;
	}

	@Override
	public ListaEncadenada<T> subList(int pos, int size) {
		ListaEncadenada<T> result = new ListaEncadenada<>();
		int listSize = (size - pos > size()) ? size() : size;
		if (size == 0 || raiz == null) {
			return result;
		}
		Nodo<T> actual = raiz;
		for (int i = 0; i < pos; i++) {
			if (actual.getNext() != null) {
				actual = actual.getNext();
			} else {
				return result;
			}
		}
		for (int i = 0; i < listSize; i++) {
			result.addLast(actual.getElement());
			actual = actual.getNext();
		}
		return result;
	}

	@Override
	public ILista<T> filetr(Predicate<T> p) {
		ListaEncadenada<T> newArray = new ListaEncadenada<>();
		Nodo<T> actual = raiz;
		while (actual != null) {
			if (p.test(actual.getElement())) {
				newArray.addLast(actual.getElement());
			}
			actual = actual.getNext();
		}
		return newArray;
	}

	@Override
	public void addOrdered(T element) {
		Nodo<T> newNode = new Nodo<T>(element);
		int scale = (ascending) ? 1 : -1;
		Nodo<T> actual = raiz;
		if (raiz == null) {
			raiz = end = newNode;
			return;
		}
		if (raiz.getElement().compareTo(element) * scale < 0) {
			newNode.setNext(raiz);
			raiz = newNode;
			return;
		}
		while (actual.getNext() != null) {
			actual = actual.getNext();
			if (actual.getElement().compareTo(element) * scale < 0) {
				newNode.setNext(actual);
				return;
			}
		}
		end.setNext(newNode);
		end = newNode;
	}

	@Override
	public T find(T element) {
		Nodo<T> actual = raiz;
		if (actual == null) {
			return null;
		}
		while (actual != null) {
			if (actual.getElement().compareTo(element) == 0) {
				return actual.getElement();
			}
			actual = actual.getNext();
		}
		return null;
	}

	@Override
	public T deleteElement(T element) {
		Nodo<T> actual = raiz;
		Nodo<T> prev = null;
		if (actual == null) {
			return null;
		} else if (raiz.getElement().compareTo(element) == 0) {
			raiz = raiz.getNext();
		}
		while (actual != null) {
			if (actual.getElement().compareTo(element) == 0) {
				prev.setNext(actual.getNext());
				return actual.getElement();
			}
			prev = actual;
			actual = actual.getNext();
		}
		return null;
	}

	@Override
	public int compareTo(ILista<T> otherList) {
		return this.firstElement().compareTo(otherList.firstElement());
	}
}
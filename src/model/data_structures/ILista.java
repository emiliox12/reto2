package model.data_structures;

import java.util.function.Predicate;

public interface ILista<T extends Comparable<T>> extends Comparable<ILista<T>> {

	/**
	 * Agrega un elemento al inicio de la lista
	 * 
	 * @param element
	 */
	void addFirst(T element);

	/**
	 * Agrega un elemento al final de la lista
	 */
	void addLast(T element);

	/**
	 * Agrega un elemento en la posición pos si la posición es una posición válida.
	 * Los elementos que estén a partir de la posición dada deben correrse una
	 * posición a la derecha. Las posiciones válidas son posiciones donde ya hay un
	 * elemento en la lista. La posición del primer elemento es 1, la del segundo es
	 * 2 y así sucesivamente.
	 * 
	 * @param element
	 * @param pos
	 */
	void insertElement(T element, int pos);

	/*
	 * * Elimina el primer elemento. Se retorna el elemento eliminado.
	 * 
	 * @returnel elemento eliminado
	 */
	T removeFirst();

	/*
	 * Elimina el último elemento. Se retorna el elemento eliminado.
	 * 
	 * @return el elemento eliminado
	 */
	T removeLast();

	/*
	 * Elimina el elemento de una posición válida. Se retorna el elemento eliminado.
	 * 
	 * @param pos
	 * 
	 * @return el elemento eliminado
	 */
	T deleteElementPos(int pos);

	/*
	 * Elimina el elemento de una posición válida. Se retorna el elemento eliminado.
	 * 
	 * @param pos
	 * 
	 * @return el elemento eliminado
	 */
	T deleteElement(T element);

	/*
	 * Retorna el primer elemento
	 * 
	 * @return
	 */
	T firstElement();

	/*
	 * Retorna el último elemento
	 * 
	 * @return el último elemento
	 */
	T lastElement();

	/*
	 * Retorna el elemento en una posición válida. La posición del primer elemento
	 * es 1, la del segundo es 2 y así sucesivamente.
	 * 
	 * @param pos
	 * 
	 * @return el elemento en una posición válida
	 */
	T getElement(int pos);

	/*
	 * Retorna el número de datos en el arreglo
	 * 
	 * @return
	 */
	int size();

	/*
	 * Retorna true si el arreglo No tiene datos. false en caso contrario.
	 * 
	 * @return Boolean
	 */
	boolean isEmpty();

	/*
	 * Retorna la posición válida de un elemento. Si no se encuentra el elemento, el
	 * valor retornado es -1.
	 * 
	 * @param element
	 * 
	 * @return la posición válida de un elemento. Si no se encuentra el elemento, el
	 * valor retornado es -1.
	 */
	int isPresent(T element);

	/*
	 * Retorna la posición válida de un elemento. Si no se encuentra el elemento, el
	 * valor retornado es -1.
	 * 
	 * @param element
	 * 
	 * @return la posición válida de un elemento. Si no se encuentra el elemento, el
	 * valor retornado es -1.
	 */
	T find(T element);

	/*
	 * Intercambia la información de los elementos en dos posiciones válidas.
	 * 
	 * @param pos1
	 * 
	 * @param pos2
	 */
	void exchange(int pos1, int pos2);

	/*
	 * Actualiza el elemento en una posición válida.
	 * 
	 * @param pos
	 * 
	 * @param elem
	 */
	void changeInfo(int pos, T elem);

	/**
	 * Crear una sublista de la lista original (this). Los elementos se toman en el
	 * mismo orden como aparecen en la lista original (this).
	 * 
	 * @param número de elementos que contendrá la sublista. Si el número es
	 *               superior al tamaño original de la lista, se obtiene una copia
	 *               de la lista original.
	 * @return sublista creada con la misma representación de la lista original
	 *         (this).
	 */
	public ILista<T> sublista(int numElementos);

	/**
	 * Retorna una sublista con los elementos desde pos hasta la cantidad
	 * solicitada.
	 * 
	 * @param pos  Posición desde la que se quiere obtener la sublista.
	 * @param size Tamaño de la sublista.
	 * @return Una nueva lista con los elementos solicitados.
	 */
	public ILista<T> subList(int pos, int size);

	public ILista<T> filetr(Predicate<T> p);

	public void addOrdered(T element);
}

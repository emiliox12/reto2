package model.utils;

import java.util.Comparator;

import model.data_structures.ILista;

public class Ordenamiento<T extends Comparable<T>> {

	public void ordenarInsercion(ILista<T> lista, Comparator<T> criterio, boolean ascendente) {
		int n = lista.size();
		int scale = ascendente ? 1 : -1;
		for (int i = 0; i <= n; i++) {
			boolean flag = false;
			for (int j = i + 1; j > 0 && !flag; j -= 1) {
				T elem1 = lista.getElement(j);
				T elem2 = lista.getElement(j - 1);
				if (elem1 != null && elem2 != null) {
					int factorComparacion = scale * criterio.compare(elem1, elem2);
					if (factorComparacion < 0) {
						lista.exchange(j, j - 1);
					} else
						flag = true;
				}
			}
		}
	}

	public void ordenarShell(ILista<T> lista, Comparator<T> criterio, boolean ascendente) {
		int n = lista.size();
		int h = 1;
		while (h < n / 3)
			h = h * 3 + 1;
		int scale = (ascendente) ? 1 : -1;
		while (h >= 1) {
			for (int i = h + 1; i < n; i++) {
				boolean flag = false;
				for (int j = i; j > h && !flag; j -= h) {
					T elem = lista.getElement(j);
					T elem2 = lista.getElement(j - h);
					if (criterio.compare(elem, elem2) * scale < 0) {
						lista.exchange(i, j);
					} else {
						flag = true;
					}
				}
			}
			h /= 3;
		}
	}

	public void ordenarMergeSort(ILista<T> lista, Comparator<T> criterio, boolean ascendente) {
		int size = lista.size();
		if (size > 1) {
			int mitad = size / 2;
			ILista<T> listaIzqui = lista.subList(1, mitad);
			ILista<T> listaDerech = lista.subList(mitad + 1, size - mitad);

			ordenarMergeSort(listaIzqui, criterio, ascendente);
			ordenarMergeSort(listaDerech, criterio, ascendente);

			int i, j, k;
			i = j = k = 1;

			int leftelements = listaIzqui.size();
			int rightelements = listaDerech.size();

			while (i <= leftelements && j <= rightelements) {
				T elemi = listaIzqui.getElement(i);
				T elemj = listaDerech.getElement(j);
				int factorComparacion = (ascendente ? 1 : -1) * criterio.compare(elemi, elemj);
				if (factorComparacion <= 0) {
					lista.changeInfo(k, elemi);
					i++;
				} else {
					lista.changeInfo(k, elemj);
					j++;
				}
				k++;
			}
			while (i <= leftelements) {
				lista.changeInfo(k, listaIzqui.getElement(i));
				i++;
				k++;
			}
			while (j <= rightelements) {
				lista.changeInfo(k, listaDerech.getElement(j));
				j++;
				k++;
			}
		}
	}

	/**
	 * Método que va dejando el pivot en su lugar, mientras mueve elementos menores
	 * a la izquierda del pivot y elementos mayores a la derecha del pivot.
	 */
	private final int partition(ILista<T> lista, Comparator<T> criterio, boolean ascendente, int lo, int hi) {
		int follower, leader;
		follower = leader = lo;
		while (leader <= hi) {
			T elem1 = lista.getElement(leader);
			T elem2 = lista.getElement(hi);
			if (elem1 != null && elem2 != null) {

				int factorComparacion = (ascendente ? 1 : -1)
						* criterio.compare(elem1, elem2);
				if (factorComparacion < 0) {
					lista.exchange(follower, leader);
					follower++;
				}
			}
			leader++;
		}
		lista.exchange(follower, hi);
		return follower;
	}

	/**
	 * Se localiza el pivot, utilizando el método de partición. Luego se hace la
	 * recursión con los elementos a la izquierda del pivot y los elementos a la
	 * derecha del pivot.
	 */
	private final void ordenarQuicksort(ILista<T> lista, Comparator<T> criterio, boolean ascendente, int lo, int hi) {
		if (lo >= hi)
			return;
		int pivot = partition(lista, criterio, ascendente, lo, hi);
		ordenarQuicksort(lista, criterio, ascendente, lo, pivot - 1);
		ordenarQuicksort(lista, criterio, ascendente, pivot + 1, hi);
	}

	/**
	 * Método de entrada, lanza el quick sort recursivo.
	 */
	public final void quickSort(ILista<T> lista, Comparator<T> criterio, boolean ascendente) {
		ordenarQuicksort(lista, criterio, ascendente, 0, lista.size() - 1);
	}
}

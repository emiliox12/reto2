package model.data_structures;

public interface ITablaSimbolos<K extends Comparable<K>, V extends Comparable<V>> {
	/** Agregar una dupla (K, V) a la tabla. Si la llave K existe, se reemplaza su valor V
	*asociado. V no puede ser null. En este caso una llave K solo tiene asociado un
	*valor V.
	**/
	void put(K key, V value);
	/**
	*Obtener el valor V asociado a la llave K. Se obtiene nullsolo si la llave K no existe.
	*Se usa el comparador sobre las llaves para saber si existe.
	**/
	V get(K key);

	/**
	*Borrar la dupla asociada a la llave K. Se obtiene el valor V asociado a la llave K.
	*Se obtiene null solo si la llave K no existe. 
	*Método Descripción
	**/
	V remove(K key);

	/**
	*Retorna true en el caso que la llave K se encuentre almacenada en la Tabla, o
	*false en el caso contrario
	**/
	boolean contains (K key);

	/**
	*Retorna true si la Tabla NO tiene datos, o false en caso contrario.
	**/
	boolean isEmpty ( );

	/**
	*Retorna el número de duplas en la Tabla de Símbolos
	**/
	int size ( );

	/**
	*Retorna todas las llaves almacenadas en la Tabla.
	**/
	ILista<K> keySet();

	/**
	*Retorna todos los valores almacenados en la Tabla
	**/
	ILista<V> valueSet();
	
	int getMaxSize();

}

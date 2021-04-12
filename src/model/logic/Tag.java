package model.logic;

import java.util.Comparator;

import model.data_structures.ILista;
import model.data_structures.ListaEncadenada;
import model.utils.Ordenamiento;

public class Tag implements Comparable<Tag> {
	private String name;
	private ILista<YoutubeVideo> videosbyLike;
	private Ordenamiento<YoutubeVideo> sorter;

	public Tag(String name) {
		this.name = name;
		videosbyLike = new ListaEncadenada<YoutubeVideo>();
		sorter = new Ordenamiento<YoutubeVideo>();
	}

	public void orderLists() {
		Comparator<YoutubeVideo> comparatorLikes = new YoutubeVideo.ComparadorXLikes();
		sorter.quickSort(videosbyLike, comparatorLikes, false);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ILista<YoutubeVideo> getVideos() {
		return videosbyLike;
	}

	public void addToLists(YoutubeVideo yt) {
		videosbyLike.addLast(yt);
	}

	@Override
	public int compareTo(Tag other) {
		return name.compareToIgnoreCase(other.getName());
	}

	@Override
	public String toString() {
		return name + videosbyLike;
	}
}

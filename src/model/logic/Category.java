package model.logic;

import java.util.Comparator;

import model.data_structures.ILista;
import model.data_structures.ListaEncadenada;
import model.utils.Ordenamiento;

public class Category implements Comparable<Category> {
	private int id;
	private String name;
	private ILista<YoutubeVideo> videosByViews;
	private ILista<YoutubeVideo> videosbyTrending;
	private Ordenamiento<YoutubeVideo> sorter;

	public Category(String id, String name) {
		this.name = name;
		this.id = Integer.parseInt(id);
		videosByViews = new ListaEncadenada<YoutubeVideo>();
		videosbyTrending = new ListaEncadenada<YoutubeVideo>();
		sorter = new Ordenamiento<YoutubeVideo>();
	}

	public void orderLists() {
		Comparator<YoutubeVideo> comparatorViews = new YoutubeVideo.ComparadorXViews();
		Comparator<YoutubeVideo> comparatorTrending = new YoutubeVideo.ComparadorXTrending();
		sorter.quickSort(videosByViews, comparatorViews, false);
		sorter.quickSort(videosbyTrending, comparatorTrending, false);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public ILista<YoutubeVideo> getVideosViews() {
		return videosByViews;
	}

	public ILista<YoutubeVideo> getVideosTrending() {
		return videosbyTrending;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Category other) {
		if (id > other.getId())
			return 1;
		else if (id < other.getId())
			return -1;
		else
			return 0;
	}

	public void addToLists(YoutubeVideo yt) {
		videosByViews.addLast(yt);
		videosbyTrending.addLast(yt);
	}

	@Override
	public String toString() {
		return "Category: " + id + name + videosByViews;
	}
}

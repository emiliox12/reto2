package model.logic;

import java.util.Comparator;

import model.data_structures.ILista;
import model.data_structures.ListaEncadenada;
import model.utils.Ordenamiento;

public class Country implements Comparable<Country> {
	private String name;
	private ILista<YoutubeVideo> videosbyLike;
	private ILista<YoutubeVideo> videosbyTrending;
	private ILista<Category> categories;
	private ILista<Tag> tags;
	private Ordenamiento<YoutubeVideo> sorter;

	public Country(String name, String videoTags) {
		this.name = name;
		videosbyLike = new ListaEncadenada<YoutubeVideo>();
		videosbyTrending = new ListaEncadenada<YoutubeVideo>();
		tags = new ListaEncadenada<Tag>();
		sorter = new Ordenamiento<YoutubeVideo>();
		categories = new ListaEncadenada<Category>();
	}

	public void orderLists() {
		Comparator<YoutubeVideo> comparatorLikes = new YoutubeVideo.ComparadorXLikes();
		Comparator<YoutubeVideo> comparatorTrending = new YoutubeVideo.ComparadorXTrending();
		sorter.quickSort(videosbyLike, comparatorLikes, false);
		sorter.quickSort(videosbyTrending, comparatorTrending, false);
		for (int i = 0; i < tags.size(); i++) {
			tags.getElement(i).orderLists();
		}
		for (int i = 0; i < categories.size(); i++) {
			categories.getElement(i).orderLists();
		}
	}

	public String getName() {
		return name;
	}

	public ILista<YoutubeVideo> getTag(String tag) {
		int pos = tags.isPresent(new Tag(tag));
		return tags.getElement(pos).getVideos();
	}

	public void addToLists(YoutubeVideo yt) {
		videosbyLike.addLast(yt);
		videosbyTrending.addLast(yt);
		Category newCategory = new Category("" + yt.getCategory_id(), "");
		int categoryPos = categories.isPresent(newCategory);
		Category categoryObj = categories.getElement(categoryPos);
		if (categoryObj == null) {
			categories.addLast(newCategory);
			categoryObj = newCategory;
		}
		categoryObj.addToLists(yt);
		String[] tagsArray = yt.getTags();
		for (String tag : tagsArray) {
			Tag newTag = new Tag(tag);
			int tagPos = tags.isPresent(newTag);
			Tag tagObj = tags.getElement(tagPos);
			if (tagObj == null) {
				tags.addLast(newTag);
				tagObj = newTag;
			}
			tagObj.addToLists(yt);
		}
	}

	@Override
	public int compareTo(Country other) {
		return name.compareToIgnoreCase(other.getName());
	}

	@Override
	public String toString() {
		return name + "\n" + videosbyLike;
	}

	public ILista<YoutubeVideo> getCategoryViews(int categoryId) {
		int pos = categories.isPresent(new Category("" + categoryId, ""));
		if (pos < 0) return new ListaEncadenada<YoutubeVideo>();
		return categories.getElement(pos).getVideosViews();
	}

	public ILista<YoutubeVideo> getTagViews(String tag) {
		int pos = tags.isPresent(new Tag(tag));
		if (pos < 0) return new ListaEncadenada<YoutubeVideo>();
		return tags.getElement(pos).getVideos();
	}

	public ILista<YoutubeVideo> getVideosbyTrending() {
		return videosbyTrending;
	}

	public String getCategories() {
		return categories.toString();
	}
}

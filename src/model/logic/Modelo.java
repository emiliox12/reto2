package model.logic;

import java.io.FileReader;
import java.io.Reader;
import java.util.Comparator;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import model.data_structures.ArregloDinamico;
import model.data_structures.ILista;
import model.data_structures.ITablaSimbolos;
import model.data_structures.ListaEncadenada;
import model.data_structures.NodoTS;
import model.data_structures.TablaHashLinearProbing;
import model.data_structures.TablaHashSeparateChaining;
import model.utils.Ordenamiento;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {
	/**
	 * Atributos del modelo del mundo
	 */
	private ITablaSimbolos<String, ILista<YoutubeVideo>> videoByCategoryCountry;
	private ITablaSimbolos<String, ILista<YoutubeVideo>> videoByCountry;
	private ITablaSimbolos<String, ILista<YoutubeVideo>> videoByCategory;
	private ITablaSimbolos<String, ILista<YoutubeVideo>> videoByTag;
	private ILista<Category> categories;
	private ILista<String> validKeys;
	private Ordenamiento<YoutubeVideo> sorter;

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Modelo() {
		validKeys = new ArregloDinamico<>(20);
	  	videoByCategoryCountry = new TablaHashSeparateChaining<>(400);
		videoByCountry = new TablaHashLinearProbing<>(400);
	  	videoByCategory = new TablaHashSeparateChaining<>(400);
		videoByTag = new TablaHashSeparateChaining<>(25000);
		categories = new ListaEncadenada<Category>();
		sorter = new Ordenamiento<YoutubeVideo>();
		// datos = new ListaEncadenada<YoutubeVideo>();
		cargar();
	}

	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo
	 * 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamano() {
		return videoByCategoryCountry.size();
	}

	public void cargar() {
		System.out.println("Start upload");
		int count = 0;
		Reader in;
		long start = System.currentTimeMillis();
		try {
			in = new FileReader("./data/category-id.csv");
			Iterable<CSVRecord> categoriesCsv = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : categoriesCsv) {
				String id = record.get(0);
				String name = record.get(1);
				Category category = new Category(id, name);
				categories.addLast(category);
			}
			in = new FileReader("./data/videos-small.csv");
			Iterable<CSVRecord> videosCsv = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : videosCsv) {
				String trending_date = record.get(1);
				String video_id = record.get("video_id");
				String title = record.get(2);
				String channel_title = record.get(3);
				String category_id = record.get(4);
				String publish_time = record.get(5);
				String videoTags = record.get(6);
				String views = record.get(7);
				String likes = record.get(8);
				String dislikes = record.get(9);
				String comment_count = record.get(10);
				String thumbnail_link = record.get(11);
				String comments_disabled = record.get(12);
				String ratings_disabled = record.get(13);
				String video_error_or_removed = record.get(14);
				String descriptio = record.get(15);
				String country = record.get(16);
				YoutubeVideo video = new YoutubeVideo(video_id, trending_date, title, channel_title, category_id,
						publish_time, videoTags, views, likes, dislikes, comment_count, thumbnail_link,
						comments_disabled, ratings_disabled, video_error_or_removed, descriptio, country);
				Category category = categories.find(new Category("" + video.getCategory_id(), ""));
				String key = country.trim().toUpperCase() + category.getName().trim().toUpperCase();
				if (validKeys.find(key) == null) {
					validKeys.addLast(key);
				}
				ILista<YoutubeVideo> videoList = videoByCategoryCountry.get(key);
				if (videoList == null) {
					videoList = new ArregloDinamico<YoutubeVideo>(100);
					videoByCategoryCountry.put(key, videoList);
				}
				ILista<YoutubeVideo> videoCountryList = videoByCountry.get(country.trim().toUpperCase());
				if (videoCountryList == null) {
					videoCountryList = new ArregloDinamico<YoutubeVideo>(100);
					videoByCountry.put(country.trim().toUpperCase(), videoCountryList);
				}
				ILista<YoutubeVideo> videoCategryList = videoByCategory.get(category.getName().trim().toUpperCase());
				if (videoCategryList == null) {
					videoCategryList = new ArregloDinamico<YoutubeVideo>(100);
					videoByCategory.put(category.getName().trim().toUpperCase(), videoCategryList);
				}
				String[] tagsArray = video.getTags();
				for (String tag : tagsArray) {
					ILista<YoutubeVideo> videoTagList = videoByTag.get(tag.trim().toUpperCase());
					if (videoTagList == null) {
						videoTagList = new ArregloDinamico<YoutubeVideo>(100);
						videoByTag.put(tag.trim().toUpperCase(), videoTagList);
						count++;
					}
					videoList.addLast(video);
				}
				videoList.addLast(video);
				videoCountryList.addLast(video);
				videoCategryList.addLast(video);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("Creación: " + (end - start) + " #: " + count);
		double cargaVideosChaining = Double.valueOf(videoByCategoryCountry.size()) / Double.valueOf(videoByCategoryCountry.getMaxSize());
		System.out.println("Separate Chaining: \n" + "size: " + videoByCategoryCountry.getMaxSize() + " Duplas: "
				+ videoByCategoryCountry.size() + " (N/M): " + cargaVideosChaining);
		// System.out.println(videosChaining.toString());
	}

	public String req1(String category_name, String country, int n) {
		ILista<YoutubeVideo> list = videoByCategoryCountry
				.get(country.trim().toUpperCase() + category_name.trim().toUpperCase());
		if (list == null) {
			return null;
		}
		Comparator<YoutubeVideo> criterio = new YoutubeVideo.ComparadorXLikes();
		sorter.quickSort(list, criterio, false);
		ILista<YoutubeVideo> resList = list.sublista(n);
		String res = "trending_date" + "\t - \t" + "title" + "\t - \t" + "channel_title" + "\t - \t" + "publish_time"
				+ "\t - \t" + "views" + "\t - \t" + "likes" + "\t - \t" + "dislikes" + "\n";
		for (int i = 0; i < resList.size(); i++) {
			YoutubeVideo yt = resList.getElement(i);
			res += yt.getTrending_date().toString() + "\t" + yt.getTitle() + "\t" + yt.getChannel_title() + "\t"
					+ yt.getPublish_time() + "\t" + yt.getViews() + "\t" + yt.getLikes() + "\t" + yt.getDislikes()
					+ "\n";
		}
		return res;
	}

	public String req2(String country) {
		ILista<YoutubeVideo> list = videoByCountry.get(country.trim().toUpperCase());
		if (list == null) {
			return null;
		}
		Comparator<YoutubeVideo> criterio = new YoutubeVideo.ComparadorXTrending();
		sorter.quickSort(list, criterio, false);
		ILista<YoutubeVideo> resList = list.sublista(1);
		String res = "title" + "\t - \t" + "channel_title" + "\t - \t" + "category_id" + "\t - \t" + "Días" + "\n";
		for (int i = 0; i < resList.size(); i++) {
			YoutubeVideo yt = resList.getElement(i);
			res += yt.getTitle() + "\t" + yt.getChannel_title() + "\t" + yt.getCategory_id() + "\t"
					+ yt.getTrendingDays() + "\n";
		}
		return res;
	}

	public String req3(String category_name) {
		ILista<YoutubeVideo> list = videoByCategory.get(category_name.trim().toUpperCase());
		if (list == null) {
			return null;
		}
		System.out.println(list.size());
		Comparator<YoutubeVideo> criterio = new YoutubeVideo.ComparadorXTrending();
		sorter.quickSort(list, criterio, false);
		ILista<YoutubeVideo> resList = list.sublista(1);
		String res = "title" + "\t - \t" + "channel_title" + "\t - \t" + "category_id" + "\t - \t" + "Días" + "\n";
		for (int i = 0; i < resList.size(); i++) {
			YoutubeVideo yt = resList.getElement(i);
			res += yt.getTitle() + "\t\t" + yt.getChannel_title() + "\t" + yt.getCategory_id() + "\t"
					+ yt.getTrendingDays() + "\n";
		}
		return res;
	}

	public String req4(String tag, int n) {
		ILista<YoutubeVideo> list = videoByTag.get(tag.trim().toUpperCase());
		System.out.println("list: " + list);
		if (list == null) {
			return null;
		}
		Comparator<YoutubeVideo> criterio = new YoutubeVideo.ComparadorXLikes();
		sorter.quickSort(list, criterio, false);
		ILista<YoutubeVideo> resList = list.sublista(n);
		String res = "title" + "\t - \t" + "channel_title" + "\t - \t" + "publish_time" + "\t - \t" + "views"
				+ "\t - \t" + "likes" + "\t - \t" + "dislikes" + "\n";
		for (int i = 0; i < resList.size(); i++) {
			YoutubeVideo yt = resList.getElement(i);
			res += yt.getTitle() + "\t" + yt.getChannel_title() + "\t" + yt.getPublish_time() + "\t" + yt.getViews()
					+ "\t" + yt.getLikes() + "\t" + yt.getDislikes() + "\t" + yt.getTagsString() + "\n";
		}
		return res;
	}

	@Override
	public String toString() {
		return videoByCountry.toString();
	}
}

package model.logic;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class YoutubeVideo implements Comparable<YoutubeVideo> {

	private String video_id;
	private LocalDate trending_date;
	private String title;
	private String channel_title;
	private int category_id;
	private LocalDate publish_time;
	private String[] tags;
	private String tagsString;
	private int views;
	private int likes;
	private int dislikes;
	private int comment_count;
	private String thumbnail_link;
	private boolean comments_disabled;
	private boolean ratings_disabled;
	private boolean video_error_or_removed;
	private String descriptio;
	private String country;
	private long trendingDays;

	public YoutubeVideo(String video_id, String trending_date, String title, String channel_title, String category_id,
			String publish_time, String tags, String views, String likes, String dislikes, String comment_count,
			String thumbnail_link, String comments_disabled, String ratings_disabled, String video_error_or_removed,
			String descriptio, String country) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy.dd.MM");
		String publishDate = publish_time.split("T")[0];
		this.video_id = video_id;
		this.trending_date = LocalDate.parse(trending_date, dtf);
		this.title = title;
		this.channel_title = channel_title;
		this.category_id = Integer.parseInt(category_id);
		this.publish_time = LocalDate.parse(publishDate);
		this.tags = tags.split("\\|");
		this.tagsString = tags;
		this.views = Integer.parseInt(views);
		this.likes = Integer.parseInt(likes);
		this.dislikes = Integer.parseInt(dislikes);
		this.comment_count = Integer.parseInt(comment_count);
		this.thumbnail_link = thumbnail_link;
		this.comments_disabled = Boolean.parseBoolean(comments_disabled);
		this.ratings_disabled = Boolean.parseBoolean(ratings_disabled);
		this.video_error_or_removed = Boolean.parseBoolean(video_error_or_removed);
		this.descriptio = descriptio;
		this.country = country;
		this.trendingDays = Duration.between(this.publish_time.atStartOfDay(), this.trending_date.atStartOfDay())
				.toDays();
		for (int i = 0; i < this.tags.length; i++) {
			this.tags[i] = this.tags[i].replaceAll("^\"+|\"+$", "");
		}
	}

	public long getTrendingDays() {
		return trendingDays;
	}

	public void setTrendingDays(long trendingDays) {
		this.trendingDays = trendingDays;
	}
	
	public String getTagsString() {
		return this.tagsString;
	}
	@Override
	public int compareTo(YoutubeVideo video) {
		return this.trending_date.compareTo(video.trending_date);
	}

	public static class ComparadorXLikes implements Comparator<YoutubeVideo> {
		public int compare(YoutubeVideo video1, YoutubeVideo video2) {
			if (video1.getLikes() > video2.getLikes())
				return 1;
			else if (video1.likes < video2.likes)
				return -1;
			else
				return 0;
		}
	}

	public static class ComparadorXViews implements Comparator<YoutubeVideo> {
		public int compare(YoutubeVideo video1, YoutubeVideo video2) {
			if (video1.getViews() > video2.getViews())
				return 1;
			else if (video1.getViews() < video2.getViews())
				return -1;
			else
				return 0;
		}
	}

	public static class ComparadorXTrending implements Comparator<YoutubeVideo> {
		public int compare(YoutubeVideo video1, YoutubeVideo video2) {
			if (video1.getTrendingDays() > video2.getTrendingDays())
				return 1;
			else if (video1.getTrendingDays() < video2.getTrendingDays())
				return -1;
			else
				return 0;
		}
	}

	public String getVideo_id() {
		return video_id;
	}

	public void setVideo_id(String video_id) {
		this.video_id = video_id;
	}

	public LocalDate getTrending_date() {
		return trending_date;
	}

	public void setTrending_date(LocalDate trending_date) {
		this.trending_date = trending_date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getChannel_title() {
		return channel_title;
	}

	public void setChannel_title(String channel_title) {
		this.channel_title = channel_title;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public LocalDate getPublish_time() {
		return publish_time;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public int getComment_count() {
		return comment_count;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}

	public String getThumbnail_link() {
		return thumbnail_link;
	}

	public void setThumbnail_link(String thumbnail_link) {
		this.thumbnail_link = thumbnail_link;
	}

	public boolean isComments_disabled() {
		return comments_disabled;
	}

	public void setComments_disabled(boolean comments_disabled) {
		this.comments_disabled = comments_disabled;
	}

	public boolean isRatings_disabled() {
		return ratings_disabled;
	}

	public void setRatings_disabled(boolean ratings_disabled) {
		this.ratings_disabled = ratings_disabled;
	}

	public boolean isVideo_error_or_removed() {
		return video_error_or_removed;
	}

	public void setVideo_error_or_removed(boolean video_error_or_removed) {
		this.video_error_or_removed = video_error_or_removed;
	}

	public String getDescriptio() {
		return descriptio;
	}

	public void setDescriptio(String descriptio) {
		this.descriptio = descriptio;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return views +  " / " + trendingDays + " / " + likes + " / " + title;
		// return trending_date + " / " + likes + " / " + title + " / " + channel_title
		// + " / " + publish_time + " / "
		// + views + " / " + dislikes;
	}
}

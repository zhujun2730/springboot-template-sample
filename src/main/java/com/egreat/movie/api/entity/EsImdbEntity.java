package com.egreat.movie.api.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 文章
 */
@Document(indexName = "movie", type = "imdb")
@Data
public class EsImdbEntity {

    public EsImdbEntity(){

    }


    @Id
    private String id;

    private int languageType;
    private String cover;
    private String title;
    private int type;
    private String url;
    private String actor;
    private String author;
    private String cover_local;
    private long create_time;
    private String date_published;
    private String director;
    private Object episode;
    private Object first_broadcast;
    private String genre;
    private String imdb_id;
    private String introduction;
    private String language;
    private String length;
    private String m_production_country;
    private String other_name;
    private String rating_human;
    private String rating_num;
    private Object season_count;
    private Object single_length;
    private String staffs;
    private String stills;
    private Object update_time;
    private String years;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLanguageType() {
        return languageType;
    }

    public void setLanguageType(int languageType) {
        this.languageType = languageType;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover_local() {
        return cover_local;
    }

    public void setCover_local(String cover_local) {
        this.cover_local = cover_local;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getDate_published() {
        return date_published;
    }

    public void setDate_published(String date_published) {
        this.date_published = date_published;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Object getEpisode() {
        return episode;
    }

    public void setEpisode(Object episode) {
        this.episode = episode;
    }

    public Object getFirst_broadcast() {
        return first_broadcast;
    }

    public void setFirst_broadcast(Object first_broadcast) {
        this.first_broadcast = first_broadcast;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getM_production_country() {
        return m_production_country;
    }

    public void setM_production_country(String m_production_country) {
        this.m_production_country = m_production_country;
    }

    public String getOther_name() {
        return other_name;
    }

    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    public String getRating_human() {
        return rating_human;
    }

    public void setRating_human(String rating_human) {
        this.rating_human = rating_human;
    }

    public String getRating_num() {
        return rating_num;
    }

    public void setRating_num(String rating_num) {
        this.rating_num = rating_num;
    }

    public Object getSeason_count() {
        return season_count;
    }

    public void setSeason_count(Object season_count) {
        this.season_count = season_count;
    }

    public Object getSingle_length() {
        return single_length;
    }

    public void setSingle_length(Object single_length) {
        this.single_length = single_length;
    }

    public String getStaffs() {
        return staffs;
    }

    public void setStaffs(String staffs) {
        this.staffs = staffs;
    }

    public String getStills() {
        return stills;
    }

    public void setStills(String stills) {
        this.stills = stills;
    }

    public Object getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Object update_time) {
        this.update_time = update_time;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }
}

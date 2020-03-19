package com.egreat.movie.api.entity;

import lombok.Data;

@Data
public class TmdbEntity {

    //tmdb info
    protected String tmdb_name;
    protected String tmdb_id;
    protected String tmdb_bg;
    protected String tmdb_poster;
    protected String tmdb_collection_name;
    protected String tmdb_collection_bg;
    protected String tmdb_collection_poster;
    protected String trailer_url;
    // 0：未初始化，1：已经初始化，2：没有tmdb数据
    protected int have_tmdb_data;

    public String getTmdb_name() {
        return tmdb_name;
    }

    public void setTmdb_name(String tmdb_name) {
        this.tmdb_name = tmdb_name;
    }

    public String getTmdb_id() {
        return tmdb_id;
    }

    public void setTmdb_id(String tmdb_id) {
        this.tmdb_id = tmdb_id;
    }

    public String getTmdb_bg() {
        return tmdb_bg;
    }

    public void setTmdb_bg(String tmdb_bg) {
        this.tmdb_bg = tmdb_bg;
    }

    public String getTmdb_poster() {
        return tmdb_poster;
    }

    public void setTmdb_poster(String tmdb_poster) {
        this.tmdb_poster = tmdb_poster;
    }

    public String getTmdb_collection_name() {
        return tmdb_collection_name;
    }

    public void setTmdb_collection_name(String tmdb_collection_name) {
        this.tmdb_collection_name = tmdb_collection_name;
    }

    public String getTmdb_collection_bg() {
        return tmdb_collection_bg;
    }

    public void setTmdb_collection_bg(String tmdb_collection_bg) {
        this.tmdb_collection_bg = tmdb_collection_bg;
    }

    public String getTmdb_collection_poster() {
        return tmdb_collection_poster;
    }

    public void setTmdb_collection_poster(String tmdb_collection_poster) {
        this.tmdb_collection_poster = tmdb_collection_poster;
    }

    public String getTrailer_url() {
        return trailer_url;
    }

    public void setTrailer_url(String trailer_url) {
        this.trailer_url = trailer_url;
    }

    public int getHave_tmdb_data() {
        return have_tmdb_data;
    }

    public void setHave_tmdb_data(int have_tmdb_data) {
        this.have_tmdb_data = have_tmdb_data;
    }
}

package com.egreat.movie.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.egreat.movie.api.dao.UpdateMovieInfoDao;
import com.egreat.movie.api.entity.MongoDoubanEntity;
import com.egreat.movie.api.entity.MongoImdbEntity;
import com.egreat.movie.api.entity.TmdbEntity;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

@Component
public class UpdateMovieService {

    @Resource
    private UpdateMovieInfoDao dao;
    private String language = "zh-CN";
    private String photoBaseUrl = "https://image.tmdb.org/t/p/original";


    public void startUpdate() {
        startUpdateDoubanDB();
        startUpdateImdbDB();
    }

    public void startUpdateDoubanDB() {
        language = "zh-CN";
        updateTmdbInfoFromDouban();
    }

    public void startUpdateImdbDB() {
        language = "en-US";
        updateTmdbInfoFromImdb();
    }

    private void updateTmdbInfoFromDouban() {
        System.out.println("开始");
        int count = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 2474; i++) {
            PageImpl<MongoDoubanEntity> doubanPageMovies = dao.getDoubanPageMovies(i, 40);
            if (doubanPageMovies != null) {
                for (MongoDoubanEntity doubanPageMovie : doubanPageMovies) {
                    String netTmdbInfo = getNetTmdbInfo(doubanPageMovie.getImdb_id());
                    if (netTmdbInfo != null) {
                        parseTmdbInfo(netTmdbInfo, doubanPageMovie);
                        count++;
                        long end = System.currentTimeMillis();
                        System.out.println("第" + count + "部，用时" + ((end - start) / 1000)
                                + "---" + doubanPageMovie.getImdb_id()
                                + "---" + doubanPageMovie.getTitle());
                    } else {
                        //数据为空，处理
                        doubanPageMovie.setHave_tmdb_data(2);
                        dao.updateDouban(doubanPageMovie);
                        count++;
                        long end = System.currentTimeMillis();
                        System.out.println("第" + count + "部，错误用时" + ((end - start) / 1000)
                                + "---" + doubanPageMovie.getImdb_id()
                                + "---" + doubanPageMovie.getTitle());
                    }
                }
            }
        }
    }

    private void updateTmdbInfoFromImdb() {
        System.out.println("开始");
        int count = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 2474; i++) {
            PageImpl<MongoImdbEntity> imdbPageMovies = dao.getImdbPageMovies(i, 40);
            if (imdbPageMovies != null) {
                for (MongoImdbEntity imdbPageMovie : imdbPageMovies) {
                    String netTmdbInfo = getNetTmdbInfo(imdbPageMovie.getId());
                    if (netTmdbInfo != null) {
                        parseTmdbInfo(netTmdbInfo, imdbPageMovie);
                        count++;
                        long end = System.currentTimeMillis();
                        System.out.println("第" + count + "部，用时" + ((end - start) / 1000)
                                + "---" + imdbPageMovie.getId()
                                + "---" + imdbPageMovie.getTitle());
                    } else {
                        //数据为空，处理
                        imdbPageMovie.setHave_tmdb_data(2);
                        dao.updateImdb(imdbPageMovie);
                        count++;
                        long end = System.currentTimeMillis();
                        System.out.println("第" + count + "部，错误用时" + ((end - start) / 1000)
                                + "---" + imdbPageMovie.getId()
                                + "---" + imdbPageMovie.getTitle());
                    }
                }
            } else {
                System.out.println("null");
            }
        }
    }

    public void updateTmdbInfoFromImdbId(String imdbId, TmdbEntity tmdbEntity) {
        String netTmdbInfo = getNetTmdbInfo(imdbId);
        if (netTmdbInfo != null) {
            parseTmdbInfo(netTmdbInfo, tmdbEntity);
        }
    }

    private void parseTmdbInfo(String netTmdbInfo, TmdbEntity tmdbEntity) {
        try {
            JSONObject jsonObject = (JSONObject) JSONObject.parse(netTmdbInfo);
            JSONArray movieResults = jsonObject.getJSONArray("movie_results");
            JSONArray tvResults = jsonObject.getJSONArray("tv_results");
            JSONArray tvEpisodeResults = jsonObject.getJSONArray("tv_episode_results");
            if (jsonArrayNotEmpty(movieResults)) {
                //是电影
                parseMovieResults(movieResults, tmdbEntity);
            } else if (jsonArrayNotEmpty(tvResults)) {
                //是电视剧
                parseTvResults(tvResults, tmdbEntity);
            } else if (jsonArrayNotEmpty(tvEpisodeResults)) {
                //是电视剧
                parseTvEpisodeResults(tvEpisodeResults, tmdbEntity);
            } else {
                //数据为空，处理
                tmdbEntity.setHave_tmdb_data(2);
                if (tmdbEntity instanceof MongoDoubanEntity) {
                    dao.updateDouban((MongoDoubanEntity) tmdbEntity);
                } else if (tmdbEntity instanceof MongoImdbEntity) {
                    dao.updateImdb((MongoImdbEntity) tmdbEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //数据异常，处理
            tmdbEntity.setHave_tmdb_data(2);
            if (tmdbEntity instanceof MongoDoubanEntity) {
                dao.updateDouban((MongoDoubanEntity) tmdbEntity);
            } else if (tmdbEntity instanceof MongoImdbEntity) {
                dao.updateImdb((MongoImdbEntity) tmdbEntity);
            }
        }
    }

    private void parseMovieResults(JSONArray movieResults, TmdbEntity tmdbEntity) {
        int id = movieResults.getJSONObject(0).getIntValue("id");
        tmdbEntity.setTmdb_id(String.valueOf(id));

        String netTmdbMovieInfo = getNetTmdbMovieInfo(String.valueOf(id));

        if (netTmdbMovieInfo != null) {
            JSONObject jsonObject = (JSONObject) JSONObject.parse(netTmdbMovieInfo);
            String title = jsonObject.getString("title");
            String backdropPath = jsonObject.getString("backdrop_path");
            String posterPath = jsonObject.getString("poster_path");

            tmdbEntity.setTmdb_name(title);
            if (backdropPath != null && !backdropPath.equals("null")) {
                tmdbEntity.setTmdb_bg(photoBaseUrl + backdropPath);
            }
            if (posterPath != null && !posterPath.equals("null")) {
                tmdbEntity.setTmdb_poster(photoBaseUrl + posterPath);
            }

            JSONObject belongs_to_collection = jsonObject.getJSONObject("belongs_to_collection");
            if (belongs_to_collection != null) {
                String collectionName = belongs_to_collection.getString("name");
                String collectionPoster = belongs_to_collection.getString("poster_path");
                String collectionBackdrop = belongs_to_collection.getString("backdrop_path");

                tmdbEntity.setTmdb_collection_name(collectionName);
                if (collectionPoster != null && !collectionPoster.equals("null")) {
                    tmdbEntity.setTmdb_collection_poster(photoBaseUrl + collectionPoster);
                }
                if (collectionBackdrop != null && !collectionBackdrop.equals("null")) {
                    tmdbEntity.setTmdb_collection_bg(photoBaseUrl + collectionBackdrop);
                }
            }
            tmdbEntity.setHave_tmdb_data(1);
            if (tmdbEntity instanceof MongoDoubanEntity) {
                dao.updateDouban((MongoDoubanEntity) tmdbEntity);
            } else if (tmdbEntity instanceof MongoImdbEntity) {
                dao.updateImdb((MongoImdbEntity) tmdbEntity);
            }
        }
    }

    private void parseTvResults(JSONArray tvResults, TmdbEntity tmdbEntity) {
        String firstAirDate = tvResults.getJSONObject(0).getString("first_air_date");
        int id = tvResults.getJSONObject(0).getIntValue("id");
        parseTv(tmdbEntity, firstAirDate, id);
    }

    private void parseTvEpisodeResults(JSONArray tvEpisodeResults, TmdbEntity tmdbEntity) {
        int showId = tvEpisodeResults.getJSONObject(0).getIntValue("show_id");
        String airDate = tvEpisodeResults.getJSONObject(0).getString("air_date");
        parseTv(tmdbEntity, airDate, showId);
    }

    private void parseTv(TmdbEntity tmdbEntity, String firstAirDate, int id) {
        tmdbEntity.setTmdb_id(String.valueOf(id));
        String tmdbTvInfo = getNetTmdbTvInfo(String.valueOf(id));
        if (tmdbTvInfo != null) {
            JSONObject info = (JSONObject) JSONObject.parse(tmdbTvInfo);
            String backdropPath = info.getString("backdrop_path");
            String posterPath = info.getString("poster_path");
            String name = info.getString("name");

            tmdbEntity.setTmdb_collection_name(name);
            if (backdropPath != null && !backdropPath.equals("null")) {
                tmdbEntity.setTmdb_bg(photoBaseUrl + backdropPath);
                tmdbEntity.setTmdb_collection_bg(photoBaseUrl + backdropPath);
            }
            if (posterPath != null && !posterPath.equals("null")) {
                tmdbEntity.setTmdb_collection_poster(photoBaseUrl + posterPath);
            }

            JSONArray seasons = info.getJSONArray("seasons");
            if (jsonArrayNotEmpty(seasons)) {
                for (int i = 0; i < seasons.size(); i++) {
                    String airDate = seasons.getJSONObject(i).getString("air_date");
                    String posterPathForSeason = seasons.getJSONObject(i).getString("poster_path");
                    if (firstAirDate != null && firstAirDate.equals(airDate)) {
                        if (posterPathForSeason != null && !posterPathForSeason.equals("null")) {
                            tmdbEntity.setTmdb_poster(photoBaseUrl + posterPathForSeason);
                        }
                    }
                }
            }
            tmdbEntity.setHave_tmdb_data(1);
            if (tmdbEntity instanceof MongoDoubanEntity) {
                dao.updateDouban((MongoDoubanEntity) tmdbEntity);
            } else if (tmdbEntity instanceof MongoImdbEntity) {
                dao.updateImdb((MongoImdbEntity) tmdbEntity);
            }
        }
    }

    private String getNetTmdbInfo(String imdbId) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String INFO_URL = "https://api.themoviedb.org/3/find/";
            return restTemplate.getForObject(INFO_URL + "{1}" +
                            "?api_key=8f11d3120fd550b3b8e13b5c35a8caa2&language=" + language + "&external_source=imdb_id",
                    String.class, imdbId);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getNetTmdbMovieInfo(String id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String MOVIE_URL = "https://api.themoviedb.org/3/movie/";
            return restTemplate.getForObject(MOVIE_URL + "{1}" +
                            "?api_key=8f11d3120fd550b3b8e13b5c35a8caa2&language=" + language,
                    String.class, id);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getNetTmdbTvInfo(String id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String TV_URL = "https://api.themoviedb.org/3/tv/";
            return restTemplate.getForObject(TV_URL + "{1}" +
                            "?api_key=8f11d3120fd550b3b8e13b5c35a8caa2&language=" + language,
                    String.class, id);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean jsonArrayNotEmpty(JSONArray jsonArray) {
        return jsonArray != null && jsonArray.size() > 0;
    }
}

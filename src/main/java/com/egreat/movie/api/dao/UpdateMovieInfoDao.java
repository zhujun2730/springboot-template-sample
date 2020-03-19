package com.egreat.movie.api.dao;


import com.egreat.movie.api.entity.MongoDoubanEntity;
import com.egreat.movie.api.entity.MongoImdbEntity;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class UpdateMovieInfoDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public void updateDouban(MongoDoubanEntity mongoDoubanEntity) {
        Query query = new Query(Criteria.where("_id").is(mongoDoubanEntity.getId()));
        Update update = new Update()
                .set("tmdb_id", mongoDoubanEntity.getTmdb_id())
                .set("tmdb_bg", mongoDoubanEntity.getTmdb_bg())
                .set("tmdb_poster", mongoDoubanEntity.getTmdb_poster())
                .set("tmdb_collection_name", mongoDoubanEntity.getTmdb_collection_name())
                .set("tmdb_collection_bg", mongoDoubanEntity.getTmdb_collection_bg())
                .set("tmdb_collection_poster", mongoDoubanEntity.getTmdb_collection_poster())
                .set("trailer_url", mongoDoubanEntity.getTrailer_url())
                .set("have_tmdb_data", mongoDoubanEntity.getHave_tmdb_data())
                .set("tmdb_name", mongoDoubanEntity.getTmdb_name());
        mongoTemplate.upsert(query, update, MongoDoubanEntity.class, "douban");
    }


    public void updateImdb(MongoImdbEntity mongoImdbEntity) {
        Query query = new Query(Criteria.where("_id").is(mongoImdbEntity.getId()));
        Update update = new Update()
                .set("tmdb_id", mongoImdbEntity.getTmdb_id())
                .set("imdb_id", mongoImdbEntity.getId())
                .set("tmdb_bg", mongoImdbEntity.getTmdb_bg())
                .set("tmdb_poster", mongoImdbEntity.getTmdb_poster())
                .set("tmdb_collection_name", mongoImdbEntity.getTmdb_collection_name())
                .set("tmdb_collection_bg", mongoImdbEntity.getTmdb_collection_bg())
                .set("tmdb_collection_poster", mongoImdbEntity.getTmdb_collection_poster())
                .set("trailer_url", mongoImdbEntity.getTrailer_url())
                .set("have_tmdb_data", mongoImdbEntity.getHave_tmdb_data())
                .set("tmdb_name", mongoImdbEntity.getTmdb_name());
        mongoTemplate.upsert(query, update, MongoImdbEntity.class, "imdb");
    }

    public MongoDoubanEntity findByImdbIdFromDouban(String imdbId) {
        Query query = Query.query(Criteria.where("imdb_id").is(imdbId));
        return mongoTemplate.findOne(query, MongoDoubanEntity.class, "douban");
    }

    public MongoImdbEntity findByImdbIdFromImdb(String imdbId) {
        Query query = Query.query(Criteria.where("id").is(imdbId));
        return mongoTemplate.findOne(query, MongoImdbEntity.class, "imdb");
    }

    public PageImpl<MongoDoubanEntity> getDoubanPageMovies(int pageIndex, int pageSize) {
        try {
            Query query = Query.query(Criteria.where("imdb_id").ne(null).and("have_tmdb_data").is(2));
            Pageable pageable = new PageRequest(pageIndex, pageSize);
            query.with(pageable);
            List<MongoDoubanEntity> items = mongoTemplate.find(query, MongoDoubanEntity.class, "douban");
            return (PageImpl<MongoDoubanEntity>) PageableExecutionUtils.getPage(items, pageable, () -> 0);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage() + "----" + mongoTemplate);
        }
        return null;
    }

    public PageImpl<MongoImdbEntity> getImdbPageMovies(int pageIndex, int pageSize) {
        try {
            Query query = Query.query(Criteria.where("id").ne(null).and("have_tmdb_data").is(0));
            Pageable pageable = new PageRequest(pageIndex, pageSize);
            query.with(pageable);
            List<MongoImdbEntity> items = mongoTemplate.find(query, MongoImdbEntity.class, "imdb");
            return (PageImpl<MongoImdbEntity>) PageableExecutionUtils.getPage(items, pageable, () -> 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PageImpl<MongoDoubanEntity> getDoubanUpdatedPageMovies(int pageIndex, int pageSize) {
        Query query = Query.query(Criteria.where("imdb_id").ne(null).and("have_tmdb_data").is(1));
        Pageable pageable = new PageRequest(pageIndex, pageSize);
        query.with(pageable);
        List<MongoDoubanEntity> items = mongoTemplate.find(query, MongoDoubanEntity.class, "douban");
        return (PageImpl<MongoDoubanEntity>) PageableExecutionUtils.getPage(items, pageable, () -> 0);
    }

    public PageImpl<MongoImdbEntity> getImdbUpdatedPageMovies(int pageIndex, int pageSize) {
        Query query = Query.query(Criteria.where("id").ne(null).and("have_tmdb_data").is(1));
        Pageable pageable = new PageRequest(pageIndex, pageSize);
        query.with(pageable);
        List<MongoImdbEntity> items = mongoTemplate.find(query, MongoImdbEntity.class, "imdb");
        return (PageImpl<MongoImdbEntity>) PageableExecutionUtils.getPage(items, pageable, () -> 0);
    }

    public long doubanUpdatedCount() {
        Query query = Query.query(Criteria.where("imdb_id").ne(null).and("have_tmdb_data").is(1));
        return mongoTemplate.count(query, MongoDoubanEntity.class, "douban");
    }

    public long imdbUpdatedCount() {
        Query query = Query.query(Criteria.where("id").ne(null).and("have_tmdb_data").is(1));
        return mongoTemplate.count(query, MongoImdbEntity.class, "imdb");
    }

}

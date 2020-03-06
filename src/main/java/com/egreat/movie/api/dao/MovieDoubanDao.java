package com.egreat.movie.api.dao;

import com.egreat.movie.api.AppConst;
import com.egreat.movie.api.entity.MongoDoubanEntity;
import com.egreat.movie.api.entity.MongoImdbEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by anany on 2019-03-25.
 * <p>
 */
@Component
public class MovieDoubanDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void insert(MongoDoubanEntity entity) {
        mongoTemplate.save(entity);
    }

    public void update(MongoDoubanEntity entity) {
        Query query = new Query(Criteria.where("_id").is(entity.getId()));
        Update update = new Update()
                .set("send_status", entity.getActor())
                .set("retry_count", entity.getAuthor());
        mongoTemplate.updateFirst(query, update, MongoDoubanEntity.class);
    }

    public MongoDoubanEntity findMovieById(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, MongoDoubanEntity.class);
    }

    public MongoDoubanEntity findMovieByName(String movieName) {
        Query query = new Query(Criteria.where("title").is(movieName));
        return mongoTemplate.findOne(query, MongoDoubanEntity.class);
    }

    public List<MongoDoubanEntity> findList() {
        Query query = new Query(Criteria.where("type").is(0))
                .limit(AppConst.RECORD_COUNT);
        return mongoTemplate.find(query, MongoDoubanEntity.class);
    }

    public List<MongoDoubanEntity> findDoubanListLike(String patternName) {


        Pattern pattern = Pattern.compile("^.*" + patternName + ".*$", Pattern.CASE_INSENSITIVE);

        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("title").regex(pattern),
                Criteria.where("other_name").regex(pattern));

        Query query = new Query();
        query.addCriteria(criteria);
        query.limit(AppConst.RECORD_COUNT);

        return mongoTemplate.find(query, MongoDoubanEntity.class);
    }

    public List findListLike(String patternName, int type) {


        Pattern pattern = Pattern.compile("^.*" + patternName + ".*$", Pattern.CASE_INSENSITIVE);

        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("title").regex(pattern),
                Criteria.where("other_name").regex(pattern));

        Query query = new Query();
        query.addCriteria(criteria);
        query.limit(AppConst.RECORD_COUNT);

        if (type == 0) {
            return mongoTemplate.find(query, MongoDoubanEntity.class);
        }
        return mongoTemplate.find(query, MongoImdbEntity.class);
    }

    public List<MongoImdbEntity> findImdbListLike(String patternName) {


        Pattern pattern = Pattern.compile("^.*" + patternName + ".*$", Pattern.CASE_INSENSITIVE);

        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("title").regex(pattern),
                Criteria.where("other_name").regex(pattern));

        Query query = new Query();
        query.addCriteria(criteria);
        query.limit(AppConst.RECORD_COUNT);

        return mongoTemplate.find(query, MongoImdbEntity.class);
    }
}

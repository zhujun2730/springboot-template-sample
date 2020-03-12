package com.egreat.movie.api.controller;


import com.egreat.movie.api.dao.UpdateMovieInfoDao;
import com.egreat.movie.api.entity.MongoDoubanEntity;
import com.egreat.movie.api.entity.MongoImdbEntity;
import com.egreat.movie.api.service.UpdateMovieService;
import com.egreat.movie.api.utils.R;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/testapi")
public class TestApi {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private UpdateMovieInfoDao dao;

    @Resource
    private UpdateMovieService service;


//    @RequestMapping("/page/douban")
//    private R showDoubanPageMovies(int page) {
//        PageImpl<MongoDoubanEntity> pageMovies = dao.getDoubanUpdatedPageMovies(page, 20);
//        return R.ok().put("data", pageMovies.getContent());
//    }
//
//    @RequestMapping("/page/imdb")
//    private R showImdbPageMovies(int page) {
//        PageImpl<MongoImdbEntity> pageMovies = dao.getImdbUpdatedPageMovies(page, 20);
//        return R.ok().put("data", pageMovies.getContent());
//    }
//
//    @RequestMapping("/count/douban")
//    private R doubanCount() {
//        return R.ok().put("data", dao.doubanUpdatedCount());
//    }
//
//    @RequestMapping("/count/imdb")
//    private R imdbCount() {
//        return R.ok().put("data", dao.imdbUpdatedCount());
//    }
//
//    @RequestMapping("/updateDoubanDB")
//    private R updateDoubanDB() {
//        new Thread(() -> service.startUpdateDoubanDB()).start();
//        return R.ok().put("data", "start...");
//    }
//
//    @RequestMapping("/updateImdbDB")
//    private R updateImdbDB() {
//        new Thread(() -> service.startUpdateImdbDB()).start();
//        return R.ok().put("data", "start...");
//    }
}

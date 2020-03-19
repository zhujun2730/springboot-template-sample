package com.egreat.movie.api.controller;


import com.egreat.movie.api.base.BaseController;
import com.egreat.movie.api.dao.MovieDoubanDao;
import com.egreat.movie.api.dao.UpdateMovieInfoDao;
import com.egreat.movie.api.entity.EsDoubanEntity;
import com.egreat.movie.api.entity.EsImdbEntity;
import com.egreat.movie.api.entity.MongoDoubanEntity;
import com.egreat.movie.api.entity.MongoImdbEntity;
import com.egreat.movie.api.service.UpdateMovieService;
import com.egreat.movie.api.utils.R;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by anany on 2019-03-25.
 * <p>
 */
@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {

    @Resource
    private MovieDoubanDao movieDoubanDao;

    @Resource
    private UpdateMovieInfoDao updateMovieInfoDao;

    @Resource
    private UpdateMovieService updateMovieService;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @RequestMapping("/queryMovie")
    public R queryMovie(String title, String languageType, String queryType, @PageableDefault Pageable pageable) {

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        System.out.println(title);

        builder.withQuery(
                QueryBuilders.disMaxQuery().add(QueryBuilders.matchQuery("title", title))
                        .add(QueryBuilders.matchQuery("other_name", title)).tieBreaker((float) 0.3)
        );

        List dataList = new ArrayList();
        if (StringUtils.isEmpty(languageType)) {
            // 中文数据
            List<EsDoubanEntity> dataDouban = elasticsearchTemplate.queryForList(
                    builder.build(), EsDoubanEntity.class);
            for (EsDoubanEntity doubanEntity : dataDouban) {
                doubanEntity.setLanguageType(0);
                dataList.add(doubanEntity);
            }

            // 英文数据
            List<EsImdbEntity> dataImdb = elasticsearchTemplate.queryForList(builder.build(), EsImdbEntity.class);
            for (EsImdbEntity imdbEntity : dataImdb) {
                imdbEntity.setLanguageType(1);
                dataList.add(imdbEntity);
            }
        } else if ("0".equals(languageType)) {   // 中文
            List<EsDoubanEntity> dataDouban = elasticsearchTemplate.queryForList(
                    builder.build(), EsDoubanEntity.class);
            for (EsDoubanEntity doubanEntity : dataDouban) {
                doubanEntity.setLanguageType(0);
                dataList.add(doubanEntity);
            }
        } else if ("1".equals(languageType)) {   // 英文
            List<EsImdbEntity> dataImdb = elasticsearchTemplate.queryForList(builder.build(), EsImdbEntity.class);
            for (EsImdbEntity imdbEntity : dataImdb) {
                imdbEntity.setLanguageType(1);
                dataList.add(imdbEntity);
            }
        }


        return R.ok().put("data", dataList);
    }

    @RequestMapping("/queryMovieDB")
    public R queryMovieMongo(String title, String languageType, @PageableDefault Pageable pageable) {

        List dataList = new ArrayList();
        if (StringUtils.isEmpty(languageType)) {
            // 中文数据
            List<MongoDoubanEntity> dataDouban = movieDoubanDao.findDoubanListLike(title);

            for (MongoDoubanEntity doubanEntity : dataDouban) {
                doubanEntity.setLanguageType(0);
                dataList.add(doubanEntity);
            }

            // 英文数据
            List<MongoImdbEntity> dataImdb = movieDoubanDao.findImdbListLike(title);

            for (MongoImdbEntity imdbEntity : dataImdb) {
                imdbEntity.setLanguageType(1);
                dataList.add(imdbEntity);
            }
        } else if ("0".equals(languageType)) {   // 中文
            List<MongoDoubanEntity> dataDouban = movieDoubanDao.findDoubanListLike(title);
            for (MongoDoubanEntity doubanEntity : dataDouban) {
                doubanEntity.setLanguageType(0);
                dataList.add(doubanEntity);
            }
        } else if ("1".equals(languageType)) {   // 英文
            List<MongoImdbEntity> dataImdb = movieDoubanDao.findImdbListLike(title);
            for (MongoImdbEntity imdbEntity : dataImdb) {
                imdbEntity.setLanguageType(1);
                dataList.add(imdbEntity);
            }
        }

        if (dataList.isEmpty()) {
            return R.error(301, "查询无数据");
        }

        return R.ok().put("data", dataList);
    }

    @RequestMapping("/updateMovieInfo")
    public R updateMovieInfoFormId(String imdbId) {
        try {
            MongoDoubanEntity doubanEntity = updateMovieInfoDao.findByImdbIdFromDouban(imdbId);
            MongoImdbEntity imdbEntity = updateMovieInfoDao.findByImdbIdFromImdb(imdbId);
            updateMovieService.updateTmdbInfoFromImdbId(doubanEntity.getImdb_id(), doubanEntity);
            updateMovieService.updateTmdbInfoFromImdbId(imdbEntity.getId(), imdbEntity);
            return R.ok().put("data", "success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok().put("data", "error");
    }
}

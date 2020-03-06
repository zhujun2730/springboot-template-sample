package com.egreat.movie.api.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by anany on 2019-03-25.
 * <p>
 * Email: zhuj@fapiao.com
 */
@Document(collection = "douban")
@Data
public class MongoDoubanEntity {


    /**
     * _id : 10000801
     * cover : https://img3.doubanio.com/f/movie/03d3c900d2a79a15dc1295154d5293a2d5ebd792/pics/movie/tv_default_large.png
     * title : 拉普兰
     * url : https://movie.douban.com/subject/10000801/
     * actor : 斯蒂芬·格拉汉姆 / 扎威·阿什顿 / Elizabeth Berrington
     * author : Michael Wynne
     * cover_local : db_cover\tv_default_large.png
     * create_time : 1553071266388
     * date_published : null
     * director : Catherine Morshead
     * genre : 喜剧
     * imdb_id : tt2091334
     * introduction : null
     * language : 英语
     * length : null
     * m_production_country : 英国
     * other_name : null
     * rating_human : null
     * rating_num :
     * type : 1
     * update_time : null
     * episode : null
     * first_broadcast : null
     * season_count : null
     * single_length : null
     */

    private String id;
    private String cover;
    private String title;
    private String url;
    private String actor;
    private String author;
    private String cover_local;
    private long create_time;
    private Object date_published;
    private String director;
    private String genre;
    private String imdb_id;
    private Object introduction;
    private String language;
    private Object length;
    private String m_production_country;
    private Object other_name;
    private Object rating_human;
    private String rating_num;
    private int type;
    private Object update_time;
    private Object episode;
    private Object first_broadcast;
    private Object season_count;
    private Object single_length;
    private int languageType;
}

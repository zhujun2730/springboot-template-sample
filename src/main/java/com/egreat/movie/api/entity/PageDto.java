package com.egreat.movie.api.entity;

import lombok.Data;

/**
 * Created by anany on 2019-08-30.
 * <p>
 */
@Data
public class PageDto {
    /**
     * 当前页
     */
    public Integer pageNo;
    /**
     * 每页显示的大小
     */
    public Integer pageSize;

    /**
     * 关键字
     */
    private String keyword;

    private Integer level;

    private Integer userId;
}

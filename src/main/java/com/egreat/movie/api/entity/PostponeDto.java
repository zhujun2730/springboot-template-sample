package com.egreat.movie.api.entity;

import lombok.Data;

/**
 * Created by anany on 2019-08-30.
 * <p>
 */
@Data
public class PostponeDto {

    private Integer radio;

    private Integer diyDays;

    private Long userId;

    @Override
    public String toString() {
        return "PostponeDto{" +
                "radio=" + radio +
                ", diyDays=" + diyDays +
                ", userId=" + userId +
                '}';
    }
}

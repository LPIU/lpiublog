package com.lxs.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 查询文章详情
 *
 * @author 25650
 * @user 潇洒
 * @date 2023/3/30-19:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PUTArticleVo {
    private Long id;
    //所属分类id
    private Long categoryId;
    private Long createBy;
    private Date createTime;
    private Integer delFlag;
    //是否允许评论 1是，0否
    private String isComment;
    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;
    //文章摘要
    private String summary;
    private List<Long> tags;
    //缩略图
    private String thumbnail;
    //标题
    private String title;
    //文章内容
    private String content;

    //访问量
    private Long viewCount;
    private Long updateBy;
    private Date updateTime;
}

package com.lxs.utils;

import com.lxs.domain.entity.Article;
import com.lxs.domain.vo.HotArticleVo;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @user 潇洒
 * @date 2023/3/8-19:46
 */
public class BeanCopyUtils {

    private BeanCopyUtils(){

    }
    public  static <V> V copyBean(Object source, Class<V> clazz) {

        V result = null;
        try {
            //创建目标对象
            result = clazz.newInstance();
            //实现属性COPY
            BeanUtils.copyProperties(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }

    public static <O,V> List<V> copyBeanList(List<O> list,Class<V> clazz){
         return list.stream()
                        .map(o -> copyBean(o,clazz))
                        .collect(Collectors.toList());
    }
    public static void main(String[] args) {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("asd");


        HotArticleVo hotArticleVo = copyBean(article, HotArticleVo.class);
        System.out.println(hotArticleVo);

    }
}

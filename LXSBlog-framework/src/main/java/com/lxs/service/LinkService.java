package com.lxs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.LinkAddDto;
import com.lxs.domain.dto.LinkPutDto;
import com.lxs.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-03-10 20:47:30
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult<Object> pageVoList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addLink(LinkAddDto linkAddDto);

    ResponseResult getLinkById(String id);

    ResponseResult putLink(LinkPutDto linkPutDto);
}

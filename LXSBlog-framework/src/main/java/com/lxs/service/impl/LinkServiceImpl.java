package com.lxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxs.constants.SystemConstants;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.LinkAddDto;
import com.lxs.domain.dto.LinkPutDto;
import com.lxs.domain.entity.Link;
import com.lxs.domain.vo.LinkVo;
import com.lxs.domain.vo.PageVo;
import com.lxs.mapper.LinkMapper;
import com.lxs.service.LinkService;
import com.lxs.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-03-10 20:47:31
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Link::getStatus, SystemConstants.Link_STATUS_NORMAL);
        List<Link> list = list(lambdaQueryWrapper);

        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult<Object> pageVoList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(status),Link::getStatus,status);
        queryWrapper.like(Objects.nonNull(name),Link::getName,name);
        Page<Link> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(page.getRecords(), LinkVo.class);
        PageVo pageVo = new PageVo(linkVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addLink(LinkAddDto linkAddDto) {
        save(BeanCopyUtils.copyBean(linkAddDto, Link.class));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLinkById(String id) {
        Link byId = getById(id);
        LinkVo link = BeanCopyUtils.copyBean(byId, LinkVo.class);
        return ResponseResult.okResult(link);
    }

    @Override
    public ResponseResult putLink(LinkPutDto linkPutDto) {
        updateById(BeanCopyUtils.copyBean(linkPutDto, Link.class));
        return ResponseResult.okResult();
    }

}
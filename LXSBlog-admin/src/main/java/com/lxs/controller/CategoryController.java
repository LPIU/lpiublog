package com.lxs.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson.JSON;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.AddCategoryDto;
import com.lxs.domain.dto.PutCategoryDto;
import com.lxs.domain.entity.Category;
import com.lxs.domain.enums.AppHttpCodeEnum;
import com.lxs.domain.vo.CategoryVo;
import com.lxs.domain.vo.ExcelCategoryVo;
import com.lxs.service.CategoryService;
import com.lxs.utils.BeanCopyUtils;
import com.lxs.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.HttpResource;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @user 潇洒
 * @date 2023/3/21-12:36
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list= categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }
    @GetMapping("/list1")
    public ResponseResult list(){
        List<CategoryVo> list= categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }
    @GetMapping("/list")
    public ResponseResult<Object> pageVoList(Integer pageNum,Integer pageSize,String name,String status ){
        return categoryService.pageVoList(pageNum,pageSize,name,status);
    }

    @PostMapping("")
    public ResponseResult addCategory(@RequestBody AddCategoryDto addCategoryDto){
        return categoryService.addCategory(addCategoryDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable String id){
        return categoryService.getCategoryById(id);
    }
    @PutMapping("")
    public ResponseResult putCategory(@RequestBody PutCategoryDto putCategoryDto){
        return categoryService.putCategory(putCategoryDto);
    }
    @DeleteMapping("/{id}")
    public ResponseResult delCategory(@PathVariable Long id){
        return categoryService.delCategory(id);
    }
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        //设置下载文件请求tou
        try {
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            List<Category> categoryVos = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response,JSON.toJSONString(responseResult));
        }
    }


}

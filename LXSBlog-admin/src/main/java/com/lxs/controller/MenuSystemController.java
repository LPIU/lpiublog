package com.lxs.controller;

import com.lxs.domain.ResponseResult;
import com.lxs.domain.entity.Menu;
import com.lxs.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @user 潇洒
 * @date 2023/3/30-20:59
 */
@RestController
@RequestMapping("/system/menu")
public class MenuSystemController {
    @Autowired
    private MenuService menuService;


    @GetMapping("/list")
    public ResponseResult listMenu(String status,String menuName){
        return menuService.listMenu(status,menuName);
    }

    /**
     * @param menu
     * @return
     */
    @PostMapping("")
    public ResponseResult addMenu(@RequestBody Menu menu){
        menuService.save(menu);
        return ResponseResult.okResult();
    }
    @GetMapping("/{id}")
    public ResponseResult searchMenu(@PathVariable String id){

        return menuService.searchMenu(id);
    }
    @PutMapping("")
    public ResponseResult putMenu(@RequestBody Menu menu){
        return menuService.putMenu(menu);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteMenu(@PathVariable String id){
        menuService.removeById(id);
        return ResponseResult.okResult();
    }
    @GetMapping("/treeselect")
    public ResponseResult treeSelect(){
        return menuService.treeSelect();
    }
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult treeSelectById(@PathVariable Long id){
        return menuService.treeSelectById(id);
    }
}

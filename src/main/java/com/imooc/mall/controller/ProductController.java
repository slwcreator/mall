package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.ProductListReq;
import com.imooc.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 前台商品 Controller
 */
@RestController
public class ProductController {

    @Resource
    ProductService productService;

    @ApiOperation("商品详情")
    @GetMapping("/product/detail")
    public ApiRestResponse<Product> detail(@RequestParam Integer id) {
        Product product = productService.detail(id);
        return ApiRestResponse.success(product);
    }

    /**
     * 前台的商品列表接口
     */
    @ApiOperation("商品列表")
    @GetMapping("/product/list")
    public ApiRestResponse<PageInfo<Product>> list(ProductListReq productListReq) {
        PageInfo<Product> pageInfo = productService.list(productListReq);
        return ApiRestResponse.success(pageInfo);
    }
}

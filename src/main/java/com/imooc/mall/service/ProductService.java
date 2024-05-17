package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.ProductListReq;

import java.io.File;
import java.io.IOException;

public interface ProductService {
    void add(AddProductReq addProductReq);

    void update(Product product);

    void delete(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);

    PageInfo<Product> listForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);

    PageInfo<Product> list(ProductListReq productListReq);

    void addProductByExcel(File destFile) throws IOException;
}

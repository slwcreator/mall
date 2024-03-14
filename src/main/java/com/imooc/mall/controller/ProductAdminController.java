package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.UpdateProductReq;
import com.imooc.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.UUID;

/**
 * 后台商品管理 Controller
 */
@RestController
public class ProductAdminController {

    @Resource
    ProductService productService;

    @ApiOperation("上传文件")
    @PostMapping("/admin/upload/file")
    public ApiRestResponse<String> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        //获取文件扩展名
        String suffix = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid + suffix;

        //文件目录
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);
        //文件全路径名
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);

        if (!fileDirectory.exists()) {
            if (!fileDirectory.mkdir()) {
                throw new ImoocMallException(ImoocMallExceptionEnum.MKDIR_FAILED);
            }
        }

        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return ApiRestResponse.success(getHost(new URI(request.getRequestURL() + ""))
                    + "/images/" + newFileName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ApiRestResponse.error(ImoocMallExceptionEnum.UPLOAD_FAILED);
        }
    }

    private URI getHost(URI uri) {
        URI effectiveURI;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }
        return effectiveURI;
    }

    @ApiOperation("新增商品")
    @PostMapping("/admin/product/add")
    public ApiRestResponse<Product> addProduct(@Valid @RequestBody AddProductReq addProductReq) {
        productService.add(addProductReq);
        return ApiRestResponse.success();
    }

    @ApiOperation("更新商品")
    @PostMapping("/admin/product/update")
    public ApiRestResponse<Product> updateProduct(@Valid @RequestBody UpdateProductReq updateProductReq) {
        Product product = new Product();
        BeanUtils.copyProperties(updateProductReq, product);
        productService.update(product);
        return ApiRestResponse.success();
    }

    @ApiOperation("删除商品")
    @PostMapping("/admin/product/delete")
    public ApiRestResponse<Product> deleteProduct(@RequestParam Integer id) {
        productService.delete(id);
        return ApiRestResponse.success();
    }
}

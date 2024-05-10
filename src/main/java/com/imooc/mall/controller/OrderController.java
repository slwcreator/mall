package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.model.request.CreateOrderReq;
import com.imooc.mall.model.vo.OrderVO;
import com.imooc.mall.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    OrderService orderService;

    @PostMapping("/order/create")
    @ApiOperation("创建订单")
    public ApiRestResponse<String> create(@RequestBody CreateOrderReq createOrderReq) {
        String orderNo = orderService.create(createOrderReq);
        return ApiRestResponse.success(orderNo);
    }

    @GetMapping("/order/detail")
    @ApiOperation("前台订单详情")
    public ApiRestResponse<OrderVO> detail(@RequestParam String orderNo) {
        OrderVO orderVO = orderService.detail(orderNo);
        return ApiRestResponse.success(orderVO);
    }

    @GetMapping("/order/list")
    @ApiOperation("用户订单列表")
    public ApiRestResponse<PageInfo<OrderVO>> list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<OrderVO> pageInfo = orderService.listForCustomer(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @PostMapping("/order/cancel")
    @ApiOperation("前台取消订单")
    public ApiRestResponse<String> cancel(@RequestParam String orderNo) {
        orderService.cancel(orderNo);
        return ApiRestResponse.success();
    }

    @PostMapping("/order/qrcode")
    @ApiOperation("生成支付二维码")
    public ApiRestResponse<String> qrcode(@RequestParam String orderNo) {
        String pngAddress = orderService.qrcode(orderNo);
        return ApiRestResponse.success(pngAddress);
    }
}

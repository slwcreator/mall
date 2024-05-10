package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.model.vo.OrderVO;
import com.imooc.mall.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderAdminController {

    @Resource
    OrderService orderService;

    @GetMapping("/admin/order/list")
    @ApiOperation("管理员订单列表")
    public ApiRestResponse<PageInfo<OrderVO>> listForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<OrderVO> pageInfo = orderService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @PostMapping("/admin/order/delivered")
    @ApiOperation("订单发货")
    public ApiRestResponse<String> delivered(@RequestParam String orderNo) {
        orderService.delivered(orderNo);
        return ApiRestResponse.success();
    }
}

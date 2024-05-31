package com.imooc.mall.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 管理员校验过滤器
 */
public class AdminFilter implements Filter {

    public User currentUser;

    @Resource
    UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("application/json;charset=utf-8");
        if ("OPTIONS".equals(request.getMethod())) {
            filterChain.doFilter(request, response);
        } else {
            String token = request.getHeader(Constant.JWT_TOKEN);
            if (StringUtils.isEmpty(token)) {
                getOutException(response, ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN));
                return;
            }

            Algorithm algorithm = Algorithm.HMAC256(Constant.JWT_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            try {
                DecodedJWT jwt = verifier.verify(token);
                currentUser = new User();
                currentUser.setId(jwt.getClaim(Constant.USER_ID).asInt());
                currentUser.setRole(jwt.getClaim(Constant.USER_ROLE).asInt());
                currentUser.setUsername(jwt.getClaim(Constant.USER_NAME).asString());
                UserFilter.userThreadLocal.set(currentUser);
            } catch (TokenExpiredException e) {
                //token过期，抛出异常
                getOutException(response, ApiRestResponse.error(ImoocMallExceptionEnum.TOKEN_EXPIRED));
                return;
            } catch (JWTDecodeException e) {
                getOutException(response, ApiRestResponse.error(ImoocMallExceptionEnum.TOKEN_WRONG));
                return;
            } catch (Exception e) {
                e.printStackTrace();
                getOutException(response, ApiRestResponse.error(ImoocMallExceptionEnum.SYSTEM_ERROR));
                return;
            }
            //校验是否是管理员
            if (userService.checkAdminRole(currentUser)) {
                filterChain.doFilter(request, response);
            } else {
                getOutException(response, ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN));
            }
        }
    }

    @Override
    public void destroy() {
    }

    private <T> void getOutException(HttpServletResponse response, ApiRestResponse<T> r) throws IOException {
        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        String jsonObject = mapper.writeValueAsString(r);
        out.println(jsonObject);
        out.flush();
        out.close();
    }
}

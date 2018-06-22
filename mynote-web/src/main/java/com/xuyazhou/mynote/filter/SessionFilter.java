package com.xuyazhou.mynote.filter;


import com.xuyazhou.mynote.Utils.JsonUtil;
import com.xuyazhou.mynote.exception.*;
import com.xuyazhou.mynote.service.IMemberLoginRegistService;
import com.xuyazhou.mynote.web.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 过滤器
 * 
 */
@Component
public class SessionFilter implements Filter {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final String ACCESS_TOKEN_HEADER = "accessToken";

    @Resource
    private Set<String> freeURIRegexSet;

    private Set<Pattern> freeURIRegexPatternSet;

    @Resource
    private MessageSource messageSource;

    @Autowired
    private IMemberLoginRegistService memberLoginRegistRemoteService;

    @PostConstruct
    public void postConstruct() {
        if (freeURIRegexPatternSet == null) {
            freeURIRegexPatternSet = new HashSet<>();
            for (String regex : freeURIRegexSet) {
                freeURIRegexPatternSet.add(Pattern.compile(regex));
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info(" filter init ......");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse rep = (HttpServletResponse) response;

            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            boolean freeStyle = false;
            // 过滤掉不需要登录请求通过的路径
            for (Pattern pattern : freeURIRegexPatternSet) {
                Matcher matcher = pattern.matcher(req.getRequestURI());
                if (matcher.find()) {
                    freeStyle = true;
                }
            }

            if (!freeStyle) {
                String accessToken = req.getHeader(ACCESS_TOKEN_HEADER);

                String userId = "";
                if (StringUtils.isNotEmpty(accessToken)) {
                    userId = memberLoginRegistRemoteService.checkLogin(accessToken);
                    if (StringUtils.isBlank(userId)) {
                        throw new LoginException(MemberException.TOKEN_ERROR);
                    }
                }

                if (StringUtils.isEmpty(userId)) {
                    throw new LoginException(LoginException.NO_PERMISSION);
                }
                req.setAttribute("userId", userId);
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            Throwable cause = e.getCause() instanceof BaseException || e.getCause() instanceof ParameterException || e.getCause() instanceof DefineException
                    ? e.getCause() : e;
            writerError(response, ResponseEntity.getEntityError(cause, Locale.SIMPLIFIED_CHINESE, messageSource));
        }
    }

    @Override
    public void destroy() {
        logger.info(" filter destroy ......");
    }

    private void writerError(ServletResponse response, ResponseEntity entity) throws IOException {
        String result = JsonUtil.toJson(entity);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(result);
        writer.flush();
    }
}

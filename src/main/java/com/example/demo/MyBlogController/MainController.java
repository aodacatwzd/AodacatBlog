package com.example.demo.MyBlogController;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayEbppInvoiceInfoSendRequest;
import com.alipay.api.request.AlipayOpenAuthTokenAppQueryRequest;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.response.AlipayEbppInvoiceInfoSendResponse;
import com.alipay.api.response.AlipayOpenAuthTokenAppQueryResponse;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.example.demo.Services.BasicService;
import com.example.demo.Services.CommentService;
import com.example.demo.Services.UserService;
import com.example.demo.Utils.IpUtil;
import com.example.demo.Utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/")
public class MainController {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final
    BasicService basicService;

    private final
    CommentService commentService;

    private final
    UserService userService;

    private static final String URL = "https://openapi.alipaydev.com/gateway.do";
    private static final String APPID = "2016101100660137";
    private static final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDQFpEU7fc1///1\n" +
            "u3sGMPpPlwXGR3yRsIBbj8dZ98zdorEkWIsu4Bew63JzILG2iKmn36lpjdMwXUEb\n" +
            "JkT2iPDnfEjCMkKKZ3RLFmG+zewcJqyNVFzADwe9ijg0GqQxxAFmLOZRtRL/OV9E\n" +
            "BetqhR3+twY+IBVJg55hS6IZehX7k+RguowPKC6CMewacsAzi6Yu5K7AL1egxEk1\n" +
            "upWWBY4VasL2Zi4Wr4wFtW7oIMj9mNfNUeZXjh0cDqhZc+/Mbf+3XM5p7LWzdFeW\n" +
            "84dfZpLy/D2h7/CFLYOZGfFv1sFKvNWfm1rG5lLHDmpX96OGdsmdE34Nese2cF0h\n" +
            "5XoGy3QHAgMBAAECggEAXVEhcu9co5zQXpTbEOW5+yNQ9aBtXrB2f5k030XfW61w\n" +
            "H0qN8FGSjkaoJrUMvT0ASEZnJOzzPoczgUdiOeUjk8wKnPwJFyRZ5Kp+3yBduCon\n" +
            "y6F/jqmu4PMxTTx9UcwCjJ2qFA5OHKLP9CNrdXOJBgdyBz5ADZ220w8l/KLhZNYY\n" +
            "b2jnd9L9N+hjV/LKl69EhOxdrJB++2o02JU0VnRoo4VMZKHkv1vkouoptmdzANah\n" +
            "XOaArmajHtPkUQHIr7ROTZ714oSUrjEVdNPPa4sitNmqm6o7xXaDtRIKInbKhO+b\n" +
            "dHtSnJ6q+s5eGln16fdxqsBmSl6VlcXT0Y+0CCxcEQKBgQD2Dm3/0enQbki0durA\n" +
            "rNooRkCcJZGwHxz4bd3dFBgkp3eeryW1vDmRxlilMYNAJjGoRAHzQMBRom+vJfd/\n" +
            "1WYN1r/trS7nozfZ+cdQAOUJcQg/VSEEwiOdsO6eaal0CLWMP6YW4rlDBhdjMMSD\n" +
            "NXSPis0ja84YC4E8W2DW/iaK/QKBgQDYf1Z4VpZIGisk/tmyigEn4fp5n34BJ7dx\n" +
            "7QDKl+fzFwmZFgSVHywGoHEurg01JiGa2mhglsQqTTlHGXnPOhNNnc9ipj87Ug3x\n" +
            "85G+OtunOlCtXk8XYuzUb/egQ5PZml5jPnxazN+GTrl0YDe9jtvDw7BICWiJO+7s\n" +
            "K3EGQ6U0UwKBgQCobOAmyE0/+v2RagTutmLuRohbWewLnoB6HNpNRj/wdyGOYV1y\n" +
            "tNDDU/3Btc6dzocXItYi6CO8vW+teFOeETi2KFVgH7eHn1XPwwYHYVVlzRVUJLVh\n" +
            "SHKQslfj33e6kWuUAM1zxOYDVtwTbYJWPfT3fI9Gu7hrBRUFd5cmEvnXEQKBgQCV\n" +
            "2OVP25afB0XPkHm7otuT8Ex+vA7p28SUHRfMIgJQdiSVifDYCkBdwdU1GxFXNTJT\n" +
            "TVKrcA8MvHClrpNqBGdx9IWjYTAo7e1rB0xcH78C61EF/l298SG9FFaP3dZK6hbk\n" +
            "mE5YRq979adVTnemQ2gHAr5TzfdF7W68loDfdhi1swKBgFGSlvRcCs5XvN5WWfaK\n" +
            "L84/29iuRP00f0OxqUdULO1hIFmO95XxE9dhyW0nImtBecDvamoAsFlfChwEIi9C\n" +
            "4x994CfeatY0MuP7c3oFK+2k+Oi171/AlWgfZjoikL5LSvZh1Nle2G5eNkozk3bG\n" +
            "XL9ia/gPinAFmivaw10/UPQd";
    private static final String FORMAT = "json";
    private static final String CHARSET = "UTF-8";
    //private static final String APP_PRIVATE_KEY2="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC1ZY55eAXcLyxRbaebwTHjmMBFxWijrV54mLPdlXEVHorfH9gl6Ntoc+EfP5UMiIgskGNulKEfN4an/5a7TR2GdHjEDKdPCNwwivNV+bKMA2Z74TzLqGV7QCFsHxUj4ucdIupKywMrwn7h9bLrQy30bqQ9bqTEmSgOGTW9DikLw8O9NJBRG4x1gEEYiw6Rxv9CLIpJ1QJ5cIKA544a8JEhtrcMP0Rc/ft6DCz3fQMgS6MZDQdU4riQTQnRlXyd/StfrFiD82O+uA0ak+YxcPT9uLQhBSo7hpoFvtrzqZHK+nK7WqMqHbOibLeRZHJSZCBZa8HiDPORMRmFrZTeeV3NAgMBAAECggEAIszZ+YnCLgzKxtBvsFzvEkfy0y/dNFGFZ0N8dk0+RZv3bnjwgc1bkn1wugr7sEzdOxd0S/mts4x2g/Jv230e5fWgWZRH4MzFWXa+2kauL1hTp/59KGElHvEduF16M8lLJ3bUVgb+k72blAQMEEByJ4u2bKa2a5UqbxH9EkkOLNhOEb2rUGrh7ZLJju7myckmO2oKNW0NZrLcs/UcAghaPL9EotcGIIYjaHr0ABMCFLibpB24GfIG094gwu6Zlf5HoQXb2mrjFwmrRULfEjI9tmB1LQZI0SPf3JSuGb+xQf0nOP5FO9nPQ4kGnq9vgf/4Aayn89RfMKLqcsPQX5ySQQKBgQDemNMPxzOoybAGwR5cK7RFl7hIkkVSXW1nou259XaFWi0GzDpQjZVrgisTtuA/wjMZLnd6yjHTdDOlmb6yzPFm6XZ0B639vMQKZ9S3X7MEFZWUjS4Trgc2SFIUM/Y12h6nqRm2zFfpaMJiP560RcAXTLVi63BTmAHDJ3byBRp13QKBgQDQngDD89cvq3kZoH2+qZd/49N+Qu15gYwq6lGLg/cZLM26OY0rM5mX4Es1+zi52BXiaUiucyal1GNpXkX9demWvrTYsNQO7L3no8dM2bzNfHwR2CRsOTq2LDgMMRiDTipCtciqhByWQbjzCtVQjxWmbYQQ+HAN13b8gCueMrdgsQKBgQC5HBXI9Ts8jhzYQRjiRWXwya7yitEjIZatrIxNLJeXZdoz4PpNqrAra2AvUNFDtFeSBVZOwn6U/flFKYmwX0YQ8u0SqKBEdgoBLT90Dx1rtBdkJdO/geV1esbnDh8dwXnkq3c60Mv6yqd71LRB7g9EUQI2dNxAaBRvGg6MTTon8QKBgGOiUrNDjg5SUvAOWn/o91Y5NRUkWc6iNJN6fZ+oUydO4qKCQg3UAxMqKEGLzjfUH/+WUQQgMuEYYrI8OCkpW3qHck/bhCvsnXY4HkNx6l4pigfrttmJK6U90Tbha9eqSTy7HS9zEUQh93b8QYzMCYcG3wBL5xh1Q3qsrChOCI7BAoGBAIvrez8oo56Q/whaQ5FRNLdtP02n1o0f7nrkWajjYHe11abE9A+ycdBMzGxvPfESLVAkse0lkI1DeKqVaPxgNYwTBzpuE7hz1suwoIgtQM50TuXWtgZqIuOps4OACjL5Y4otxlQjRuddHNM9Lg/shEtsXajPPqsBWGlpil6wP1by";
    private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj/H4WHlL5U+Lbq6c7Wunt1zLxWmtQVr4/WzJU1aTApgqZ1G1urRI38qpX6uuU74y3308/s4TB/KFErSs4HoNefptICGcKmVbA2+FxLdS2hEiTZZNzal3tXy2zBRah08QHCXXZMaQfJv9OeXXR/WsWET67vE7hv4/o3WaLZ9r80iG3lSqHX0Ozo+bG4dzkJ8heNh1UW2jJCMlOgEJm0yW6ioWoJiuQkreKWz8pW7V3CljkSLmT95sNIdhQK63iXNd2JB65OcaeIjQH8VSWOve0vXqVqWjAtKGmx8bknELmeu218n+5gm9ANt+zZgRFQ7SQl9U2xQMKgZKxbXpGvioLQIDAQAB";
    private static final String SIGN_TYPE = "RSA2";

    public MainController(BasicService basicService, CommentService commentService, UserService userService) {
        this.basicService = basicService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(Model model) {
        model.addAttribute("basicService", new BasicService());
        List<BasicService> articleList = basicService.getArticle("select * from article;");
        model.addAttribute("articleList", articleList);
        return "index/home";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String InsertArticle(@ModelAttribute BasicService basicService1, Model model, HttpServletRequest request) {
        if (basicService1.getId() != null) {
            basicService.delete(Integer.parseInt(basicService1.getId()));
            homePage(model);
        }
        if (basicService1.getName() != null && basicService1.getContext() != null) {
            System.out.println(IpUtil.getIpAddr(request));
            basicService1.setIp(IpUtil.getIpAddr(request));
            basicService.create(basicService1.getName(), basicService1.getContext(), basicService1.getIP(), basicService1.getSrc());
            homePage(model);
        }
        return "index/article";
    }

    @RequestMapping(value = "/secretBase", method = RequestMethod.GET)
    public String articlePage(Model model) {
        model.addAttribute("basicService", new BasicService());
        List<BasicService> articleList = basicService.getArticle("select * from article;");
        model.addAttribute("articleList", articleList);
        return "index/article";
    }

    @RequestMapping(value = "/articleList", method = RequestMethod.GET)
    public String articleList(Model model) {
        model.addAttribute("basicService", new BasicService());
        List<BasicService> articleList = basicService.getArticle("select * from article");
        model.addAttribute("articleList", articleList);
        return "index/articleList";
    }

    @RequestMapping(value = "/articleOpen/{id}", method = RequestMethod.GET)
    public String Articles(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("basicService", new BasicService());
        model.addAttribute("commentService", new CommentService());
        String sql1 = "select * from article where id=" + id + ";";
        String sql2 = "select * from comment where id=" + id + ";";
        List<BasicService> articleList = basicService.getArticle(sql1);
        List<CommentService> commentList = commentService.getComment(sql2);
        model.addAttribute("articleList", articleList);
        model.addAttribute("commentList", commentList);
        return "index/articleOpen";
    }

    @RequestMapping(value = "/articleOpen/{id}", method = RequestMethod.POST)
    public String InsertComment(@ModelAttribute CommentService commentService1, Model model, HttpServletRequest request, @PathVariable("id") Integer id, HttpSession httpSession) {

        System.out.println(IpUtil.getIpAddr(request));
        commentService1.setIp(IpUtil.getIpAddr(request));
        Date date = new Date();
        commentService1.setTime(simpleDateFormat.format(date));
        commentService.create(commentService1.getUsername(), commentService1.getContent(), id.toString(), commentService1.getIp(), commentService1.getTime());
        Articles(model, id);
        return "index/articleOpen";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String LoginPage(Model model) {
        model.addAttribute("userService", new UserService());
        return "index/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String Login(@NotNull @ModelAttribute UserService userService1, HttpSession httpSession, Model model, Map<String, Object> map) {
        //map.clear();
        System.out.println(userService1.getUserName());
        String sql = "select password from user where username='" + userService1.getUserName() + "';";
        //System.out.println(sql);
        System.out.println(httpSession.getId());
        List<UserService> userServiceList = userService.getInfo(sql);
        if (null != userServiceList.get(0)) {
            if (userServiceList.get(0).getPasswordMD5().equals(MD5Util.md5(userService1.getPasswordMD5()))) {
                System.out.println("yes");
                httpSession.setAttribute("username", userService1.getUserName());

                UUID uuid = UUID.randomUUID();
                userService.updateUUID(uuid.toString(),userService1.getUserName());
                return "redirect:" + httpSession.getAttribute("url");
            } else {
                map.put("msg", "账号/密码错误");
                return "index/login";
            }
        } else {
            return "index/login";
        }
    }

    @RequestMapping(value = "testAli", method = RequestMethod.GET)
    @ResponseBody
    public AlipayOpenAuthTokenAppQueryResponse get(HttpServletRequest httpServletRequest) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(URL, APPID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        //AlipayClient alipayClient = new DefaultAlipayClient(URL, APPID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);


        //商户批量授权
        /*AlipayEbppInvoiceMerchantlistEnterApplyRequest request = new AlipayEbppInvoiceMerchantlistEnterApplyRequest();
        request.setBizContent("{" +
                "\"merchant_base\":{" +
                "\"m_short_name\":\"JSDX\"," +
                "\"m_name\":\"江苏电信\"," +
                "\"logo_info\":\"data:image/jpeg;base64,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\"" +
                "    }," +
                "      \"sub_merchant_list\":[{" +
                "        \"sub_m_short_name\":\"JSDX_DQ\"," +
                "\"sub_m_name\":\"江苏电信电渠\"," +
                "\"register_no\":\"91500000747150346A\"," +
                "\"pid\":\"2088102143353534\"" +
                "        }]," +
                "\"sub_merchant_common_info\":{" +
                "\"product_code\":\"INVOICE_RETURN\"," +
                "\"s_short_name\":\"DQ\"" +
                "    }" +
                "  }");
        AlipayEbppInvoiceMerchantlistEnterApplyResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return response;*/


        //获取个人信息的auth_code auth_token userID
/*        String auth_code = httpServletRequest.getParameter("auth_code");
        System.out.println("auth_code:"+auth_code);
        AlipaySystemOauthTokenRequest alipaySystemOauthTokenRequest = new AlipaySystemOauthTokenRequest();
        alipaySystemOauthTokenRequest.setGrantType("authorization_code");
        alipaySystemOauthTokenRequest.setCode(auth_code);
        AlipaySystemOauthTokenResponse alipaySystemOauthTokenResponse = alipayClient.execute(alipaySystemOauthTokenRequest);
        String auth_token=alipaySystemOauthTokenResponse.getAccessToken();
        System.out.println("auth_token:"+auth_token);
        System.out.println("userid:"+alipaySystemOauthTokenResponse.getUserId());*/


        //获取第三方应用的auth_code auth_token 追加auth_token后查看授权状态
        String app_auth_code = httpServletRequest.getParameter("app_auth_code");
        System.out.println("app_auth_code:" + app_auth_code);

        AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
        request.setBizContent("{" +
                "    \"grant_type\":\"authorization_code\"," +
                "    \"code\":\"" + app_auth_code + "\"" +
                "  }");
        AlipayOpenAuthTokenAppResponse alipayOpenAuthTokenAppResponse = alipayClient.execute(request);
        String userID = alipayOpenAuthTokenAppResponse.getUserId();
        System.out.println("userID:" + userID);
        String app_auth_token = alipayOpenAuthTokenAppResponse.getAppAuthToken();
        System.out.println("app_auth_token:" + app_auth_token);
        AlipayOpenAuthTokenAppQueryRequest alipayOpenAuthTokenAppQueryRequest = new AlipayOpenAuthTokenAppQueryRequest();
        alipayOpenAuthTokenAppQueryRequest.setBizContent("{" +
                "\"app_auth_token\":\"" + app_auth_token + "\"" +
                "  }");
        AlipayOpenAuthTokenAppQueryResponse alipayOpenAuthTokenAppQueryResponse = alipayClient.execute(alipayOpenAuthTokenAppQueryRequest);
        System.out.println(alipayOpenAuthTokenAppQueryResponse.getStatus());
        return alipayOpenAuthTokenAppQueryResponse;

        //追加token
        /*request.putOtherTextParam("app_auth_token", "201907BB11322d01f1c246c2a93322b9338cbX83");
        AlipayEbppInvoiceInfoSendResponse alipayOpenAuthTokenAppResponse = alipayClient.execute(request);
        //System.out.println(alipayOpenAuthTokenAppResponse.getAppAuthToken());
        return alipayOpenAuthTokenAppResponse;*/

        //开简单发票
        /*AlipayEbppInvoiceSyncSimpleSendRequest request = new AlipayEbppInvoiceSyncSimpleSendRequest();
        request.setBizContent("{" +
                "\"m_short_name\":\"JSDX\"," +
                "\"sub_m_short_name\":\"JSDX_DQ\"," +
                "\"invoice_info\":{" +
                "\"user_id\":\"2088422459852830\"," +
                "\"out_invoice_id\":\"2088100022223333\"," +
                "\"file_download_url\":\"https://www.aodacatwzd.cn/downloads/test.pdf\"," +
                "\"file_download_type\":\"PDF\"," +
                "\"extend_fields\":\"m_invoice_detail_url=https://www.aodacatwzd.cn\"" +
                "    }" +
                "  }");
        request.putOtherTextParam("app_auth_token", "201908BB7cf2d254e1be444b833475f09de70D38");
        AlipayEbppInvoiceSyncSimpleSendResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return response;*/

        //发票签约
        /*AlipayEbppInvoiceAuthSignRequest request = new AlipayEbppInvoiceAuthSignRequest();
        request.setBizContent("{" +
                "\"user_id\":\"2088399922382233\"," +
                "\"authorization_type\":\"INVOICE_AUTO_SYNC\"," +
                "\"m_short_name\":\"CSD\"" +
                "  }");
        AlipayEbppInvoiceAuthSignResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return response;*/

        //解约接口
        /*AlipayEbppInvoiceAuthUnsignRequest request = new AlipayEbppInvoiceAuthUnsignRequest();
        request.setBizContent("{" +
                "\"user_id\":\"2088399922382233\"," +
                "\"authorization_type\":\"INVOICE_AUTO_SYNC\"," +
                "\"m_short_name\":\"CSD\"" +
                "  }");
        AlipayEbppInvoiceAuthUnsignResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return response;*/


    }


    @RequestMapping(value = "send", method = RequestMethod.GET)
    @ResponseBody
    public AlipayEbppInvoiceInfoSendResponse send() throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(URL, APPID, APP_PRIVATE_KEY,
                FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayEbppInvoiceInfoSendRequest request = new AlipayEbppInvoiceInfoSendRequest();
        request.setBizContent("{" +
                "\"m_short_name\":\"JSDX\"," +
                "\"sub_m_short_name\":\"JSDX_DQ\"," +
                "      \"invoice_info_list\":[{" +
                "        \"user_id\":\"2088102179069203\"," +
                "\"invoice_code\":\"4112740003\"," +
                "\"invoice_no\":\"41791003\"," +
                "\"invoice_date\":\"2019-07-10\"," +
                "\"sum_amount\":\"101.00\"," +
                "\"ex_tax_amount\":\"100.00\"," +
                "\"tax_amount\":\"1.00\"," +
                "          \"invoice_content\":[{" +
                "            \"item_name\":\"餐饮费\"," +
                "\"item_no\":\"1010101990000000000\"," +
                "\"item_spec\":\"G39\"," +
                "\"item_unit\":\"台\"," +
                "\"item_quantity\":1," +
                "\"item_unit_price\":\"100.00\"," +
                "\"item_ex_tax_amount\":\"100.00\"," +
                "\"item_tax_rate\":\"0.01\"," +
                "\"item_tax_amount\":\"1.00\"," +
                "\"item_sum_amount\":\"101.00\"," +
                "\"row_type\":\"0\"" +
                "            }]," +
                "\"out_trade_no\":\"20171023293456785924333\"," +
                "\"invoice_type\":\"BLUE\"," +
                "\"invoice_kind\":\"PLAIN\"," +
                "\"invoice_title\":{" +
                "\"title_name\":\"支付宝（中国）网络技术有限公司\"," +
                "\"payer_register_no\":\"9133010060913454XP\"," +
                "\"payer_address_tel\":\"杭州市西湖区天目山路黄龙时代广场0571-11111111\"," +
                "\"payer_bank_name_account\":\"中国建设银行11111111\"" +
                "        }," +
                "\"payee_register_no\":\"222222222222222\"," +
                "\"payee_register_name\":\"支付宝（杭州）信息技术有限公司\"," +
                "\"payee_address_tel\":\"杭州市西湖区某某办公楼 0571-237405862\"," +
                "\"payee_bank_name_account\":\"西湖区建行11111111111\"," +
                "\"check_code\":\"15170246985745164986\"," +
                "\"out_invoice_id\":\"201710283459661232435535\"," +
                "\"ori_blue_inv_code\":\"4112740002\"," +
                "\"ori_blue_inv_no\":\"41791002\"," +
                "\"file_download_type\":\"PDF\"," +
                "\"file_download_url\":\"https://www.aodacatwzd.cn/downloads/test.pdf\"," +
                "\"payee\":\"张三\"," +
                "\"checker\":\"李四\"," +
                "\"clerk\":\"赵吴\"," +
                "\"invoice_memo\":\"订单号：2017120800001\"," +
                "\"extend_fields\":\"m_invoice_detail_url=https://www.aodacatwzd.cn\"" +
                "        }]" +
                "  }");

        //request.putOtherTextParam("app_auth_token","201908BB20458ec944f4470881551a6324443X20");

        AlipayEbppInvoiceInfoSendResponse alipayEbppInvoiceInfoSendResponse = alipayClient.execute(request);
        return alipayEbppInvoiceInfoSendResponse;
    }

    @RequestMapping(value = "getInfoCode", method = RequestMethod.GET)
    public String getInfoCode() {
        return "redirect:https://openauth.alipaydev.com/oauth2/publicAppAuthorize.htm?app_id=2016101100660137&scope=auth_user,auth_base&redirect_uri=http%3a%2f%2f180.100.223.23:8080/testAli";
    }

    @RequestMapping(value = "getThirdCode", method = RequestMethod.GET)
    public String getThirdCode() {
        return "redirect:https://openauth.alipaydev.com/oauth2/appToAppAuth.htm?app_id=2016101100660137&redirect_uri=http%3a%2f%2f180.100.223.23:8080/testAli";
    }

    @RequestMapping(value = "/surprise", method = RequestMethod.GET)
    public String Hello() {
        return "/index/Hello";
    }
}
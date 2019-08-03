package com.example.demo.MyBlogController;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenAuthTokenAppQueryRequest;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
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

    private static final String URL = "https://openapi.alipay.com/gateway.do";
    private static final String APPID = "2019073066068172";
    private static final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC1ZY55eAXcLyxR\n" +
            "baebwTHjmMBFxWijrV54mLPdlXEVHorfH9gl6Ntoc+EfP5UMiIgskGNulKEfN4an\n" +
            "/5a7TR2GdHjEDKdPCNwwivNV+bKMA2Z74TzLqGV7QCFsHxUj4ucdIupKywMrwn7h\n" +
            "9bLrQy30bqQ9bqTEmSgOGTW9DikLw8O9NJBRG4x1gEEYiw6Rxv9CLIpJ1QJ5cIKA\n" +
            "544a8JEhtrcMP0Rc/ft6DCz3fQMgS6MZDQdU4riQTQnRlXyd/StfrFiD82O+uA0a\n" +
            "k+YxcPT9uLQhBSo7hpoFvtrzqZHK+nK7WqMqHbOibLeRZHJSZCBZa8HiDPORMRmF\n" +
            "rZTeeV3NAgMBAAECggEAIszZ+YnCLgzKxtBvsFzvEkfy0y/dNFGFZ0N8dk0+RZv3\n" +
            "bnjwgc1bkn1wugr7sEzdOxd0S/mts4x2g/Jv230e5fWgWZRH4MzFWXa+2kauL1hT\n" +
            "p/59KGElHvEduF16M8lLJ3bUVgb+k72blAQMEEByJ4u2bKa2a5UqbxH9EkkOLNhO\n" +
            "Eb2rUGrh7ZLJju7myckmO2oKNW0NZrLcs/UcAghaPL9EotcGIIYjaHr0ABMCFLib\n" +
            "pB24GfIG094gwu6Zlf5HoQXb2mrjFwmrRULfEjI9tmB1LQZI0SPf3JSuGb+xQf0n\n" +
            "OP5FO9nPQ4kGnq9vgf/4Aayn89RfMKLqcsPQX5ySQQKBgQDemNMPxzOoybAGwR5c\n" +
            "K7RFl7hIkkVSXW1nou259XaFWi0GzDpQjZVrgisTtuA/wjMZLnd6yjHTdDOlmb6y\n" +
            "zPFm6XZ0B639vMQKZ9S3X7MEFZWUjS4Trgc2SFIUM/Y12h6nqRm2zFfpaMJiP560\n" +
            "RcAXTLVi63BTmAHDJ3byBRp13QKBgQDQngDD89cvq3kZoH2+qZd/49N+Qu15gYwq\n" +
            "6lGLg/cZLM26OY0rM5mX4Es1+zi52BXiaUiucyal1GNpXkX9demWvrTYsNQO7L3n\n" +
            "o8dM2bzNfHwR2CRsOTq2LDgMMRiDTipCtciqhByWQbjzCtVQjxWmbYQQ+HAN13b8\n" +
            "gCueMrdgsQKBgQC5HBXI9Ts8jhzYQRjiRWXwya7yitEjIZatrIxNLJeXZdoz4PpN\n" +
            "qrAra2AvUNFDtFeSBVZOwn6U/flFKYmwX0YQ8u0SqKBEdgoBLT90Dx1rtBdkJdO/\n" +
            "geV1esbnDh8dwXnkq3c60Mv6yqd71LRB7g9EUQI2dNxAaBRvGg6MTTon8QKBgGOi\n" +
            "UrNDjg5SUvAOWn/o91Y5NRUkWc6iNJN6fZ+oUydO4qKCQg3UAxMqKEGLzjfUH/+W\n" +
            "UQQgMuEYYrI8OCkpW3qHck/bhCvsnXY4HkNx6l4pigfrttmJK6U90Tbha9eqSTy7\n" +
            "HS9zEUQh93b8QYzMCYcG3wBL5xh1Q3qsrChOCI7BAoGBAIvrez8oo56Q/whaQ5FR\n" +
            "NLdtP02n1o0f7nrkWajjYHe11abE9A+ycdBMzGxvPfESLVAkse0lkI1DeKqVaPxg\n" +
            "NYwTBzpuE7hz1suwoIgtQM50TuXWtgZqIuOps4OACjL5Y4otxlQjRuddHNM9Lg/s\n" +
            "hEtsXajPPqsBWGlpil6wP1by";
    private static final String FORMAT = "json";
    private static final String CHARSET = "UTF-8";
    //private static final String APP_PRIVATE_KEY2="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC1ZY55eAXcLyxRbaebwTHjmMBFxWijrV54mLPdlXEVHorfH9gl6Ntoc+EfP5UMiIgskGNulKEfN4an/5a7TR2GdHjEDKdPCNwwivNV+bKMA2Z74TzLqGV7QCFsHxUj4ucdIupKywMrwn7h9bLrQy30bqQ9bqTEmSgOGTW9DikLw8O9NJBRG4x1gEEYiw6Rxv9CLIpJ1QJ5cIKA544a8JEhtrcMP0Rc/ft6DCz3fQMgS6MZDQdU4riQTQnRlXyd/StfrFiD82O+uA0ak+YxcPT9uLQhBSo7hpoFvtrzqZHK+nK7WqMqHbOibLeRZHJSZCBZa8HiDPORMRmFrZTeeV3NAgMBAAECggEAIszZ+YnCLgzKxtBvsFzvEkfy0y/dNFGFZ0N8dk0+RZv3bnjwgc1bkn1wugr7sEzdOxd0S/mts4x2g/Jv230e5fWgWZRH4MzFWXa+2kauL1hTp/59KGElHvEduF16M8lLJ3bUVgb+k72blAQMEEByJ4u2bKa2a5UqbxH9EkkOLNhOEb2rUGrh7ZLJju7myckmO2oKNW0NZrLcs/UcAghaPL9EotcGIIYjaHr0ABMCFLibpB24GfIG094gwu6Zlf5HoQXb2mrjFwmrRULfEjI9tmB1LQZI0SPf3JSuGb+xQf0nOP5FO9nPQ4kGnq9vgf/4Aayn89RfMKLqcsPQX5ySQQKBgQDemNMPxzOoybAGwR5cK7RFl7hIkkVSXW1nou259XaFWi0GzDpQjZVrgisTtuA/wjMZLnd6yjHTdDOlmb6yzPFm6XZ0B639vMQKZ9S3X7MEFZWUjS4Trgc2SFIUM/Y12h6nqRm2zFfpaMJiP560RcAXTLVi63BTmAHDJ3byBRp13QKBgQDQngDD89cvq3kZoH2+qZd/49N+Qu15gYwq6lGLg/cZLM26OY0rM5mX4Es1+zi52BXiaUiucyal1GNpXkX9demWvrTYsNQO7L3no8dM2bzNfHwR2CRsOTq2LDgMMRiDTipCtciqhByWQbjzCtVQjxWmbYQQ+HAN13b8gCueMrdgsQKBgQC5HBXI9Ts8jhzYQRjiRWXwya7yitEjIZatrIxNLJeXZdoz4PpNqrAra2AvUNFDtFeSBVZOwn6U/flFKYmwX0YQ8u0SqKBEdgoBLT90Dx1rtBdkJdO/geV1esbnDh8dwXnkq3c60Mv6yqd71LRB7g9EUQI2dNxAaBRvGg6MTTon8QKBgGOiUrNDjg5SUvAOWn/o91Y5NRUkWc6iNJN6fZ+oUydO4qKCQg3UAxMqKEGLzjfUH/+WUQQgMuEYYrI8OCkpW3qHck/bhCvsnXY4HkNx6l4pigfrttmJK6U90Tbha9eqSTy7HS9zEUQh93b8QYzMCYcG3wBL5xh1Q3qsrChOCI7BAoGBAIvrez8oo56Q/whaQ5FRNLdtP02n1o0f7nrkWajjYHe11abE9A+ycdBMzGxvPfESLVAkse0lkI1DeKqVaPxgNYwTBzpuE7hz1suwoIgtQM50TuXWtgZqIuOps4OACjL5Y4otxlQjRuddHNM9Lg/shEtsXajPPqsBWGlpil6wP1by";
    private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhms+QaVT0LxhitMz/dlxSlww/B3TUIKpbkY/ZQXuy4RFg84moX4ZgCHNXgxhnpc/GU0c8P2MEvEDiFUELqMBi7BipL/CQkmPew93KG6ieY/wsLwxLZXmH6cdMxu0FV34uQKxACcIBqDGGG5MzHklsGbhyKowf34VHxobeWTEcwr8qqhFSfWJ2YxyfVhkfEYwomMF6N4GhhWP9Bq9VT0vbeejL5YngaP4/TTMosrfMeYtya0YlFs8bYi+Iz/reW0WS825VytAptSDl2lVlAQTXhz1ZIlHJCiFXRhpt2mQvzOxGLuHyP3UTytwgv/ULfQ47Q8oTTZoMAoreXaMurAcPwIDAQAB";
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
    public String InsertComment(@ModelAttribute CommentService commentService1, Model model, HttpServletRequest request, @PathVariable("id") Integer id,HttpSession httpSession) {

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
        if (null!=userServiceList.get(0)) {
            if (userServiceList.get(0).getPasswordMD5().equals(MD5Util.md5(userService1.getPasswordMD5()))) {
                System.out.println("yes");
                httpSession.setAttribute("username", userService1.getUserName());
                return "redirect:"+httpSession.getAttribute("url");
            } else {
                map.put("msg","账号/密码错误");
                return "index/login";
            }
        } else {
            return "index/login";
        }
    }

    @RequestMapping(value = "testAli",method = RequestMethod.GET)
    @ResponseBody
    public AlipayOpenAuthTokenAppQueryResponse get(HttpServletRequest httpServletRequest) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(URL,APPID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE);
        /*AlipayClient alipayClient = new DefaultAlipayClient(URL, APPID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);

       //开复杂发票
        AlipayEbppInvoiceInfoSendRequest request = new AlipayEbppInvoiceInfoSendRequest();
        request.setBizContent("{" +
                "\"m_short_name\":\"XSD\"," +
                "\"sub_m_short_name\":\"XSD_HL\"," +
                "      \"invoice_info_list\":[{" +
                "        \"user_id\":\"2088422459852830\"," +
                "\"apply_id\":\"2019073066068172\"," +
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
                "\"out_trade_no\":\"20171023293456785924325\"," +
                "\"invoice_type\":\"BLUE\"," +
                "\"invoice_kind\":\"PLAIN\"," +
                "\"invoice_title\":{" +
                "\"title_name\":\"支付宝（中国）网络技术有限公司\"," +
                "\"payer_register_no\":\"9133010060913454XP\"," +
                "\"payer_address_tel\":\"杭州市西湖区天目山路黄龙时代广场0571-11111111\"," +
                "\"payer_bank_name_account\":\"中国建设银行11111111\"" +
                "        }," +
                "\"payee_register_no\":\"310101000000090\"," +
                "\"payee_register_name\":\"支付宝（杭州）信息技术有限公司\"," +
                "\"payee_address_tel\":\"杭州市西湖区某某办公楼 0571-237405862\"," +
                "\"payee_bank_name_account\":\"西湖区建行11111111111\"," +
                "\"check_code\":\"15170246985745164986\"," +
                "\"out_invoice_id\":\"201710283459661232435535\"," +
                "\"ori_blue_inv_code\":\"4112740002\"," +
                "\"ori_blue_inv_no\":\"41791002\"," +
                "\"file_download_type\":\"PDF\"," +
                "\"file_download_url\":\"http://img.hadalo.com/aa/kq/ddhrtdefgxKVXXXXa6apXXXXXXXXXX.pdf\"," +
                "\"payee\":\"张三\"," +
                "\"checker\":\"李四\"," +
                "\"clerk\":\"赵吴\"," +
                "\"invoice_memo\":\"订单号：2017120800001\"," +
                "\"extend_fields\":\"m_invoice_detail_url=http://196.021.871.011:8080/invoice/detail.action?fpdm= 4112740003&fphm=41791003\"" +
                "        }]" +
                "  }");*/


        //获取个人信息的auth_code auth_token userID
        /*String auth_code = httpServletRequest.getParameter("auth_code");
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
        System.out.println("app_auth_code:"+app_auth_code);

        AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
        request.setBizContent("{" +
                "    \"grant_type\":\"authorization_code\"," +
                "    \"code\":\""+app_auth_code+"\"" +
                "  }");
        AlipayOpenAuthTokenAppResponse alipayOpenAuthTokenAppResponse = alipayClient.execute(request);
        String userID = alipayOpenAuthTokenAppResponse.getUserId();
        System.out.println("userID:"+userID);
        String app_auth_token = alipayOpenAuthTokenAppResponse.getAppAuthToken();
        System.out.println("app_auth_token:"+app_auth_token);
        AlipayOpenAuthTokenAppQueryRequest alipayOpenAuthTokenAppQueryRequest = new AlipayOpenAuthTokenAppQueryRequest();
        alipayOpenAuthTokenAppQueryRequest.setBizContent("{" +
                "\"app_auth_token\":\""+app_auth_token+"\"" +
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
                "\"m_short_name\":\"XSD\"," +
                "\"sub_m_short_name\":\"XSD_HL\"," +
                "\"invoice_info\":{" +
                "\"user_id\":\"2088422459852830\"," +
                "\"out_invoice_id\":\"2088100022223333\"," +
                "\"file_download_url\":\"http://img.hadalo.com/aa/kq/ddhrtdefgxKVXXXXa6apXXXXXXXXXX.pdf\"," +
                "\"file_download_type\":\"PDF\"," +
                "\"extend_fields\":\"m_invoice_detail_url=http://127.0.0.1:8080/invoice/detail.action?fpdm= 1234567&fphm=123456\"" +
                "    }" +
                "  }");
        request.putOtherTextParam("app_auth_token", "201907BB11322d01f1c246c2a93322b9338cbX83");
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

    @RequestMapping(value = "getInfoCode",method = RequestMethod.GET)
    public String getInfoCode() {
        return "redirect:https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2019073066068172&scope=auth_user,auth_base&redirect_uri=http%3a%2f%2f180.100.223.23:8080/testAli";
    }

    @RequestMapping(value = "getThirdCode",method = RequestMethod.GET)
    public String getThirdCode(){
        return "redirect:https://openauth.alipay.com/oauth2/appToAppAuth.htm?app_id=2019073066068172&redirect_uri=http%3a%2f%2f180.100.223.23:8080/testAli";
    }

    @RequestMapping(value = "/surprise", method = RequestMethod.GET)
    public String Hello() {
        return "/index/Hello";
    }
}
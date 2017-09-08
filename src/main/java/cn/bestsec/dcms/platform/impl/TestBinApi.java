package cn.bestsec.dcms.platform.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestBinApi {
    
    
    @RequestMapping(value = "http://localhost:8080/dcms/bin_api/hello")
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("TestBinApi "+"Hello SpringMvc");
    }

}

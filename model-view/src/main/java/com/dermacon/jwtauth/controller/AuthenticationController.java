package com.dermacon.jwtauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthenticationController {

    // todo put this method is init class ???
    /**
     * Useful to create a singleton instance because this can be used throughout the project
     * Loadbalancer annotation tells the template to interpret the given url with the eureka
     * service discovery tool
     * @return generated Resttemplate
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/check")
    public String test() {
        String out = restTemplate.getForObject("http://token-provider/health", String.class);
        System.out.println(" ----------------------> out: " + out);
        return "test";
    }

    @RequestMapping("/new-login")
    public String newLogin(Model model) {
        model.addAttribute("inputCredentials", new InputCredentials());
        return "login-view";
    }

    @RequestMapping("/test")
    public String testView() {
        return "test";
    }


    @RequestMapping("/cookie")
    public String testCookie(HttpServletRequest req,  HttpServletResponse response) {
        // create a cookie
        Cookie cookie = new Cookie("test_cookie", "testtest");

        // expires in 7 days
        cookie.setMaxAge(7 * 24 * 60 * 60);

        // optional properties
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        // add cookie to response
        response.addCookie(cookie);
        return "works";
    }

}

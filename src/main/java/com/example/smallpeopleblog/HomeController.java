package com.example.smallpeopleblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// 1. 원격 프로그램으로 등록
@Controller
public class HomeController {

    // 2. URL과 메서드를 연결
    @RequestMapping("/")
    public String main() {
        return "index";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/products")
    public String products() {
        return "products";
    }

    @RequestMapping("/store")
    public String store() {
        return "store";
    }

}

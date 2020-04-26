package pl.com.bohdziewicz.trelloDbTask.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticWEbPageController {

    @RequestMapping("/")
    public String index(Map<String, String> model) {

        model.put("variable", "My Thymleaf variable");

        return "index";
    }
}

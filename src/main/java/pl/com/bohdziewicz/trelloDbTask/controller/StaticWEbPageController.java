package pl.com.bohdziewicz.trelloDbTask.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticWEbPageController {

    @RequestMapping("/thymleaf_practice")
    public String index(Map<String, Object> model) {

        model.put("variable", "My Thymleaf variable");
        model.put("one", 1);
        model.put("two", 2);
        model.put("textVariable", "testText");

        return "thymleaf_practice";
    }
}

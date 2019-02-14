package ch.napohaku.playground.api.books;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/booksa")
public class BookController {

    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    @Value("${name}")
    private String name;

    @Value("${version}")
    private String version;

    @RequestMapping(value = "property", method = RequestMethod.GET)
    public HashMap getProperty() {
        HashMap value = new HashMap();
        value.put("version", name + " from config" + version);
        return value;
    }

}
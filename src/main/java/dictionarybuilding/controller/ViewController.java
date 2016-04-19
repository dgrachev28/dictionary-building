package dictionarybuilding.controller;

import dictionarybuilding.dao.DocumentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ViewController {

    @Autowired
    private DocumentDao documentDao;


    @RequestMapping(value = "/findEntrance")
    public String findEntrance(@RequestParam String verb) {
        String text = documentDao.getText();
        return "";
    }

}

package dictionarybuilding.controller;

import dictionarybuilding.dao.DocumentDao;
import dictionarybuilding.service.MystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class ViewController {

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private MystemService mystemService;


    @RequestMapping(value = "/findEntrance")
    public String findEntrance(@RequestParam String verb, HttpSession session) {
//        String text = documentDao.getText();
        String realPath = session.getServletContext().getRealPath("");
        mystemService.run(realPath);
        return "";
    }

}

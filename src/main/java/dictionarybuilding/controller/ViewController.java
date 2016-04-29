package dictionarybuilding.controller;

import dictionarybuilding.dao.DocumentDao;
import dictionarybuilding.model.VerbUse;
import dictionarybuilding.service.AddDocumentService;
import dictionarybuilding.service.MystemService;
import dictionarybuilding.service.RealPathService;
import dictionarybuilding.service.VerbEntranceService;
import dictionarybuilding.web.Response;
import dictionarybuilding.web.VerbDescription;
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
    private MystemService mystemService;

    @Autowired
    private AddDocumentService addDocumentService;

    @Autowired
    private VerbEntranceService verbEntranceService;

    @RequestMapping(value = "/findEntrance")
    public Response findEntrance(@RequestParam String verb, HttpSession session) {
        RealPathService.setPaths(session.getServletContext().getRealPath(""));
        mystemService.run();
        addDocumentService.test();
        VerbUse result = verbEntranceService.getNextVerbEntrance(verb);
        VerbDescription verbDescription = new VerbDescription();
        verbDescription.setVerb(result.getVerb());
        verbDescription.setSentence(result.getSentence().getText());
        System.out.println(result.getSentence().getText());
        return verbDescription;
    }

}

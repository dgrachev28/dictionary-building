package dictionarybuilding.controller;

import dictionarybuilding.service.AddDocumentService;
import dictionarybuilding.service.MystemService;
import dictionarybuilding.service.RealPathService;
import dictionarybuilding.service.VerbEntranceService;
import dictionarybuilding.web.Response;
import dictionarybuilding.web.model.EmptyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return verbEntranceService.getNextVerbEntrance(verb);
    }

    @RequestMapping(value = "/addDocument")
    public Response addDocument(HttpSession session) {
        RealPathService.setPaths(session.getServletContext().getRealPath(""));
        mystemService.run();
        addDocumentService.addDocument();
        return new EmptyResponse();
    }

    @RequestMapping(value = "/clearVerbs")
    public Response clearVerbs() {
        addDocumentService.clearVerbs();
        return new EmptyResponse();
    }


}

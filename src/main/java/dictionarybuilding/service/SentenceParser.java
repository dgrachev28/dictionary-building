package dictionarybuilding.service;

import dictionarybuilding.model.Sentence;
import dictionarybuilding.web.AppException;
import dictionarybuilding.web.model.Word;
import dictionarybuilding.web.model.WordVariant;
import org.apache.xmlbeans.impl.soap.Node;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SentenceParser {

    public List<Word> parse(Sentence sentence) {
        try {
            InputSource source = new InputSource(new StringReader(sentence.getXml()));

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(source);

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();

            NodeList delimiters = (NodeList) xpath.evaluate("/xml-fragment/text()", document, XPathConstants.NODESET);
            NodeList words = (NodeList) xpath.evaluate("/xml-fragment/w", document, XPathConstants.NODESET);

            List<Word> result = new ArrayList<Word>();

            for (int i = 1; i < delimiters.getLength(); i++) {
                String delimiter = delimiters.item(i).getTextContent().replaceAll("\n", "").trim() + " ";

                Element word = (Element) words.item(i - 1);
                String wordStr = word.getTextContent().replaceAll("\n", "").trim();

                NodeList wordVariants = word.getChildNodes();
                List<WordVariant> resultWordVariants = new ArrayList<WordVariant>();
                for (int j = 0; j < wordVariants.getLength(); j++) {
                    if (wordVariants.item(j).getNodeType() != Node.ELEMENT_NODE) {
                        continue;
                    }
                    Element wordVariant = (Element) wordVariants.item(j);
                    String lexeme = wordVariant.getAttribute("lex");
                    Double weight = Double.parseDouble(wordVariant.getAttribute("wt"));
                    String gramInfo = wordVariant.getAttribute("gr");
                    resultWordVariants.add(new WordVariant(lexeme, weight, gramInfo));
                }
                Collections.sort(resultWordVariants);
                result.add(new Word(wordStr, resultWordVariants, delimiter));
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(e.getMessage());
        }
    }

}

package dictionarybuilding.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class MystemService {

    private String resourcesFolder;
    private String mystemPath;


    public void run(String realPath) {
        this.resourcesFolder = realPath + "/WEB-INF/classes/file/";
        this.mystemPath = realPath + "/WEB-INF/classes/lib/mystem/mystem.exe";
        runMystem("input.txt", "output.xml");
    }

    private void runMystem(String inFileName, String outFileName) {
        try {
            String inFile = resourcesFolder + inFileName;
            String outFile = resourcesFolder + outFileName;
            ProcessBuilder procBuilder = new ProcessBuilder(
                    mystemPath, "-ni", "--format", "xml", "--eng-gr", inFile, outFile);
            procBuilder.redirectErrorStream(true);


            Process process = procBuilder.start();
            InputStream stdout = process.getInputStream();
            InputStreamReader isrStdout = new InputStreamReader(stdout);
            BufferedReader brStdout = new BufferedReader(isrStdout);

            String programLine = null;
            while ((programLine = brStdout.readLine()) != null) {
                System.out.println(programLine);
            }

            int exitVal = process.waitFor();

            if (exitVal != 0) {
                throw new Exception("Программа mystem не завершилась с кодом 0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package dictionarybuilding.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class MystemService {

    public void run() {
        runMystem("input.txt", "mystem_out.xml");
    }

    private void runMystem(String inFileName, String outFileName) {
        try {
            String inFile = RealPathService.resourcesFolder + inFileName;
            String outFile = RealPathService.resourcesFolder + outFileName;
            ProcessBuilder procBuilder = new ProcessBuilder(
                    RealPathService.mystemPath, "-nic", "--weight", "--format", "xml", "--eng-gr", inFile, outFile);
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

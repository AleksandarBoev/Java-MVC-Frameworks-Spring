package real_estate_agency_app.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
public class MyFileReaderImpl implements MyFileReader {

    @Override
    public String getContentFromRelativePath(String relativePath) throws IOException {
        File file = new ClassPathResource(relativePath).getFile();
        StringBuilder sb = new StringBuilder();
        BufferedReader fileReader = new BufferedReader(new FileReader(file));

        String currentLine;
        while ((currentLine = fileReader.readLine()) != null) {
            sb.append(currentLine).append(System.lineSeparator());
        }

        fileReader.close();
        return sb.toString();
    }
}

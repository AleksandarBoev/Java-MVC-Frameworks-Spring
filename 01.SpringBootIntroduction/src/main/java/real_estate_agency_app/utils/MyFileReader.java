package real_estate_agency_app.utils;

import java.io.IOException;

public interface MyFileReader {
    String getContentFromRelativePath(String relativePath) throws IOException;
}

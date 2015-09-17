package DTExpansion;

import java.io.IOException;

/**
 * Created by krayush on 27-08-2015.
 */
public class DTCOOCLexiconWrapper {
    public static void main(String[] args)throws IOException
    {
        new SendHTTPRequest();
        new GetDTTopCandidateWordsHindi();
        new GetDTTopWordsPolarityHindi();
        new GetCOOCPolarityHindi();
        new ComparePolaritiesHindi();
    }

}

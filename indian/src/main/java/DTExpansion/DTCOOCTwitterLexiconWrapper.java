package DTExpansion;

import java.io.IOException;

/**
 * Created by krayush on 27-08-2015.
 */
public class DTCOOCTwitterLexiconWrapper {
    public static void main(String[] args)throws IOException
    {
        //new SendHTTPRequest();
        new GetDTTopCandidateWordsHindiTwitter();
        new GetDTTopWordsPolarityHindiTwitter();
        new GetCOOCPolarityHindiTwitter();
        new ComparePolaritiesHindiTwitter();
    }
}

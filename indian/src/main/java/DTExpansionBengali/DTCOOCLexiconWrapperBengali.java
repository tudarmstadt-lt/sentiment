package DTExpansionBengali;

import java.io.IOException;

/**
 * Created by krayush on 27-08-2015.
 */
public class DTCOOCLexiconWrapperBengali {
    public static void main(String[] args)throws IOException
    {
        new SendHTTPRequest();
        new GetDTTopCandidateWordsBengali();
        new GetDTTopWordsPolarityBengali();
        new GetCOOCPolarityBengali();
        new ComparePolaritiesBengali();
    }

}

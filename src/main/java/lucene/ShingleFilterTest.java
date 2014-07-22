package lucene;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.StringReader;

public class ShingleFilterTest {

    public static void main(String[] args) throws IOException {

        String theSentence = "this is a big dog";
        StringReader reader = new StringReader(theSentence);
        StandardTokenizer source = new StandardTokenizer(Version.LUCENE_4_9, reader);
        TokenStream tokenStream = new StandardFilter(Version.LUCENE_4_9, source);
        ShingleFilter sf = new ShingleFilter(tokenStream, 2, 100);
        sf.setOutputUnigrams(false);

        CharTermAttribute charTermAttribute = sf.addAttribute(CharTermAttribute.class);
        sf.reset();

        while (sf.incrementToken()) {
            System.out.println(charTermAttribute.toString());
        }

        sf.end();
        sf.close();
    }
}

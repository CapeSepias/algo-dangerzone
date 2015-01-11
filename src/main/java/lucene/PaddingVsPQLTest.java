package lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.AnalyzerWrapper;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.epo.presto.pql.translation.lucene.util.PQLSpanMultiTermQueryWrapper;
import org.epo.presto.pql.translation.lucene.util.TextRangeQuery;

import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

public class PaddingVsPQLTest {

    public static void main(String[] args) {
        Directory dir = null;
        IndexReader reader = null;
        try {
            dir = new RAMDirectory();
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_9, new WhitespaceAnalyzer(Version.LUCENE_4_9));
            config.setMergePolicy(new LogDocMergePolicy()); // we might use docids to validate
            IndexWriter writer = new IndexWriter(dir, config);

            writer.addDocument(doc(1, "Things with a melting point of 250 degrees."));
            writer.addDocument(doc(2, "Things with a resistance of 500 ohms."));
            writer.addDocument(doc(3, "Things with a melting point of 500 degrees."));
            writer.addDocument(doc(4, "Things with a melting point of 700 degrees."));
            writer.addDocument(doc(5, "Things with a melting point of 950 degrees."));
            writer.addDocument(doc(6, "Things with a melting point of a million degrees."));
            writer.addDocument(doc(7, "Things with a melting point of 701 degrees."));
            writer.addDocument(doc(8, "Things with a melting point of 499 degrees."));
            writer.addDocument(doc(9, "Things with a melting point of 499.9 degrees."));
            writer.addDocument(doc(10, "Things with a melting point of 500.0 degrees."));
            writer.addDocument(doc(11, "Things with a melting point of 500.1 degrees."));
            writer.addDocument(doc(12, "Things with a melting point of 700.0 degrees."));
            writer.addDocument(doc(13, "Things with a melting point of 700.1 degrees."));
            writer.addDocument(doc(14, "Things with a resistance of 560 ohms."));
            writer.addDocument(doc(15, "Things with a melting point of 6100.0 degrees."));
            writer.addDocument(doc(16, "Things with a melting point of 6200 degrees."));

            reader = DirectoryReader.open(writer, false);
            writer.close();

            IndexSearcher searcher = new IndexSearcher(reader);
            TopScoreDocCollector collector = TopScoreDocCollector.create(10, true);
            Query query = new PQLSpanMultiTermQueryWrapper<>(new TextRangeQuery(new Term("field", ""), -1000, 1000, null));
            searcher.search(query, collector);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignore) {
                }
            }
            if (dir != null) {
                try {
                    dir.close();
                } catch (IOException ignore) {
                }
            }

        }
    }

    private static Document doc(int id, String text) {
        Document d = new Document();
        d.add(new IntField("id", id, Field.Store.YES));
        d.add(new TextField("text", text, Field.Store.YES));
        return d;
    }

}

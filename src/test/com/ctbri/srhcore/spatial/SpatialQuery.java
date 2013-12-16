package test.com.ctbri.srhcore.spatial;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.spatial.SpatialStrategy;
import org.apache.lucene.spatial.prefix.RecursivePrefixTreeStrategy;
import org.apache.lucene.spatial.prefix.tree.GeohashPrefixTree;
import org.apache.lucene.spatial.prefix.tree.SpatialPrefixTree;
import org.apache.lucene.spatial.query.SpatialArgs;
import org.apache.lucene.spatial.query.SpatialOperation;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.ctbri.segment.U;
import com.ctbri.srhcore.pojo.POIObject;
import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import com.spatial4j.core.shape.Point;

public class SpatialQuery {
	

	private static SpatialContext ctx=SpatialContext.GEO;
	private static SpatialStrategy strategy;
	private static IndexSearcher indexSearcher;
	static{
		Directory directory;
		try {
//			directory = FSDirectory.open(new File("C:\\Documents and Settings\\taoyj\\My Documents\\temp0507\\filter\\110000"));
			directory = FSDirectory.open(new File("index/110000/"));
			SpatialPrefixTree grid = new GeohashPrefixTree(ctx, 11);
			strategy = new RecursivePrefixTreeStrategy(grid, "geoField");
			IndexReader indexReader = DirectoryReader.open(directory);
			indexSearcher = new IndexSearcher(indexReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String args[]) throws IOException{
		matchAllByCircle();
//		matchAllByDistence();
//		test();
	}
	public static void matchAllByDistence() throws IOException{
		Point pt = ctx.makePoint(116.02837361,39.13111583);
		System.out.println(pt);
		ValueSource valueSource = strategy.makeDistanceValueSource(pt);//the distance (in degrees)
		Sort reverseDistSort = new Sort(valueSource.getSortField(false)).rewrite(indexSearcher);//true=asc dist
		TopDocs docs = indexSearcher.search(new MatchAllDocsQuery(), 10, reverseDistSort);
		show(docs);
		ctx.getDistCalc().distance(ctx.makePoint(0.0, 0.0),ctx.makePoint(0.0, 0.0));
	}
	public static void matchAllByCircle() throws IOException{
	      double dis= DistanceUtils.dist2Degrees(10, DistanceUtils.EARTH_MEAN_RADIUS_KM);
	        SpatialArgs args = new SpatialArgs(SpatialOperation.Intersects,
	        ctx.makeCircle(116.4664403,39.94166283,dis));
	        Query q=strategy.makeQuery(args);
//	        System.out.println(args.toString());
	        System.out.println(q);
	        BooleanQuery bq=new BooleanQuery();
	        bq.add(q, Occur.MUST);
	        bq.add(new TermQuery(new Term("typeCode","100")), Occur.MUST);
	        System.out.println(bq);
	        Point pt=ctx.makePoint(116.4664403,39.94166283);
	        ValueSource valueSource = strategy.makeDistanceValueSource(pt);//the distance (in degrees)
	        Sort reverseDistSort = new Sort(valueSource.getSortField(false)).rewrite(indexSearcher);//true=asc dist
	        TopDocs docs = indexSearcher.search(bq, null, 10,reverseDistSort);
	        show(docs);
	}
	public static void test() throws IOException{
		 SpatialContext ctx = SpatialContext.GEO;
	        SpatialStrategy strategy = new RecursivePrefixTreeStrategy(new GeohashPrefixTree(ctx, 11), "geoField");
//	        String file = "./filter/index/110000/110000";
	        String file = "./index/140000/140900/";
	        IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(file)));
	        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
	        Point pt = ctx.makePoint(116.253448,39.971478);
	        ValueSource valueSource = strategy.makeDistanceValueSource(pt);//the distance (in degrees)
	        Sort reverseDistSort = new Sort(valueSource.getSortField(false)).rewrite(indexSearcher);//true=asc dist
	        TopDocs docs = indexSearcher.search(new MatchAllDocsQuery(), 10, reverseDistSort);
	        System.out.println(docs.totalHits);
	}
	public static void show(TopDocs docs) throws IOException{
		System.out.println(docs.totalHits);
		ScoreDoc[] sd=docs.scoreDocs;
		List<POIObject> list = new ArrayList<POIObject>();
		for(int i=0;i<sd.length;i++){
//			System.out.println(indexSearcher.explain(new MatchAllDocsQuery(), sd[i].doc).toString());
			Document doc = indexSearcher.doc(sd[i].doc);
			System.out.println(indexSearcher.doc(sd[i].doc));
			POIObject poi = new POIObject();
			poi.setLDoc(doc);
//			poi.setScore(hits.score(j));
//			if(poi.getFieldValue("invol") == null || poi.getFieldValue("invol").length()==0)
//				poi.setFieldValue("invol", 1+"");
			if(poi.getRank() == 0)
				poi.setRank(1);
			list.add(poi);			
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println("name: "+list.get(i).getName());
			
		}
	}
	


}

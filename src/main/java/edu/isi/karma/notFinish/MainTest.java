package edu.isi.karma.notFinish;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import edu.isi.karma.support.ApplyHistorySupport;
import edu.isi.karma.util.DBType;
import edu.isi.karma.util.FileUtil;
import edu.isi.karma.util.SparqlUtil;
import edu.isi.karma.webserver.KarmaException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 4535992 on 30/11/2015.
 */
public class MainTest {

    public static void main(String args[]) throws IOException, KarmaException {
        test2();
    }

    private static void test2() throws IOException, KarmaException {
        File r2rml = new File("" +
                "C:\\Users\\tenti\\Desktop\\Marco Utility\\TESI 2015-09-30\\Web-Karma-20151130\\karma-modelSupport\\src\\main\\java\\edu\\isi\\karma\\test\\R2RML_infodocument-model_2015-07-08.ttl");
        ApplyHistorySupport support =  ApplyHistorySupport.getInstance();
        JSONArray array = support.extractWorkSheetHistory("infodocument_2015_09_18", r2rml);

        File outputJson = new File(
                "C:\\Users\\tenti\\Desktop\\Marco Utility\\TESI 2015-09-30\\Web-Karma-20151130\\karma-modelSupport\\src\\main\\java\\edu\\isi\\karma\\test\\output.json");
        FileUtil.writePrettyPrintedJSONObjectToFile(new JSONObject().put("WorkSheetHistory", (Object) array),outputJson);
    }

    private void test1() throws SQLException, ClassNotFoundException {
        String content ="";
        ModelWebKarma model = new ModelWebKarma(
                "localhost", 3306, "siimobility", "siimobility","infodocument_2015_09_18", "geodb",
                DBType.MySQL,"http://purl.org/goodrelations/v1#");

        File fileTest = new File("C:\\Users\\tenti\\Desktop\\EAT\\modelWebKarma\\src\\main\\java\\test\\test.txt");
        String tablename = "infodocument_2015_09_18";
        //model.createWoskSheetHistory(tablename,fileTest.getAbsolutePath());

        List<ModelColumn> list = new ArrayList<ModelColumn>();
        ModelColumn m1 = new ModelColumn("http://schema.org/url",
                "http://purl.org/goodrelations/v1#BusinessEntity",null,"url",true,true,false,false, XSDDatatype.XSDstring);
        ModelColumn m2 = new ModelColumn("http://schema.org/addressRegion",
                "http://purl.org/goodrelations/v1#BusinessEntity",null,"provincia",true,true,false,false,XSDDatatype.XSDstring);
        ModelColumn m3 = new ModelColumn("http://schema.org/addressLocality",
                "http://purl.org/goodrelations/v1#BusinessEntity",null,"city",true,true,false,false,XSDDatatype.XSDstring);
        ModelColumn m4 = new ModelColumn("http://schema.org/streetAddress",
                "http://purl.org/goodrelations/v1#BusinessEntity",null,"indirizzo",true,true,false,false,XSDDatatype.XSDstring);
        ModelColumn m5 = new ModelColumn("http://purl.org/goodrelations/v1#vatID",
                "http://purl.org/goodrelations/v1#BusinessEntity",null,"iva",true,true,false,false,XSDDatatype.XSDstring);
        ModelColumn m6 = new ModelColumn("http://schema.org/email",
                "http://purl.org/goodrelations/v1#BusinessEntity",null,"email",true,true,false,false,XSDDatatype.XSDstring);
        ModelColumn m7 = new ModelColumn("http://schema.org/telephone",
                "http://purl.org/goodrelations/v1#BusinessEntity",null,"telefono",true,true,false,false,XSDDatatype.XSDstring);
        ModelColumn m8 = new ModelColumn("http://schema.org/faxNumber",
                "http://purl.org/goodrelations/v1#BusinessEntity",null,"fax",true,true,false,false,XSDDatatype.XSDstring);
        ModelColumn m9 = new ModelColumn("http://purl.org/goodrelations/v1#legalName",
                "http://purl.org/goodrelations/v1#BusinessEntity",null,"edificio",true,true,false,false,XSDDatatype.XSDstring);
        ModelColumn m10 = new ModelColumn("http://schema.org/addressCountry",
                "http://purl.org/goodrelations/v1#BusinessEntity",null,"nazione",true,true,false,false,XSDDatatype.XSDstring);
        ModelColumn m11 = new ModelColumn("http://www.w3.org/2004/02/skos/core#note",
                "http://purl.org/goodrelations/v1#BusinessEntity",null,"description",true,true,false,false,XSDDatatype.XSDstring);
        ModelColumn m12 = new ModelColumn("http://schema.org/postalCode",
                "http://purl.org/goodrelations/v1#BusinessEntity",null,"postalCode",true,true,false,false,XSDDatatype.XSDstring);


        ModelColumn c1 = new ModelColumn("http://schema.org/latitude",
                "http://purl.org/goodrelations/v1#Location",null,"latitude",true,true,false,false,XSDDatatype.XSDstring);
        ModelColumn c2 = new ModelColumn("http://schema.org/longitude",
                "http://purl.org/goodrelations/v1#Location",null,"longitude",true,true,false,false,XSDDatatype.XSDstring);

        content += SparqlUtil.preparePrefix("@")+"\n";



       /* ModelSemanticTable table = new ModelSemanticTable(SQLHelper.getCurrentConnection(),tablename);
        for(String column : model.getColumns(SQLHelper.getCurrentConnection(),tablename)){
            if(column == "longitude" || column == "latitude"){}
            content += model.createWoskSheetHistory(ModelSemanticType);
        }
*/
    }
}

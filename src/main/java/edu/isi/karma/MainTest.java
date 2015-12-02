package edu.isi.karma;


import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import edu.isi.karma.controller.history.HistoryJsonUtil;
import edu.isi.karma.kr2rml.mapping.KR2RMLMapping;
import edu.isi.karma.support.ApplyMappingSupport;
import edu.isi.karma.util.HistoryJsonUtility;
import edu.isi.karma.util.SupportUtil;
import edu.isi.karma.webserver.KarmaException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTest {

    private static void l(Object... obj){
        System.out.println(Arrays.toString(obj));
    }

    public static void main(String args[]) throws IOException, KarmaException {
        File r2rml = new File("" +
                "C:\\Users\\tenti\\Desktop\\Marco Utility\\TESI 2015-09-30\\Web-Karma-20151130\\karma-modelSupport\\src\\main\\java\\edu\\isi\\karma\\test\\R2RML_infodocument-model_2015-07-08.ttl");

        File outputJson = new File(
                "C:\\Users\\tenti\\Desktop\\Marco Utility\\TESI 2015-09-30\\Web-Karma-20151130\\karma-modelSupport\\src\\main\\java\\edu\\isi\\karma\\test\\output.json");

        //GET WORKSHEET HISTORY
        boolean isOffline = SupportUtil.isOffline("localhost", 8181);
        ModelWebKarmaSupport model =  ModelWebKarmaSupport.getInstance(isOffline);
        KR2RMLMapping mapping = model.prepareKR2RMLMapping("infodocument_2015_09_18", r2rml);
        JSONArray historyJson = mapping.getWorksheetHistory();
        
        for(int i=0; i < historyJson.length(); i++) {

            String tags = HistoryJsonUtility.readTags(historyJson, i);
            HistoryJsonUtility.updateTags("xxxx", historyJson, i);
            String tags2 = HistoryJsonUtility.readTags(historyJson, i);
            String type3 = HistoryJsonUtility.readTypeHNodeId(historyJson, i);
            HistoryJsonUtility.updateTypeHNodeId("newType", historyJson, i);
            String c1 = HistoryJsonUtility.readColumnName(historyJson, i);
            HistoryJsonUtility.updateColumnName("xxxx", historyJson, i);
            String c2 = HistoryJsonUtility.readColumnName(historyJson, i);
            String full1 = HistoryJsonUtility.readFullType(historyJson, i);
            HistoryJsonUtility.updateFullType("xxxx", historyJson, i);
            String full2 = HistoryJsonUtility.readFullType(historyJson, i);
            String type4 = HistoryJsonUtility.readTypeSemanticType(historyJson, i);
            HistoryJsonUtility.updateTypeSemanticType("newType2", historyJson, i);
            String w = HistoryJsonUtility.readWorksheetId(historyJson, i);
            Boolean key = HistoryJsonUtility.readIsKey(historyJson, i);
            Boolean train = HistoryJsonUtility.readTrainAndShowUpdates(historyJson, i);
            String type5 = HistoryJsonUtility.readRdfLiteralType(historyJson, i);
            String worksheetId = HistoryJsonUtility.readWorksheetId(historyJson, i);
            HistoryJsonUtility.updateWorksheetId("yyyy", historyJson, i);
            Boolean isKey = HistoryJsonUtility.readIsKey(historyJson, i);
            HistoryJsonUtility.updateIsKey(true, historyJson, i);
            Boolean trainAndShowUpdates = HistoryJsonUtility.readTrainAndShowUpdates(historyJson, i);
            HistoryJsonUtility.updateTrainAndShowUpdates(false, historyJson, i);
            String rdfLiteralType = HistoryJsonUtility.readRdfLiteralType(historyJson, i);
            HistoryJsonUtility.updateRdfLiteralType("xsd:boolean", historyJson, i);
            HistoryJsonUtility.updateRdfLiteralType(XSDDatatype.XSDdate, historyJson, i); //Tricky you can use jena XSDDatatype
            List<String> inputColumns = HistoryJsonUtility.readInputColumns(historyJson, i);
            List<String> outputColumns = HistoryJsonUtility.readOutputColumns(historyJson, i);
            HistoryJsonUtility.updateInputColumns(Arrays.asList("column1"), historyJson, i);
            HistoryJsonUtility.updateOutputColumns(Arrays.asList("column2"), historyJson, i);
            String commandName = HistoryJsonUtility.readCommandName(historyJson, i);
            HistoryJsonUtility.updateCommandName("com", historyJson, i);
        }
    }
}

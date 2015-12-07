package edu.isi.karma;


import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import edu.isi.karma.controller.history.HistoryJsonUtil;
import edu.isi.karma.kr2rml.*;
import edu.isi.karma.kr2rml.formatter.KR2RMLColumnNameFormatter;
import edu.isi.karma.kr2rml.mapping.KR2RMLMapping;
import edu.isi.karma.kr2rml.mapping.KR2RMLMappingAuxillaryInformation;
import edu.isi.karma.kr2rml.mapping.R2RMLMappingIdentifier;
import edu.isi.karma.kr2rml.planning.TriplesMap;
import edu.isi.karma.kr2rml.planning.TriplesMapGraphMerger;
import edu.isi.karma.kr2rml.template.TemplateTerm;
import edu.isi.karma.kr2rml.template.TemplateTermSet;
import edu.isi.karma.rep.metadata.WorksheetProperties;
import edu.isi.karma.support.ApplyMappingSupport;
import edu.isi.karma.support.ApplySubjectMapSupport;
import edu.isi.karma.supportObject.SupportPredicate;
import edu.isi.karma.supportObject.SupportStatement;
import edu.isi.karma.util.HistoryJsonUtility;
import edu.isi.karma.util.SupportUtil;
import edu.isi.karma.webserver.KarmaException;
import org.apache.commons.collections.map.MultiValueMap;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainTest {

    private static void l(Object... obj){
        System.out.println(Arrays.toString(obj));
    }

    public static void main(String args[]) throws IOException, KarmaException {
        File r2rml = new File("" +
                "C:\\Users\\tenti\\Desktop\\Marco Utility\\TESI 2015-09-30\\Web-Karma-2\\karma-modelSupport\\src\\main\\java\\edu\\isi\\karma\\test\\R2RML_infodocument-model_2015-07-08.ttl");

        File outputJson = new File(
                "C:\\Users\\tenti\\Desktop\\Marco Utility\\TESI 2015-09-30\\Web-Karma-2\\karma-modelSupport\\src\\main\\java\\edu\\isi\\karma\\test\\output.json");

        boolean isOffline = SupportUtil.isOffline("localhost", 8181);
        ModelWebKarmaSupport model =  ModelWebKarmaSupport.getInstance(isOffline);
        KR2RMLMapping mapping = model.prepareKR2RMLMapping("infodocument_2015_09_18", r2rml);

        //What i succesfully full manage for my purpose

        KR2RMLColumnNameFormatter formatter = mapping.getColumnNameFormatter(); // done
        JSONArray historyJson = mapping.getWorksheetHistory(); //done
        WorksheetProperties.SourceTypes sourceTypes = mapping.getSourceType(); //done
        KR2RMLVersion version = mapping.getVersion(); //done
        R2RMLMappingIdentifier identifier = mapping.getId(); // done


        Map<String,TriplesMap> map2 = mapping.getTriplesMapIndex(); // in progress..
        Map<String,SubjectMap> map = mapping.getSubjectMapIndex(); // in progress..



        KR2RMLMappingAuxillaryInformation auxIfo = mapping.getAuxInfo();
        TriplesMapGraphMerger triplesMapGraphMerger = auxIfo.getTriplesMapGraph();
        //Map<String, List<String>> blankNodesColumnCoverage = auxIfo.getBlankNodesColumnCoverage(); //..not necessary
        //Map<String, String> blankNodesUriPrefixMap = auxIfo.getBlankNodesUriPrefixMap(); //..not necessary
        Map<String, List<PredicateObjectMap>> columnNameToPredObjMLinks = auxIfo.getColumnNameToPredObjLinks();

        List<SupportStatement> mvm = model.readAllInfo();
        //get from the web karma model all the information for set the semantic type
        for(SupportStatement ss: mvm){
            //name source column
            String sourceColumn = ss.getSubjectMap().getColumnHNodeId();
            //list of rdf type assign to the source column
            List<String> rdfTypeOfSourceColumn = ss.getSubjectMap().getRdfsTypeString();
            List<String> predicateRdfType = ss.getListPredicate();
            List<String> objectMapRdfType = ss.getListObjectMap();
            String test = "";
        }


        Map<String, String> subjectMapIdToTemplateAnchor = auxIfo.getSubjectMapIdToTemplateAnchor();

        //TESTED PredicateObjectsMap READ
       /* boolean b1 = model.hasTriplesMap("name_location");
        List<PredicateObjectMap> list1 = model.readPredicateObjectBySubject("name_location");
        TriplesMap t1 = model.readTriplesMapByKey("xxxxxx");
        TriplesMap t2 = model.readTriplesMapByName("name_location");
        PredicateObjectMap p1 = model.readPredicateObjectMap(0, 0);
        List<PredicateObjectMap> l11 = model.readPredicateObjectMaps(0);
        Predicate  pp1 = model.readPredicate(0, 0);
        ObjectMap oo1 = model.readObject(0, 0);
        Map<Predicate,ObjectMap> map11 = model.readPredicateObjects(0);
        Map<Predicate,ObjectMap> map22 = model.readPredicateObjects("xxxxx");
        Map<Predicate,ObjectMap> map33 =
                model.readPredicateObjects("http://isi.edu/integration/karma/dev#TriplesMap_920bef46-4975-42a1-8789-4213e27a6f41");*/

        //TESTED SUBJECT READ
       /* boolean c = model.hasSubject("name_location");
        boolean b = model.hasSubject("xxxx");
        SubjectMap sub1 = model.readSubjectMapByKey("xxxxxx");
        SubjectMap sub2 = model.readSubjectMapByName("name_location");
        TemplateTerm value1 = model.readTemplateTerm(0, 0);
        TemplateTerm value11 = model.readTemplateTerm("name_location");
        List<TemplateTerm> value2 = model.readTemplateTerms(0);
        TemplateTermSet rdftype1 = model.readRdfType(0, 0);
        List<TemplateTermSet> rdfType = model.readRdfsType(0);
        List<TemplateTermSet> rdftype2 = model.readRdfType("name_location");
        TemplateTermSet rdftype3 = model.readRdfType("name_location",0);*/


        //TESTED READ/WRITE HISTORY JSON
        //JSONArray historyJson = mapping.getWorksheetHistory();
        /*for(int i=0; i < historyJson.length(); i++) {

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
        }*/
    }
}

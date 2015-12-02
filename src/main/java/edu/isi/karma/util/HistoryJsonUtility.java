package edu.isi.karma.util;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.internal.filter.ValueNode;
import com.jayway.jsonpath.spi.json.JsonOrgJsonProvider;
import com.jayway.jsonpath.spi.mapper.JsonOrgMappingProvider;
import edu.isi.karma.controller.history.HistoryJsonUtil;
import org.codehaus.jackson.map.util.JSONPObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 4535992 on 30/11/2015.
 * @author 4535992.
 * @version 2015-11-30.
 */
 @SuppressWarnings("unused")
public class HistoryJsonUtility {

    /*private static final Configuration configuration = Configuration.builder()
            .jsonProvider(new JsonOrgJsonProvider())
            .mappingProvider(new JsonOrgMappingProvider())
            .build();

    public static String readTags(JSONArray jsonObject){
        return JsonPath.parse(jsonObject.toString()).read("$.tags[0]");
    }

    public static JSONArray updateTags(JSONArray jsonObject){
        String sJson = jsonObject.toString();
        ValueNode.JsonNode updatedJson = JsonPath.using(configuration).parse(sJson).put("$.tags[0]",sJson,"xxx").json();
        return new JSONArray(updatedJson.toString());
    }*/

    public enum SupportJsonKeys {
        tags, hNodeId, inputParameters,SemanticTypesArray
    }

    public enum SupportJsonValue{
        columnName,FullType,isPrimary
    }

    private static JSONObject jo(){
        return new JSONObject();
    }

    private static JSONArray ja(){
        return new JSONArray();
    }

    public static void updateTags(String tags,JSONArray historyJson,int index){
        historyJson.getJSONObject(index).put("tags", ja().put(tags));
    }

    public static String readTags(JSONArray historyJson,int index){
        return historyJson.getJSONObject(index).getJSONArray("tags").getString(0);
    }

    public static String readColumnName(JSONArray historyJson,int index){
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONArray array = HistoryJsonUtil.getJSONArrayValue("hNodeId", inputParameters);
        return array.getJSONObject(0).getString("columnName");
    }

    public static void updateColumnName(String columnName,JSONArray historyJson,int index) {
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        HistoryJsonUtil.setArgumentValue("hNodeId", ja().put(jo().put("columnName", columnName)), inputParameters);
    }

    public static String readTypeHNodeId(JSONArray historyJson,int index) {
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONObject value = HistoryJsonUtil.getJSONObjectWithName("hNodeId", inputParameters);
        return HistoryJsonUtil.getParameterType(value).toString();
    }

    public static void updateTypeHNodeId(String type,JSONArray historyJson,int index) {
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONObject value = HistoryJsonUtil.getJSONObjectWithName("hNodeId", inputParameters);
        value.put("type", type);
    }

    public static String readFullType(JSONArray historyJson,int index) {
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONArray values = HistoryJsonUtil.getJSONArrayValue("SemanticTypesArray", inputParameters);
        return values.getJSONObject(0).getString("FullType");
    }

    public static void updateFullType(String fullType,JSONArray historyJson,int index) {
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONArray values = HistoryJsonUtil.getJSONArrayValue("SemanticTypesArray", inputParameters);
        values.getJSONObject(0).put("FullType", fullType);
    }

    public static Boolean readIsPrimary(JSONArray historyJson,int index) {
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONArray values = HistoryJsonUtil.getJSONArrayValue("SemanticTypesArray", inputParameters);
        return values.getJSONObject(0).getBoolean("isPrimary");
    }

    public static void updateIsPrimary(Boolean isPrimary,JSONArray historyJson,int index) {
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONArray values = HistoryJsonUtil.getJSONArrayValue("SemanticTypesArray", inputParameters);
        values.getJSONObject(0).put("isPrimary",isPrimary);
    }

    public static String readDomainLabel(JSONArray historyJson,int index) {
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONArray values = HistoryJsonUtil.getJSONArrayValue("SemanticTypesArray", inputParameters);
        return values.getJSONObject(0).getString("DomainLabel");
    }

    public static void updateDomainLabel(String domainLabel,JSONArray historyJson,int index) {
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONArray values = HistoryJsonUtil.getJSONArrayValue("SemanticTypesArray", inputParameters);
        values.getJSONObject(0).put("DomainLabel", domainLabel);
    }

    public static String readDomainId(JSONArray historyJson,int index) {
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONArray values = HistoryJsonUtil.getJSONArrayValue("SemanticTypesArray", inputParameters);
        return values.getJSONObject(0).getString("DomainId");
    }

    public static void updateDomainId(String domainId,JSONArray historyJson,int index) {
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONArray values = HistoryJsonUtil.getJSONArrayValue("SemanticTypesArray", inputParameters);
        values.getJSONObject(0).put("DomainId", domainId);
    }

    public static String readDomainUri(JSONArray historyJson,int index) {
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONArray values = HistoryJsonUtil.getJSONArrayValue("SemanticTypesArray", inputParameters);
        return values.getJSONObject(0).getString("DomainUri");
    }

    public static void updateDomainUri(String domainUri,JSONArray historyJson,int index) {
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONArray values = HistoryJsonUtil.getJSONArrayValue("SemanticTypesArray", inputParameters);
        values.getJSONObject(0).put("DomainUri", domainUri);
    }

    public static String readTypeSemanticType(JSONArray historyJson,int index) {
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONObject value = HistoryJsonUtil.getJSONObjectWithName("SemanticTypesArray", inputParameters);
        return HistoryJsonUtil.getParameterType(value).toString();
    }

    public static void updateTypeSemanticType(String type,JSONArray historyJson,int index) {
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONObject value = HistoryJsonUtil.getJSONObjectWithName("SemanticTypesArray", inputParameters);
        value.put("type", type);
    }

    public static String readWorksheetId(JSONArray historyJson,int index){
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        return HistoryJsonUtil.getStringValue("worksheetId", inputParameters);
    }

    public static void updateWorksheetId(String worksheetId,JSONArray historyJson,int index){
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        HistoryJsonUtil.setArgumentValue("worksheetId", worksheetId, inputParameters);
    }

    public static Boolean readIsKey(JSONArray historyJson,int index){
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        return HistoryJsonUtil.getBooleanValue("isKey", inputParameters);
    }

    public static void updateIsKey(Boolean isKey,JSONArray historyJson,int index){
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        HistoryJsonUtil.setArgumentValue("isKey",isKey,inputParameters);
    }

    public static Boolean readTrainAndShowUpdates(JSONArray historyJson,int index){
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        return HistoryJsonUtil.getBooleanValue("trainAndShowUpdates", inputParameters);
    }

    public static void updateTrainAndShowUpdates(Boolean trainAndShowUpdates,JSONArray historyJson,int index){
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        HistoryJsonUtil.setArgumentValue("trainAndShowUpdates",trainAndShowUpdates, inputParameters);
    }

    public static String readRdfLiteralType(JSONArray historyJson,int index){
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        return HistoryJsonUtil.getStringValue("rdfLiteralType", inputParameters);
    }

    public static void updateRdfLiteralType(String rdfLiteralType,JSONArray historyJson,int index){
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        HistoryJsonUtil.setArgumentValue("rdfLiteralType", rdfLiteralType, inputParameters);
    }

    public static void updateRdfLiteralType(XSDDatatype xsd,JSONArray historyJson,int index){
        updateRdfLiteralType(xsd.getURI().replace(XSDDatatype.XSD, "xsd:").replace("#",""), historyJson, index);
    }

    public static List<String> readInputColumns(JSONArray historyJson,int index){
        List<String> columns = new ArrayList<>();
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONObject object = HistoryJsonUtil.getJSONObjectWithName("inputColumns", inputParameters);
        JSONArray values = new JSONArray(object.getString("value"));
        for(int i=0; i < values.length(); i++){
            JSONObject obj = values.getJSONObject(i);
            JSONArray values2 = obj.getJSONArray("value");
            for(int j= 0; j < values2.length(); j++){
                JSONObject obj2 = values2.getJSONObject(j);
                columns.add(obj2.getString("columnName"));
            }
        }
        return columns;
    }

    public static List<String> readOutputColumns(JSONArray historyJson,int index){
        List<String> columns = new ArrayList<>();
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        JSONObject object = HistoryJsonUtil.getJSONObjectWithName("outputColumns", inputParameters);
        JSONArray values = new JSONArray(object.getString("value"));
        for(int i=0; i < values.length(); i++){
            JSONObject obj = values.getJSONObject(i);
            JSONArray values2 = obj.getJSONArray("value");
            for(int j= 0; j < values2.length(); j++){
                JSONObject obj2 = values2.getJSONObject(j);
                columns.add(obj2.getString("columnName"));
            }
        }
        return columns;
    }

    public static void updateInputColumns(List<String> columns,JSONArray historyJson,int index){
        String[] arr =  columns.toArray(new String[columns.size()]);
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        String s = "[{\\\"value\\\":";
        String sc ="";
        for(String string : columns){
            sc = "[{\\\"columnName\\\":\\\"";
            sc += string;
            sc += "\\\"}]";
        }
        s += sc;
        s += "}]";
        HistoryJsonUtil.getJSONObjectWithName("inputColumns", inputParameters).put("value",s);
    }

    public static void updateOutputColumns(List<String> columns,JSONArray historyJson,int index){
        String[] arr =  columns.toArray(new String[columns.size()]);
        JSONArray inputParameters = historyJson.getJSONObject(index).getJSONArray("inputParameters");
        String s = "[{\\\"value\\\":";
        String sc ="";
        for(String string : columns){
            sc = "[{\\\"columnName\\\":\\\"";
            sc += string;
            sc += "\\\"}]";
        }
        s += sc;
        s += "}]";
        HistoryJsonUtil.getJSONObjectWithName("outputColumns", inputParameters).put("value",s);
    }

    public static void updateCommandName(String commandName,JSONArray historyJson,int index){
        historyJson.getJSONObject(index).put("commandName",commandName);
    }

    public static String readCommandName(JSONArray historyJson,int index){
        return historyJson.getJSONObject(index).getString("commandName");
    }

}

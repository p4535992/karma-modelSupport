package edu.isi.karma.notFinish;

import edu.isi.karma.controller.history.WorksheetCommandHistoryExecutor;
import edu.isi.karma.kr2rml.mapping.R2RMLMappingIdentifier;
import edu.isi.karma.kr2rml.mapping.WorksheetR2RMLJenaModelParser;
import edu.isi.karma.rep.*;
import edu.isi.karma.util.AbstractJDBCUtil;
import edu.isi.karma.util.DBType;
import edu.isi.karma.util.JDBCUtilFactory;
import edu.isi.karma.webserver.KarmaException;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 4535992 on 18/11/2015.
 */
public class ModelWebKarma{

    private static final String PREFIX_KM = "@prefix rr: <http://www.w3.org/ns/r2rml#> .";
    private static final String PREFIX_R2RML ="@prefix km-dev: <http://isi.edu/integration/karma/dev#> .";
    private static final String KM = "km-dev:";

   // private static Logger logger = LoggerFactory.getLogger(ModelWebKarma.class);
    private String dBorSIDName;
    private String encoding;
    private Connection conn;
    private String tableName;
    private List<String> inputColumns;
    private List<String> outputColumns = new ArrayList<String>();
    private static int DATABASE_TABLE_FETCH_SIZE = 10000;


    private String sourceName;
    private String modelPublicationTime;
    private String modelVersion = "1.7";
    private String hasBaseURI;

    public ModelWebKarma(){}

    public ModelWebKarma(String hostname,Integer portnumber,String username,String password,String tableName,
                         String dBorSIDName, DBType dbType,String baseUri) throws SQLException, ClassNotFoundException {

        AbstractJDBCUtil dbUtil = JDBCUtilFactory.getInstance(dbType);
        this.conn = dbUtil.getConnection(hostname, portnumber, username, password, dBorSIDName);
        this.inputColumns = dbUtil.getColumnNames(dBorSIDName, tableName, conn);
        this.modelPublicationTime = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        this.tableName = tableName;
        this.hasBaseURI = baseUri;

        Workspace workspace = WorkspaceManager.getInstance().createWorkspace();
        RepFactory factory = null;
        Worksheet worksheet = factory.createWorksheet(tableName, workspace, "UTF-8");

        WorksheetCommandHistoryExecutor wche = new WorksheetCommandHistoryExecutor("",workspace);
        //W\karma-commands\commands-alignment-openrdf\src\..\controller\command\worksheet\ApplyHistoryFromR2RMLModelCommand.java

        //PrintWriter writer = new PrintWriter(fileOutput);
        //worksheet.prettyPrint("km-dev",writer,factory);
    }

    private String base(List<String> inputColumns,List<String> outputColumns) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("_:node19pn5vcthx1 a km-dev:R2RMLMapping ;\n");
        sb.append("km-dev:sourceName \"").append(sourceName).append("\" ;\n");
        sb.append("km-dev:modelPublicationTime \"").append(modelPublicationTime).append("\"^^xsd:long ;\n");
        sb.append("km-dev:modelVersion \"").append(modelVersion).append("\" ;\n");
        sb.append("km-dev:hasInputColumns \"").append(cc(inputColumns)).append("\" ;\n");
        sb.append("km-dev:hasOutputColumns \"").append(cc(outputColumns)).append("\" ;\n");
        sb.append("km-dev:hasModelLabel \"").append(tableName).append("\" ;\n");
        sb.append("km-dev:hasBaseURI \"").append(hasBaseURI).append("\" ;\n");
        return sb.toString();
    }




    private String cn(String columnName){
        return "[{\\\"columnName\\\":\\\""+columnName+"\\\"}]";
    }

    private String cc(List<String> list){
        String result = "[";
        for(String item: list){
           result += cn(item) + ",";
        }
        result = result.substring(0,result.length()-1);
        result +="]";
        return result;
    }


    /*private void createWoskSheetHistory(String tableName,String fileOutput)
            throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        sb.append("km-dev:hasWorksheetHistory");

        Workspace workspace = WorkspaceManager.getInstance().createWorkspace();
        RepFactory factory = null;
        Worksheet worksheet = factory.createWorksheet(tableName, workspace, "UTF-8");
        PrintWriter writer = new PrintWriter(fileOutput);
        worksheet.prettyPrint("km-dev",writer,factory);
    }
    */

    private void createWoskSheetHistory( ModelColumn mColumn){
        String sColumnName = mColumn.getNameColumn();
        String sRdfLiteralType =  mColumn.getRdfLiteralType();
        String sMetaPropertyValue = mColumn.getDomainId();

        if(mColumn.isClass()) mColumn.setFullType("http://isi.edu/integration/karma/dev#classLink");

        JSONArray aWorkSheetHistory = new JSONArray();
        if(mColumn.getRdfLiteralType().isEmpty()) mColumn.setRdfLiteralType("xsd:string");
        JSONObject root = new JSONObject();
        //{\"tags\":[\"Modeling\"],
        root.put("tags", ja().put("Modeling"));

        JSONArray inputParameters =ja();
        if(mColumn.isClass()){
            //{\"name\":\"metaPropertyName\",\"value\":\"isUriOfClass\",\"type\":\"other\"}
            inputParameters.put(jo().put("name","metaPropertyName").put("value","isUriOfClass").put("type","other"));
            //{\"name\":\"metaPropertyValue\",\"value\":\"http://purl.org/goodrelations/v1#Location1\",\"type\":\"other\"}
            inputParameters.put(jo().put("name","metaPropertyValue").put("value",sMetaPropertyValue).put("type","other"));
        }
         /*{\"name\":\"hNodeId\",\"value\":[{\"columnName\":\"edificio\"}], \"type\":\"hNodeId\"},*/
        //{\"name\":\"hNodeId\",\"value\":[{\"columnName\":\"name_location\"}],\"type\":\"hNodeId\"}
        inputParameters
                .put(jo().put("name", "hNodeId")
                        .put("value", ja().put(jo().put("columnName", sColumnName))).put("type", "hNodeId"));

        /*{\"name\":\"SemanticTypesArray\",
         \"value\":[
            {\"isPrimary\":true,
                \"FullType\":\"http://purl.org/goodrelations/v1#name\",
                \"DomainLabel\":\"gr:BusinessEntity1\",
                \"DomainId\":\"http://purl.org/goodrelations/v1#BusinessEntity1\",
                \"DomainUri\":\"http://purl.org/goodrelations/v1#BusinessEntity\"}],
         \"type\":\"other\"},
         */
        /*
        {\"name\":\"SemanticTypesArray\",
        \"value\":
        [{\"isPrimary\":true,
        \"FullType\":\"http://isi.edu/integration/karma/dev#classLink\",
        \"DomainLabel\":\"gr:Location1\",
        \"DomainId\":\"http://purl.org/goodrelations/v1#Location1\",
        \"DomainUri\":\"http://purl.org/goodrelations/v1#Location\"}],
        \"type\":\"other\"}
        */
        inputParameters
                .put(jo().put("name", "SemanticTypesArray")
                                .put("value",
                                        ja().put(jo()
                                                        .put("isPrimary", mColumn.isPrimary())
                                                        .put("FullType", mColumn.getFullType())
                                                        .put("DomainLabel", mColumn.getDomainLabel() + " (add)")
                                                        .put("DomainId", mColumn.getDomainId() + " (add)")
                                                        .put("DomainUri", mColumn.getDomainUri())
                                        )
                    )
                    .put("type", "other")
                );
        //{\"name\":\"worksheetId\",\"value\":\"W\",\"type\":\"worksheetId\"},
        //{\"name\":\"worksheetId\",\"value\":\"W\",\"type\":\"worksheetId\"}
        inputParameters
                .put(jo().put("name", "worksheetId").put("value", "W").put("type", "worksheetId"));
        //{\"name\":\"isKey\",\"value\":false,\"type\":\"other\"},
        //{\"name\":\"isKey\",\"value\":false,\"type\":\"other\"}
        inputParameters
                .put(jo().put("name", "isKey").put("value", mColumn.isKey()).put("type", "other"));
        //{\"name\":\"trainAndShowUpdates\", \"value\":false,\"type\":\"other\"},
        //{\"name\":\"trainAndShowUpdates\",\"value\":false,\"type\":\"other\"}
        inputParameters
                .put(jo().put("name", "trainAndShowUpdates")
                        .put("value", mColumn.isTrainAndShowUpdates()).put("type", "other"));
        //{\"name\":\"rdfLiteralType\",\"value\":\"xsd:string\",\"type\":\"other\"},
        //{\"name\":\"rdfLiteralType\",\"value\":\"xsd:string\",\"type\":\"other\"}
        inputParameters
                .put(jo().put("name", "rdfLiteralType")
                        .put("value", sRdfLiteralType).put("type", "other"));
        /*{\"name\":\"inputColumns\",\"value\":\"[
               {\\\"value\\\":[{\\\"columnName\\\":\\\"nazione\\\"}]}
           ]\",
          \"type\":\"hNodeIdList\"},*/
        /*
        {\"name\":\"inputColumns\",\"value\":\"[
                {\\\"value\\\":[{\\\"columnName\\\":\\\"name_location\\\"}]
                }]\",
        \"type\":\"hNodeIdList\"}
        */
        inputParameters
                .put(jo().put("name", "inputColumns")
                                .put("value", ja().put(
                                                jo().put("\"value\"",
                                                        ja().put(jo().put("\"columnName\"", "\"" + mColumn.getNameColumn() + "\"")))
                            )
                    )
                    .put("type", "hNodeIdList")
                );
        /*
         {\"name\":\"outputColumns\",
          \"value\":\"[{\\\"value\\\":[{\\\"columnName\\\":\\\"nazione\\\"}]}]\",
           \"type\":\"hNodeIdList\"}
        */
        /*
        {\"name\":\"outputColumns\",
        \"value\":\"[{\\\"value\\\":[{\\\"columnName\\\":\\\"name_location\\\"}]}]\",
        \"type\":\"hNodeIdList\"}
        */
        inputParameters
                .put(jo().put("name", "outputColumns")
                                .put("value", ja().put(
                                                jo().put("\"value\"",
                                                        ja().put(jo().put("\"columnName\"", "\"" + mColumn.getNameColumn() + "\"")))
                                        )
                                )
                                .put("type", "hNodeIdList")
                );
        //fine array input parameters
        root.put("inputParameters",inputParameters);
        //\"commandName\":\"SetSemanticTypeCommand\"}
        if(mColumn.isClass()) root.put("commandName", "SetMetaPropertyCommand");
        else root.put("commandName", "SetSemanticTypeCommand");

        //\"inputParameters\":[
        aWorkSheetHistory.put(root);
    }

    private JSONObject jo(){
        return new JSONObject();
    }

    private JSONArray ja(){
        return new JSONArray();
    }

    private ModelColumn mc(){
        return new ModelColumn();
    }

    private String createTriplesMatch(String tableName,String domainId,String domainLabel,String nameClass){
        StringBuilder sb = new StringBuilder();
        /*
        km-dev:TriplesMap_920bef46-4975-42a1-8789-4213e27a6f41 a rr:TriplesMap .
        _:node19pn5vcthx1 km-dev:hasTriplesMap km-dev:TriplesMap_920bef46-4975-42a1-8789-4213e27a6f41 .
        km-dev:TriplesMap_920bef46-4975-42a1-8789-4213e27a6f41 km-dev:isPartOfMapping _:node19pn5vcthx1 .
        _:node19pn5vcthx2 rr:tableName "infodocument_coord_siimobility_05052014" ;
            a rr:LogicalTable ;
            km-dev:isPartOfMapping _:node19pn5vcthx1 .
        */
        String id = "km-dev:TriplesMap_920bef46-4975-42a1-8789-4213e27a6f41 .\n";
        sb.append(id +" a rr:TriplesMap .\n");
        sb.append("_:node19pn5vcthx1 km-dev:hasTriplesMap "+id+" .\n");
        sb.append(id + "km-dev:isPartOfMapping _:node19pn5vcthx1 .\n");
        sb.append("_:node19pn5vcthx2 rr:tableName \""+tableName+"\" ;\n");
        sb.append(" a rr:LogicalTable ;\n");
        sb.append("km-dev:isPartOfMapping _:node19pn5vcthx1 .\n");
        sb.append("_:node19pn5vcthx1 km-dev:hasLogicalTable _:node19pn5vcthx2 .\n");

        //Prepare Class
        sb.append("km-dev:TriplesMap_920bef46-4975-42a1-8789-4213e27a6f41 rr:logicalTable _:node19pn5vcthx2 ;\n");
        sb.append("rr:subjectMap _:node19pn5vcthx3 .\n");
        sb.append("_:node19pn5vcthx1 km-dev:hasSubjectMap _:node19pn5vcthx3 .\n");
        sb.append("_:node19pn5vcthx3 km-dev:isPartOfMapping _:node19pn5vcthx1 ;\n");
        sb.append("a rr:SubjectMap ;\n");
        sb.append("km-dev:alignmentNodeId \""+domainId+"\" ;\n");
        sb.append("rr:class "+domainLabel+" ;\n");
        sb.append("rr:template \"{"+nameClass+"}\" ; \n");
        sb.append("a km-dev:steinerTreeRootNode . \n");
        //Add optional predicate for the classes
        sb.append("km-dev:PredicateObjectMap_bec2c906-b3f9-4113-a86e-d3cce1138831 rr:predicate gr:hasPOS .");

        //Objects
        sb.append("km-dev:RefObjectMap_6b35b44a-c9e2-495d-909b-b033a3d766d1 a rr:RefObjectMap , rr:ObjectMap ;\n" +
                "rr:parentTriplesMap km-dev:TriplesMap_a61cd545-01ff-4eee-9d95-911b9d0e7111 ;\n" +
                "km-dev:isPartOfMapping _:node19pn5vcthx1 .");
        sb.append("");
        sb.append("");
        return "";
    }

    private void loadAndPrintModelJenaR2RML(File R2RML) throws IOException, KarmaException {
        String name = R2RML.getName();
        URL location = R2RML.toURI().toURL();
        R2RMLMappingIdentifier id = new R2RMLMappingIdentifier(name,location);
        WorksheetR2RMLJenaModelParser parser = new WorksheetR2RMLJenaModelParser(id);
        com.hp.hpl.jena.rdf.model.Model jenaModel = parser.getModel();
        jenaModel.write(System.out,"Turtle");
    }




}

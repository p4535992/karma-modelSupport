karma-modelSupport
================================
###### NOTE: This repository  project is not ready yet, it's was created as a meeting place for other people,
###### who want to help is welcome, any other advice or tips for the code are welcome too.
##### I do not own any rights to the code [Web-karma](https://github.com/usc-isi-i2/Web-Karma), 
###### this repository seeks only to create new functions to be integrated with it.
The ultimate goal of this project is to provide a Web-Karma a set of Java API methods for generate R2RML model files 
in format turtle, at the moment Karma allows the creation only through the graphical interface of a server (e.g. jetty).
All the information on Web-Karma can be found on the official page [Web-karma](https://github.com/usc-isi-i2/Web-Karma).
What i do until now? Almost nothing i just started this project so any help or advise is welcome.

### We want to work with Jetty Server on or off?

For now , for each action i need, i will try to make a method for work with the 'karma-offline' module and a method 
for work with the HttpServletRequest from the server.
1) you can put a boolean value to the constructor for say if the server is on or not (by default is true).
```java
      boolean isOffline = true;
      ModelWebKarmaSupport model = ModelWebKarmaSupport.getInstance(isOffline);
```
2) Automatic check on runtime if the server is on by the ip address and the port.
```java
      boolean isOffline = SupportUtil.isOffline("localhost",8181);
```
### Generates triple offline through the API Java Web-karma, in a more friendly manner.
###### With Connection
Very simple trick for generate file of triples from a java.sql.Connection,required the dependency to the 'karma-offline' module of Web-Karma:
```java
    Connection conn = ....; //already existent to a Database.
    File r2rml = new File("" + "C:\\...\\R2RML_infodocument-model_2015-07-08.ttl");
    File output = new File("C:\\...\\output.n3");
    String tableName = "infodocument_2015_09_18";

    GenerationRDFSupport support = GenerationRDFSupport.getInstance();
    support.generateRDF(r2rml,output,conn,tablename);
```
###### Other friendly constructor from database:
```java
    generateRDF(File karmaModel,File fileOftriple,DBType dbTypeKarma,String hostname,String username,
                String password,String port,String nameOfDatabase,String nameOfTable)
    generateRDF(File karmaModel,File fileOftriple,Connection conn,String nameOfTable)
    generateRDF(File karmaModel,File inputData,File fileOfTriple)
    generateRDF(File karmaModel,InputStream inputData,File fileOfTriple)
    generateRDF(File karmaModel,String inputData,File fileOfTriple)
```
NOTE: The File of triple generated is always on N-Triples format, but you can just use API like Jena and Sesame for    
convert to yours favorite format.
### Read/Create/Update the WorkSheetHistory of a File R2RML (.ttl) or from the Jetty Server by HttpServletRequest.

NOTE: Not Finished yet. For now require 'karma-common','karma-commands','karma-jdbc','karma-util','karma-offline'

###### Read and print WorkSheetHistory from R2RML File (work)
```java
        File r2rml = new File("C:\\...\\R2RML_infodocument-model_2015-07-08.ttl");
        //GET WORKSHEET HISTORY
        boolean isOffline = SupportUtil.isOffline("localhost", 8181);
        ModelWebKarmaSupport model =  ModelWebKarmaSupport.getInstance(isOffline);
        KR2RMLMapping mapping = model.prepareKR2RMLMapping("infodocument_2015_09_18", r2rml);
        JSONArray historyJson = mapping.getWorksheetHistory();
        
        //or ...
        
        JSONArray historyJson = model.extractWorkSheetHistory("infodocument_2015_09_18", r2rml);
        
        //Print json
        File outputJson = new File( "C:\\...\\output.json");
        FileUtil.writePrettyPrintedJSONObjectToFile(new JSONObject().put("WorkSheetHistory", 
                            (Object) historyJson), outputJson);
```

###### Read and print WorkSheetHistory from Jetty Server (must be full tested)
```java
        HttpServletrequest request = ...
        WorkSpace workspace = ...;
        //GET WORKSHEET HISTORY
        boolean isOffline = SupportUtil.isOffline("localhost", 8181);
        ModelWebKarmaSupport model =  ModelWebKarmaSupport.getInstance(isOffline);
        KR2RMLMapping mapping = model.prepareKR2RMLMapping(request,workspace);
        JSONArray historyJson = mapping.getWorksheetHistory();
        
        //or...

        JSONArray historyJson = model.extractWorkSheetHistory(request,workspace);
    
        //Print json
        File outputJson = new File( "C:\\...\\output.json");
        FileUtil.writePrettyPrintedJSONObjectToFile(new JSONObject().put("WorkSheetHistory", 
                            (Object) array), outputJson);
```
###### READ/UPDATE every single JSONOBJECT of the WorkSheetHistory JSON Array
```java
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
```
###### READ Content SubjectMap and TriplesMap of the KR2RML Mapping
```java
    //TESTED PredicateObjectsMap READ
    boolean b1 = model.hasTriplesMap("name_location");
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
            model.readPredicateObjects("http://isi.edu/integration/karma/dev#TriplesMap_920bef46-4975-42a1-8789-4213e27a6f41");
    
    //TESTED SUBJECT READ
    Map<String,SubjectMap> map = mapping.getSubjectMapIndex();
    boolean c = model.hasSubject("name_location");
    boolean b = model.hasSubject("xxxx");
    SubjectMap sub1 = model.readSubjectMapByKey("xxxxxx");
    SubjectMap sub2 = model.readSubjectMapByName("name_location");
    TemplateTerm value1 = model.readTemplateTerm(0, 0);
    TemplateTerm value11 = model.readTemplateTerm("name_location");
    List<TemplateTerm> value2 = model.readTemplateTerms(0);
    TemplateTermSet rdftype1 = model.readRdfType(0, 0);
    List<TemplateTermSet> rdfType = model.readRdfsType(0);
    List<TemplateTermSet> rdftype2 = model.readRdfType("name_location");
    TemplateTermSet rdftype3 = model.readRdfType("name_location",0);
```
###### READ all column name of a table and extract a structure subject -> predicate -> object
```java
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
```
### TODO

- Create a method for generated a new Default JSONARRAY WorkSheetHistory to populate with specific parameter.
- Write The Basic Properties of a R2RML file of Web-karma.
For example:
```text
_:node19pn5vcthx1 a km-dev:R2RMLMapping ;
km-dev:sourceName "infodocument_2015_09_18" ;
km-dev:modelPublicationTime "1436370842550"^^xsd:long ;
km-dev:modelVersion "1.7" ;
km-dev:hasInputColumns "[[{\"columnName\":\"url\"}],[{\"columnName\":\"identifier\"}],[{\"columnName\":\"name_location\"}]]" ;
km-dev:hasOutputColumns "[[{\"columnName\":\"url\"}],[{\"columnName\":\"identifier\"}],[{\"columnName\":\"name_location\"}]]" ;
km-dev:hasModelLabel "infodocument_2015_09_18" ;
km-dev:hasBaseURI "http://purl.org/goodrelations/v1#" ;
```
- Read/Update/Delete the Triple Match of the File R2RML Turtle from Java API, and Server Commands.

- Other stuff but for now, i must finished the other point....




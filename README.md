karma-modelSupport
================================

##### NOTE: This repository  project is not ready yet, it's was created as a meeting place for other people,
##### who want to help, and for get advice or tips on the code.

#### I do not own any rights to the code Web-Karma, this repository seeks only to create new functions to be integrated with it.

The ultimate goal of this project is to provide a Web-Karma Java API for creating files in drive levels
R2RML format turtle, at the moment Karma allows the creation only through the graphical interface of a server (e.g. jetty).

All the information on Web-Karma can be found on this page [Web-karma](https://github.com/usc-isi-i2/Web-Karma).

What i do until now? Almost nothing i just started this project so any help or advise is welcome

### Generates triple offline through the API Java Web-karma, in a more friendly manner.

###### With Connection
Very simple trick for generate file of triples from a java.sql.Connection,  
required the dependency to the 'karma-offline' module of Web-Karma
```java

    Connection already existent to a Database.
    File r2rml = new File("" + "C:\\...\\R2RML_infodocument-model_2015-07-08.ttl");
    File output = new File("C:\\...\\output.n3");
    String tableName = "infodocument_2015_09_18";

    GenerationRDFSupport support = GenerationRDFSupport.getInstance();
    support.generateRDF(r2rml,output,conn,tablename;
```
###### Other friendly constructor from database:
```java
    generateRDF(File karmaModel,File fileOftriple,DBType dbTypeKarma,String hostname,String username,
                String password,String port,String nameOfDatabase,String nameOfTable)

    generateRDF(File karmaModel,File fileOftriple,Connection conn,String nameOfTable)
```
###### Other friendly constructor from file:
```java
    generateRDF(File karmaModel,File inputData,File fileOfTriple)
    generateRDF(File karmaModel,InputStream inputData,File fileOfTriple)
    generateRDF(File karmaModel,String inputData,File fileOfTriple)
```
NOTE: The File of triple generated is always on N-Triples format, but you can just use API like Jena and Sesame for    
convert to yours favorite format.


### Read/Create/Update the WorkSheetHistory of a File R2RML (.ttl) or from the Jetty Server by HttpServletRequest.

NOTE: Not Finished yet.

For now require 'karma-common','karma-commands','karma-jdbc','karma-util'

###### Read and print WorkSheetHistory from R2RML File (work)
```java
        File r2rml = new File("C:\\...\\R2RML_infodocument-model_2015-07-08.ttl");
        ApplyHistorySupport support =  ApplyHistorySupport.getInstance();
        JSONArray array = support.extractWorkSheetHistory("infodocument_2015_09_18", r2rml);
        File outputJson = new File( "C:\\...\\output.json");
        FileUtil.writePrettyPrintedJSONObjectToFile(new JSONObject().put("WorkSheetHistory", (Object) array), outputJson);
```

###### Read and print WorkSheetHistory from Jetty Server (must be full tested)
```java
        HttpServletrequest request = ...
        WorkSpace workspace = ...;
        ApplyHistorySupport support =  ApplyHistorySupport.getInstance();
        JSONArray array = support.extractWorkSheetHistory(request,workspace);
        File outputJson = new File( "C:\\...\\output.json");
        FileUtil.writePrettyPrintedJSONObjectToFile(new JSONObject().put("WorkSheetHistory", (Object) array), outputJson);
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




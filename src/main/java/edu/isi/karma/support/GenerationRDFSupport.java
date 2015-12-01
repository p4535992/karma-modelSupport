package edu.isi.karma.support;

import edu.isi.karma.kr2rml.URIFormatter;
import edu.isi.karma.kr2rml.mapping.R2RMLMappingIdentifier;
import edu.isi.karma.kr2rml.writer.N3KR2RMLRDFWriter;
import edu.isi.karma.rdf.GenericRDFGenerator;
import edu.isi.karma.rdf.OfflineRdfGenerator;
import edu.isi.karma.rdf.RDFGeneratorRequest;
import edu.isi.karma.util.AbstractJDBCUtil;
import edu.isi.karma.util.DBType;
import edu.isi.karma.util.JDBCUtilFactory;
import edu.isi.karma.webserver.KarmaException;

import java.io.*;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 4535992 on 30/11/2015.
 * @author 4535992.
 * @version 2015-11-30.
 */
public class GenerationRDFSupport {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GenerationRDFSupport.class);

    protected GenerationRDFSupport() {}

    private static GenerationRDFSupport instance = null;

    public static GenerationRDFSupport getInstance(){
        if(instance == null) {
            instance = new GenerationRDFSupport();
        }
        return instance;
    }

    public File generateRDF(File karmaModel,File fileOftriple,Connection conn,String nameOfTable)
            throws IOException, SQLException {
        DatabaseMetaData dbm = conn.getMetaData();
        return generateRDF("DB",karmaModel.getAbsolutePath(),fileOftriple.getAbsolutePath(),
                getDbType(dbm).name(),getHostFromUrl(dbm.getURL()),getUsernameFromUrl(dbm.getURL()),
                getPasswordFromUrl(dbm.getURL()),String.valueOf(getPortFromUrl(dbm.getURL())),conn.getCatalog(),nameOfTable);
    }


    /**
     * Method to generate triple file with Web-Karma API from a local file:JSON,CSV,XML,AVRO.
     * Note: sourceTypeKarma the Source Type of karma e.g. "DB"  A database.
     * @param karmaModel the path to the model R2RML turtle of Karma e.g. "karma_files/model/".
     * @param fileOftriple the path to the output file of triple e.g. "karma_files/output/".
     * @param dbTypeKarma the DBType of database sue with KARMA e.g. "MySQL","Oracle","SQLServer","PostGIS".
     * @param hostname the hostname where is allocated the database e.g. "localhost".
     * @param username the username of the database.
     * @param password the password of the database.
     * @param port the port of the database.
     * @param nameOfDatabase the name of the database.
     * @param nameOfTable the username of the table of the database.
     * @return the file of triple generated with Web-karma.
     * @throws IOException throw if any error is occurred.
     */
    public File generateRDF(File karmaModel,File fileOftriple,DBType dbTypeKarma,String hostname,String username,
                            String password,String port,String nameOfDatabase,String nameOfTable) throws IOException {
        String sourceTypeKarma = "DB";
        return generateRDF(sourceTypeKarma,karmaModel.getAbsolutePath(),fileOftriple.getAbsolutePath(),
                dbTypeKarma.name(),hostname,username,password,port,nameOfDatabase,nameOfTable);
    }

    /**
     * Method to generate triple file with Web-Karma API from a local file:JSON,CSV,XML,AVRO.
     * @param sourceTypeKarma the Source Type of karma e.g. "DB"  A database.
     * @param pathToFileKarmaModel the path to the model R2RML turtle of Karma e.g. "karma_files/model/".
     * @param pathFileTripleOfOutput the path to the output file of triple e.g. "karma_files/output/".
     * @param dbTypeKarma the type of database sue with KARMA e.g. "MySQL","Oracle","SQLServer","PostGIS".
     * @param hostname the hostname where is allocated the database e.g. "localhost".
     * @param username the username of the database.
     * @param password the password of the database.
     * @param port the port of the database.
     * @param nameOfDatabase the name of the database.
     * @param nameOfTable the username of the table of the database.
     * @return the file of triple generated with Web-karma.
     * @throws IOException throw if any error is occurred.
     */
    private File generateRDF(String sourceTypeKarma,String pathToFileKarmaModel,
                       String pathFileTripleOfOutput,String dbTypeKarma,String hostname,String username,String password,
                       String port,String nameOfDatabase,String nameOfTable) throws IOException {
        String pathOut = pathFileTripleOfOutput+"";

        String[] value = new String[]{
                sourceTypeKarma,pathToFileKarmaModel,pathOut,
                dbTypeKarma,hostname,username,password,
                port,nameOfDatabase,nameOfTable
        };
        String[] param = new String[]{
                "--sourcetype", "--modelfilepath","--outputfile", "--dbtype","--hostname",
                "--username","--password","--portnumber","--dbname","--tablename"
                //"--encoding"
        };
        String[] args2;
        try {
            args2 = mergeArraysForInput(param, value);
            logger.info("PARAM KARMA:" + args2.toString());
            logger.info("try to create a file of triples from a relational table with karma...");
            OfflineRdfGenerator.main(args2);
            logger.info("...file of triples created with name:" + pathOut);
            return new File(pathOut);
        }catch(Exception ex){
            logger.error(ex.getLocalizedMessage(),ex);
            return new File(pathOut);
        }
    }

    /**
     * Method to generate triple file with Web-KArma API from a local file:JSON,CSV,XML,AVRO.
     * @param karmaModel file Model R2RML of Web-Karma.
     * @param inputData File input data.
     * @param fileOfTriple file support where save the file of triples generated.
     * @return the File output of triple (.n3,.rdf).
     */
    public File generateRDF(File karmaModel,File inputData,File fileOfTriple){
        return generationOfTripleWithKarma(karmaModel, inputData,fileOfTriple);
    }

    /**
     * Method to generate triple file with Web-KArma API from a local file:JSON,CSV,XML,AVRO.
     * @param karmaModel file Model R2RML of Web-Karma.
     * @param inputData Stream input data.
     * @param fileOfTriple file support where save the file of triples generated.
     * @return the File output of triple (.n3,.rdf).
     */
    public File generateRDF(File karmaModel,InputStream inputData,File fileOfTriple){
        return generationOfTripleWithKarma(karmaModel, inputData,fileOfTriple);
    }

    /**
     * Method to generate triple file with Web-KArma API from a local file:JSON,CSV,XML,AVRO.
     * @param karmaModel file Model R2RML of Web-Karma.
     * @param inputData String input data.
     * @param fileOfTriple file support where save the file of triples generated.
     * @return the File output of triple (.n3,.rdf).
     */
    public File generateRDF(File karmaModel,String inputData,File fileOfTriple){
        return generationOfTripleWithKarma(karmaModel, inputData,fileOfTriple);
    }

    /**
     * Method to generate triple file with Web-KArma API from a local file:JSON,CSV,XML,AVRO.
     * @param karmaModel file Model R2RML of Web-Karma.
     * @param inputData input put like String,File,InputStream.
     * @param fileOfTriple file support where save the file of triples generated.
     * @return the File output of triple (.n3,.rdf).
     */
    private File generationOfTripleWithKarma(File karmaModel,Object inputData,File fileOfTriple){
        try {
            GenericRDFGenerator rdfGenerator = new GenericRDFGenerator("DEFAULT_TEST");
            R2RMLMappingIdentifier modelIdentifier = new R2RMLMappingIdentifier(
                    getFilenameWithoutExt(karmaModel), karmaModel.toURI().toURL());
            rdfGenerator.addModel(modelIdentifier);
            PrintWriter pw = new PrintWriter(new StringWriter());
            N3KR2RMLRDFWriter writer = new N3KR2RMLRDFWriter(new URIFormatter(), pw);
            RDFGeneratorRequest request;
            fileOfTriple.createNewFile();
            if (fileOfTriple.exists()) {
                request = new RDFGeneratorRequest(
                        getFilenameWithoutExt(karmaModel), //"people-model"
                        getLocalPath(fileOfTriple.getAbsolutePath())); //"files/data/people.json"

                if(inputData instanceof File) request.setInputFile((File) inputData);
                else if(inputData instanceof String) request.setInputData((String) inputData);
                else if(inputData instanceof InputStream) request.setInputStream((InputStream) inputData);
                else{
                    logger.error("The InputData is in a not supported format, must be a File, String or InputStream");
                    return fileOfTriple;
                }

                request.setAddProvenance(true);
                if(fileOfTriple.getAbsolutePath().toLowerCase().endsWith(".json")) request.setDataType(GenericRDFGenerator.InputType.JSON);
                else if(fileOfTriple.getAbsolutePath().toLowerCase().endsWith(".xml")) request.setDataType(GenericRDFGenerator.InputType.XML);
                else if(fileOfTriple.getAbsolutePath().toLowerCase().endsWith(".csv")) request.setDataType(GenericRDFGenerator.InputType.CSV);
                else if(fileOfTriple.getAbsolutePath().toLowerCase().endsWith(".avro")) request.setDataType(GenericRDFGenerator.InputType.AVRO);
                else{
                   logger.error("This file:" + fileOfTriple.getAbsolutePath() +
                            " can't be triplify with Web-Karma these are the permittted format JSON.CSV,XML,AVRO");
                    throw  new KarmaException("This file:" + fileOfTriple.getAbsolutePath() +
                            " can't be triplify with Web-Karma these are the permittted format JSON.CSV,XML,AVRO");
                }

                request.addWriter(writer);
                rdfGenerator.generateRDF(request);
            }
            else{
                logger.error("The InputStream is NULL!!!");
            }
            logger.info("Generated RDF: " + pw.toString());
            return fileOfTriple;
        } catch (KarmaException|IOException e) {
            logger.error(e.getLocalizedMessage(), e);
            return fileOfTriple;
        }
    }


    //--------------------------------------------------------
    // Some utility method
    //--------------------------------------------------------

    /**
     * Method for get the filename without extension.
     * @param f file of input
     * @return name of the file without the extension
     */
     private String getFilenameWithoutExt(File f) {
        return getFilenameWithoutExt(f.getAbsolutePath());
    }

    /**
     * Method for get the filename without extension.
     * @param fullPath string of the path to the file
     * @return name of the file without the extension
     */
    private String getFilenameWithoutExt(String fullPath) {
        if(!fullPath.contains(".")) fullPath = fullPath +". ";
        int dot = fullPath.lastIndexOf(".");
        int sep = fullPath.lastIndexOf(File.separatorChar);
        return fullPath.substring(sep + 1, dot);
    }

    /**
     * Method or get the local path in the project.
     * @param file File object.
     * @return the local path to the file in the project.
     */
     private String getLocalPath(File file){ return getLocalPath("", file.getAbsolutePath());}

    /**
     * Method for get the local path in the project.
     * @param absolutePath string of the absolute path to the file in the project.
     * @return the local path to the file in the project
     */
     private String getLocalPath(String absolutePath){return getLocalPath("", absolutePath);}

    /**
     * Method for get the local path in the project.
     * @param basePath string of the absolute path to the direcotry of the project.
     * @param localPath string of the absolute path to the file in the project.
     * @return the local path to the file in the project
     */
     private String getLocalPath(String basePath,String localPath){
        basePath = basePath.replace(System.getProperty("user.dir"),"");
        return basePath+File.separator+localPath;
    }

    /**
     * Merge the content of two arrays of string with same size for
     * make the args for a main method java class with option e home.
     * @param param array of parameter.
     * @param value array of values.
     * @return merged array.
     */
    private String[] mergeArraysForInput(String[] param, String[] value) {
        String[] array = (String[]) Array.newInstance(param[0].getClass(), param.length + value.length);
        int j = 0;
        if(param.length==value.length) {
            //array = new T[param.length+value.length];
            //array = (T[]) Array.newInstance(param[0].getClass(),param.length+value.length);
            for (int i = 0; i < param.length; i++) {
                if (i == 0) j = j + i;
                else j = j + 1;
                array[j] = param[i];
                j = j + 1;
                array[j] = value[i];
            }
        }else{
            logger.warn("WARNING: Check your array size");
        }
        return array;
    }

    private DBType getDbType(Connection conn) throws SQLException {
        DatabaseMetaData dbm = conn.getMetaData();
        return getDbType(dbm);
    }

    private DBType getDbType(DatabaseMetaData dbm) throws SQLException {
        String type = dbm.getDatabaseProductName();
        if(type.equals(DBType.MySQL.name())|| type.equals("MySQL"))return DBType.MySQL;
        else if(type.equals(DBType.Oracle.name()) || type.equals("Oracle"))return DBType.Oracle;
        else if(type.equals(DBType.PostGIS.name()) || type.equals("PostgreSQL")) return DBType.PostGIS;
        else if(type .equals(DBType.SQLServer.name()) || type.equals("Microsoft SQL Server")) return DBType.SQLServer;
        else if(type .equals(DBType.Sybase.name())) return DBType.Sybase;
        else return null;
    }

    private String getHostFromUrl(String url) {
        String regexForHostAndPort = "[.\\w]+:\\d+";
        Pattern hostAndPortPattern = Pattern.compile(regexForHostAndPort);
        Matcher matcher = hostAndPortPattern.matcher(url);
        matcher.find();
        int start = matcher.start();
        int end = matcher.end();
        if(start >= 0 && end >= 0) {
            String hostAndPort = url.substring(start, end);
            String [] array = hostAndPort.split(":");
            if(array.length >= 2)
                return array[0];
        }
        throw new IllegalArgumentException("couldn't find pattern '" + regexForHostAndPort + "' in '" + url + "'");
    }

    private Integer getPortFromUrl(String url) {
        String regexForHostAndPort = "[.\\w]+:\\d+";
        Pattern hostAndPortPattern = Pattern.compile(regexForHostAndPort);
        Matcher matcher = hostAndPortPattern.matcher(url);
        matcher.find();
        int start = matcher.start();
        int end = matcher.end();
        if(start >= 0 && end >= 0) {
            String hostAndPort = url.substring(start, end);
            String [] array = hostAndPort.split(":");
            if(array.length >= 2)
                return Integer.parseInt(array[1]);
        }
        throw new IllegalArgumentException("couldn't find pattern '" + regexForHostAndPort + "' in '" + url + "'");
    }

     private String getUsernameFromUrl(String url){
        Pattern pat = Pattern.compile("(\\&|\\?|\\=|\\/)?(user|username)(\\=)(.*?)(\\&|\\?|\\=|\\/|\\s)+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pat.matcher(url  + " ");
        if(matcher.find())  {
            String[] find = (matcher.group(0)).split("=");
            return find[1].substring(0,find[1].length()-1);
        }
        throw new IllegalArgumentException("couldn't find pattern '" + pat.toString() + "' in '" + url + "'");
    }

    private String getPasswordFromUrl(String url){
        Pattern pat = Pattern.compile("(\\&|\\?|\\=|\\/)?(pass|password)(\\=)(.*?)(\\&|\\?|\\=|\\/|\\s)+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pat.matcher(url + " ");
        if(matcher.find())  {
            String[] find = (matcher.group(0)).split("=");
            return find[1].substring(0,find[1].length()-1);
        }
        throw new IllegalArgumentException("couldn't find pattern '" + pat.toString() + "' in '" + url + "'");
    }


    //------------------------------------------------------------
    // TEST
    //------------------------------------------------------------

    public static void main(String args[]) throws IOException, SQLException, ClassNotFoundException {
        File r2rml = new File("" +
                "C:\\Users\\tenti\\Desktop\\Marco Utility\\TESI 2015-09-30\\Web-Karma-20151130" +
                "\\karma-modelSupport\\src\\main\\java\\edu\\isi\\karma\\test\\R2RML_infodocument-model_2015-07-08.ttl");

        File output = new File(
                "C:\\Users\\tenti\\Desktop\\Marco Utility\\TESI 2015-09-30\\Web-Karma-20151130" +
                        "\\karma-modelSupport\\src\\main\\java\\edu\\isi\\karma\\test\\output.n3");

        GenerationRDFSupport support = GenerationRDFSupport.getInstance();

        /*support.generateRDF("DB",r2rml,output,DBType.MySQL,"localhost",
                "siimobility", "siimobility","3306","geodb","infodocument_2015_09_18");*/

        AbstractJDBCUtil dbUtil = JDBCUtilFactory.getInstance(DBType.MySQL);
        Connection conn = dbUtil.getConnection("localhost", 3306, "siimobility", "siimobility", "geodb");
        support.generateRDF(r2rml,output,conn,"infodocument_2015_09_18");

    }


}

package edu.isi.karma.supportJena;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;

import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;

import com.hp.hpl.jena.sparql.util.NodeUtils;

import org.apache.jena.iri.IRI;
import org.apache.jena.iri.IRIFactory;


import java.io.*;
import java.net.URI;
import java.util.*;

/**
 * Class utility for Jena
 * Created by 4535992 in 2015-04-28.
 * @author 4535992.
 * @version 2015-12-07.
 */
@SuppressWarnings("unused")
public class Jena2Kit {

    //CONSTRUCTOR
    protected Jena2Kit() {}

    private static Jena2Kit instance = null;

    public static Jena2Kit getInstance(){
        if(instance == null) {
            instance = new Jena2Kit();
        }
        return instance;
    }

    /**
     * A list of com.hp.hpl.jena.datatypes.xsd.XSDDatatype.
     * return all the com.hp.hpl.jena.datatypes.RDFDatatype supported from jena.
     * @param uri the String of the uri resource.
     * @return  the RDFDatatype of the uri resource.
     */
    public static RDFDatatype convertStringToRDFDatatype(String uri){
        return convertToXSDDatatype(uri);
    }

    /**
     * Method convert a string to XSDDatatype.
     * @param uri string uri of the XSDDatatype.
     * @return xsdDatatype of the string uri if exists.
     */
    public static XSDDatatype convertToXSDDatatype(String uri) {
        for (XSDDatatype xsdDatatype : allFormatsOfXSDDataTypes) {
            if(xsdDatatype.getURI().equalsIgnoreCase(XSDDatatype.XSD+"#"+uri)) return xsdDatatype;
            if(xsdDatatype.getURI().replace(XSDDatatype.XSD,"")
                    .toLowerCase().contains(uri.toLowerCase())) return xsdDatatype;
        }
        throw new IllegalArgumentException("The XSD Datatype '" + uri + "' is not recognised");
    }

    /**
     * A list of com.hp.hpl.jena.datatypes.xsd.XSDDatatype.
     * return all the XSDDatatype supported from jena.
     */
    public static final XSDDatatype allFormatsOfXSDDataTypes[] = new XSDDatatype[]{
            XSDDatatype.XSDstring,XSDDatatype.XSDENTITY,XSDDatatype.XSDID,XSDDatatype.XSDIDREF,
            XSDDatatype.XSDanyURI,XSDDatatype.XSDbase64Binary,XSDDatatype.XSDboolean,XSDDatatype.XSDbyte,
            XSDDatatype.XSDdate,XSDDatatype.XSDdateTime,XSDDatatype.XSDdecimal,XSDDatatype.XSDdouble,
            XSDDatatype.XSDduration,XSDDatatype.XSDfloat,XSDDatatype.XSDgDay,XSDDatatype.XSDgMonth,
            XSDDatatype.XSDgMonthDay,XSDDatatype.XSDgYear,XSDDatatype.XSDgYearMonth,XSDDatatype.XSDhexBinary,
            XSDDatatype.XSDint,XSDDatatype.XSDinteger,XSDDatatype.XSDlanguage,XSDDatatype.XSDlong,
            XSDDatatype.XSDName,XSDDatatype.XSDNCName,XSDDatatype.XSDnegativeInteger,XSDDatatype.XSDNMTOKEN,
            XSDDatatype.XSDnonNegativeInteger,XSDDatatype.XSDnonPositiveInteger,XSDDatatype.XSDnormalizedString,
            XSDDatatype.XSDNOTATION,XSDDatatype.XSDpositiveInteger,XSDDatatype.XSDQName,XSDDatatype.XSDshort,
            XSDDatatype.XSDtime,XSDDatatype.XSDtoken,XSDDatatype.XSDunsignedByte,XSDDatatype.XSDunsignedInt,
            XSDDatatype.XSDunsignedLong,XSDDatatype.XSDunsignedShort
    };

    /**
     * Method utility: create new property for a Model.
     * @param modelOrUri the Jena Model where search the property.
     * @param subjectIri string of the subject.
     * @return RDFNode.
     */
    public static RDFNode createRDFNode(Object modelOrUri,Object subjectIri){
        Model model;
        subjectIri = toId(subjectIri);
        if(isIRI(subjectIri) || isUri(subjectIri)) {
            if (modelOrUri != null && modelOrUri instanceof Model) {
                model = (Model) modelOrUri;
                try {
                    return model.asRDFNode(NodeUtils.asNode(String.valueOf(toIri(subjectIri))));
                }catch(Exception e){
                    return model.asRDFNode(NodeUtils.asNode(toString(subjectIri)));
                }
            } else {
                model = createModel();
                try {
                    return model.asRDFNode(NodeUtils.asNode(String.valueOf(toIri(subjectIri))));
                }catch(Exception e){
                    return model.asRDFNode(NodeUtils.asNode(toString(subjectIri)));
                }
            }
        }else{
            //SystemLog.warning(subjectIri + " is not a IRI normalized!!!");
            return null;
        }
    }

    /**
     * Method utility: create new property for a Model.
     * @param subjectIri string of the subject.
     * @return RDFNode.
     */
    public static RDFNode createRDFNode(Object subjectIri){
        return createRDFNode(null, toString(subjectIri));
    }

    /**
     * Method to create a Jena Resource.
     * @param localNameOrUri the String name local Graph or the String iri of the subject.
     * @return the Jena Resource.
     */
    public static Resource createResource(String localNameOrUri){
        return createResource(null, localNameOrUri);
    }

    /**
     * Method to create a Jena Resource.
     * @param localNameOrUri the String name local Graph or the String iri of the subject.
     * @return the Jena Resource.
     */
    public static Resource createResource(Object localNameOrUri){
        return createResource(null, localNameOrUri);
    }

    /**
     * Method to create a Jena Resource.
     * @param graphUriAndModel the String iri or the Jena Model.
     * @param localNameorUri the String name local Graph or the String iri of the subject.
     * @return the Jena Resource.
     */
    public static Resource createResource(Object graphUriAndModel,Object localNameorUri){
        localNameorUri = toId(localNameorUri);
        if(graphUriAndModel == null){
            if(isIRI(localNameorUri) || isUri(localNameorUri))return ResourceFactory.createResource(toString(localNameorUri));
            else return null;
        }else {
            if (isString(graphUriAndModel)) {
                if (toString(graphUriAndModel).endsWith("/") || toString(graphUriAndModel).endsWith("#")) {
                    String uri = toString(graphUriAndModel) + localNameorUri;
                    if(isIRI(uri) || isUri(uri))return ResourceFactory.createResource(uri);
                    else return null;
                } else {
                    String uri = toString(graphUriAndModel)  + "/" +  localNameorUri;
                    if(isIRI(uri) || isUri(uri))return ResourceFactory.createResource(uri);
                    else return null;
                }
            }
            else if (graphUriAndModel instanceof Model) {
                return (Resource) createRDFNode(graphUriAndModel, localNameorUri);
            }
            else return null;
        }
    }

    /**
     * Method to create a Jena Property.
     * @param stringOrModelGraph the String iri or the Jena Model.
     * @param predicateUri the String name local Graph or the String iri of the subject.
     * @param impl if true use the PredicateImpl to create the predicate.
     * @return the Jena Predicate.
     */
    private static Property createPropertyBase(Object stringOrModelGraph, Object predicateUri,boolean impl){
        if(stringOrModelGraph == null){
            if(impl){
                if(predicateUri!=null){
                    if(isIRI(predicateUri) || isUri(predicateUri))return new PropertyImpl(toString(predicateUri));
                    else return null;
                }
                else return null;
            }else{
                if(predicateUri!=null) {
                    if (isIRI(predicateUri) || isUri(predicateUri))  return ResourceFactory.createProperty(toString(predicateUri));
                    else return ResourceFactory.createProperty(toString(predicateUri));
                }
                else return null;
            }
        }
        if(isStringNoEmpty(stringOrModelGraph)){
            if(!toString(stringOrModelGraph).endsWith("/")|| !toString(stringOrModelGraph).endsWith("#")){
                stringOrModelGraph = stringOrModelGraph + "/";
            }
            if(impl){
                if(predicateUri!=null){
                    if(isIRI(predicateUri) || isUri(predicateUri) || toString(stringOrModelGraph).isEmpty()) {
                        return new PropertyImpl(toString(predicateUri));
                    }else if(isStringNoEmpty(stringOrModelGraph) &&
                            isIRI(toString(stringOrModelGraph)+"/"+toString(predicateUri))){
                        return new PropertyImpl(toString(stringOrModelGraph), toString(predicateUri));
                    }
                    else return null;
                }
                else return null;
            }else{
                if(predicateUri!=null) {
                    if (isIRI(predicateUri) || isUri(predicateUri) || toString(stringOrModelGraph).isEmpty()) {
                        return ResourceFactory.createProperty(toString(predicateUri));
                    }else if(isStringNoEmpty(stringOrModelGraph) &&
                            isIRI(toString(stringOrModelGraph)+"/"+toString(predicateUri))){
                        return ResourceFactory.createProperty(toString(stringOrModelGraph), toString(predicateUri));
                    }
                    else return null;
                }
                else return null;
            }
        }
        else if(stringOrModelGraph instanceof Model && isStringNoEmpty(predicateUri)) {
            return createRDFNode(stringOrModelGraph, predicateUri).as(Property.class);
        }
        else return null;
    }

    /**
     * Method to create a Jena Property.
     * @param stringOrModelGraph the String iri or the Jena Model.
     * @param localNameOrSubject the String name local Graph or the String iri of the subject.
     * @return the Jena Predicate.
     */
    public static Property createProperty(Object stringOrModelGraph, String localNameOrSubject){
        return createPropertyBase(stringOrModelGraph, localNameOrSubject, false);
    }

    /**
     * Method to create a Jena Property.
     * @param stringOrModelGraph the String iri or the Jena Model.
     * @param localNameOrSubject the String name local Graph or the String iri of the subject.
     * @return the Jena Predicate.
     */
    public static Property createProperty(Object stringOrModelGraph, Object localNameOrSubject){
        return createPropertyBase(stringOrModelGraph, localNameOrSubject, false);
    }

    /**
     * Method to create a Jena Property.
     * @param localNameOrSubject the String name local Graph or the String iri of the subject.
     * @return the Jena Predicate.
     */
    public static Property createProperty(String localNameOrSubject){
        return  createPropertyBase(null, localNameOrSubject, false);
    }

    /**
     * Method to create a Jena Property.
     * @param localNameOrSubject the String name local Graph or the String iri of the subject.
     * @return the Jena Predicate.
     */
    public static Property createProperty(Object localNameOrSubject){
        return  createPropertyBase(null, localNameOrSubject, false);
    }


    /**
     * Method utility: create new typed literal from uri.
     * @param model the Model jean where create the Literal.
     * @param stringOrObject  the value of the Jena Literal.
     * @param datatype the Jena RDFDatatype of the literal.
     * @return the Jena Literal.
     */
    private static Literal createLiteralBase(Model model,Object stringOrObject,RDFDatatype datatype){
        if(model == null) {
            if (isString(stringOrObject)) {
                if (datatype != null) return ResourceFactory.createTypedLiteral(toString(stringOrObject), datatype);
                else return ResourceFactory.createPlainLiteral(toString(stringOrObject));
            } else {
                if (datatype != null) return ResourceFactory.createTypedLiteral(toString(stringOrObject), datatype);
                return ResourceFactory.createTypedLiteral(stringOrObject);
            }
        }else{
            if (isString(stringOrObject)) {
                if (datatype != null) return model.createTypedLiteral(toString(stringOrObject), datatype);
                else return model.createLiteral(toString(stringOrObject));
            } else {
                if (datatype != null) return model.createTypedLiteral(stringOrObject, datatype);
                return model.createTypedLiteral(stringOrObject);
            }
        }
    }

    /**
     * Method utility: create new typed literal from uri.
     * @param stringOrObject  the value of the Jena Literal.
     * @param datatype the Jena RDFDatatype of the literal.
     * @return the Jena Literal.
     */
    public static Literal createLiteral(Model model,Object stringOrObject,RDFDatatype datatype){
        return createLiteralBase(model, stringOrObject, datatype);
    }


    /**
     * Method utility: create new typed literal from uri.
     * @param stringOrObject  the value of the Jena Literal.
     * @param datatype the Jena RDFDatatype of the literal.
     * @return the Jena Literal.
     */
    public static Literal createLiteral(Object stringOrObject,RDFDatatype datatype){
        return createLiteralBase(null, stringOrObject, datatype);
    }

    /**
     * Method utility: create new typed literal from uri.
     * @param stringOrObject  the value of the Jena Literal.
     * @param typeUri the Jena RDFDatatype of the literal.
     * @return the Jena Literal.
     */
    public static Literal createLiteral(Object stringOrObject,String typeUri){
        return createLiteralBase(null, stringOrObject, convertStringToRDFDatatype(typeUri));
    }

    /**
     * Method utility: create new typed literal from uri.
     * @param stringOrObject  the value of the Jena Literal.
     * @return the Jena Literal.
     */
    public static Literal createLiteral(Object stringOrObject){
        return createLiteralBase(null, stringOrObject, null);
    }

    /**
     * Method utility: create new typed literal from uri.
     * @param stringOrObject the value of the Jena Literal.
     * @return the Jena Literal.
     */
    public static Literal createLiteral(String stringOrObject){
        return createLiteralBase(null, stringOrObject, null);
    }

    /**
     * Method utility: create statement form a jena Model.
     * @param model the Jena Model.
     * @param subject the iri subject.
     * @param predicate the iri predicate.
     * @param object the iri object.
     * @return Statement.
     */
    public static Statement createStatement( Model model, String subject,String predicate,String object) {
        return  createStatementBase(model, subject, predicate, object, null, null);
    }

    public static Statement createStatement(
            Model model, String subject,String predicate,Object object,String graphUri,XSDDatatype xsdDatatype) {
        return  createStatementBase(model, subject, predicate, object, graphUri, xsdDatatype);
    }

    public static Statement createStatement(
            Model model, URI subject,URI predicate,URI object,String graphUri,XSDDatatype xsdDatatype) {
        return  createStatementBase(model, subject, predicate, object, graphUri, xsdDatatype);
    }

    /**
     * Method utility: create statement form a jena Model.
     * @param model the Jena Model.
     * @param subject the iri subject.
     * @param predicate the iri predicate.
     * @param object the iri object.
     * @param graphUri the iri of the graph.
     * @param xsdDatatype the XSDDatatype of the Literal
     * @return Statement.
     */
    private static Statement createStatementBase(
        Model model, Object subject,Object predicate,Object object,Object graphUri,XSDDatatype xsdDatatype) {
        if (model == null) {
            if(graphUri == null || isStringOrUriEmpty(graphUri)) {
                return ResourceFactory.createStatement(createResource(subject),
                        createProperty(predicate), createLiteral(object, xsdDatatype));
            }else{
                return ResourceFactory.createStatement(createResource(graphUri,subject),
                        createProperty(graphUri,predicate),createLiteral(object, xsdDatatype));
            }
        } else {
            return model.createStatement(createResource(model,subject),
                    createProperty(model, predicate),createLiteral(object, xsdDatatype));
        }

    }

    /**
     * Method utility: create statement form a jena Model.
     * @param model the Jena Model.
     * @param subject the iri subject.
     * @param predicate the iri predicate.
     * @param object the iri object.
     * @param graphUri the iri of the graph.
     * @return Statement.
     */
    public static Statement createStatement( Model model, String subject,String predicate,Object object,String graphUri) {
        return createStatementBase(model, subject, predicate, object, graphUri, null);
    }

    /**
     * Method utility: create statement form a jena Model.
     * @param subject the iri subject.
     * @param predicate the iri predicate.
     * @param object the iri object.
     * @param graphUri the iri of the graph.
     * @return Statement.
     */
        public static Statement createStatement(String subject,String predicate,String object,String graphUri){
            return createStatementBase(null, subject, predicate, object, graphUri, null);
        }

    /**
     * Method utility: create statement form a jena Model.
     * @param subject the iri subject.
     * @param predicate the iri predicate.
     * @param object the iri object.
     * @return Statement.
     */
    public static Statement createStatement(String subject,String predicate,String object){
        return createStatementBase(null, subject, predicate, object, null, null);
    }

    /**
     * Method utility: create statement form a jena Model.
     * @param subject the iri subject.
     * @param predicate the iri predicate.
     * @param object the iri object.
     * @return Statement.
     */
    public static Statement createStatement(String subject,String predicate,Object object){
        return createStatementBase(null, subject, predicate, object, null, null);
    }

    /**
     * Method utility: create statement form a jena Model.
     * @param subject the iri subject.
     * @param predicate the iri predicate.
     * @param object the iri object.
     * @param xsdDatatype the XSDDatatype of the Literal
     * @return Statement.
     */
    public static Statement createStatement(String subject,String predicate,Object object,XSDDatatype xsdDatatype){
        return createStatementBase(null, subject, predicate, object, null, xsdDatatype);
    }

    /**
     * Method utility: create statement form a jena Model.
     * @param subject the iri subject.
     * @param predicate the iri predicate.
     * @param object the iri object.
     * @param graphUri  the URI to the graph base of the ontology.
     * @param xsdDatatype the XSDDatatype of the Literal
     * @return Statement.
     */
    public static Statement createStatement(String subject,String predicate,Object object,String graphUri,XSDDatatype xsdDatatype){
        return createStatementBase(null, subject, predicate, object, graphUri, xsdDatatype);
    }

    /**
     * Method utility: create statement form a jena Model.
     * @param subject the iri subject.
     * @param predicate the iri predicate.
     * @param object the iri object.
     * @param graphUri  the URI to the graph base of the ontology.
     * @param xsdDatatype the XSDDatatype of the Literal
     * @return Statement.
     */
    public static Statement createStatement(String subject,String predicate,Object object,String graphUri,String xsdDatatype){
        return createStatementBase(null, subject, predicate, object, graphUri, createToXSDDatatype(xsdDatatype));
    }

    /**
     * Method convert a string to XSDDatatype.
     * @param uri string uri of the XSDDatatype.
     * @return xsdDatatype of the string uri if exists.
     */
    public static XSDDatatype createToXSDDatatype(String uri) {
        for (XSDDatatype xsdDatatype : allFormatsOfXSDDataTypes) {
            if(xsdDatatype.getURI().equalsIgnoreCase(XSDDatatype.XSD+"#"+uri)) return xsdDatatype;
            if(xsdDatatype.getURI().replace(XSDDatatype.XSD,"")
                    .toLowerCase().contains(uri.toLowerCase())) return xsdDatatype;
        }
        throw new IllegalArgumentException("The XSD Datatype '" + uri + "' is not recognised");
    }

    /**
     * Method utility: create new default Jena Model.
     * @return Jena Model.
     */
    public static Model createModel(){
       return ModelFactory.createDefaultModel();
    }

    /**
     * Method utility: create new default Jena Graph.
     * @return Jena Graph.
     */
    public static Graph createGraph(){
        return  com.hp.hpl.jena.sparql.graph.GraphFactory.createDefaultGraph();
    }

    /**
     * Method to convert a Jena Model to a Jena Ontology Model.
     * @param model the Jena Base Model.
     * @return the Jena Ontology Model.
     */
    public static OntModel createOntologyModel(Model model){
        return ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model);
    }

    /**
     * Method to create a Jena Triple Match.
     * @param subject the Jena Node of the Subject.
     * @param predicate the Jena Node of the Predicate.
     * @param object the Jena Node of the Object.
     * @return the Jena TripleMatch.
     */
    public static TripleMatch createTripleMatch(Node subject,Node predicate,Node object){
        return new Triple(subject,predicate,object);
    }

    /**
     * Method to create a Jena Triple Match.
     * @param triple the Jena Triple Object.
     * @return the Jena TripleMatch.
     */
    public static TripleMatch createTripleMatch(Triple triple){ return triple;}

    /**
     * Method to check if a String uri is a IRI normalized.
     * http://stackoverflow.com/questions/9419658/normalising-possibly-encoded-uri-strings-in-java
     * @param uri the String to verify.
     * @return if true the String is a valid IRI.
     */
    public static Boolean isIRI(Object uri){
        try {
            if(isString(uri)) {
                IRIFactory factory = IRIFactory.uriImplementation();
                IRI iri = factory.construct(toString(uri));
           /* ArrayList<String> a = new ArrayList<>();
            a.add(iri.getScheme());
            a.add(iri.getRawUserinfo());
            a.add(iri.getRawHost());
            a.add(iri.getRawPath());
            a.add(iri.getRawQuery());
            a.add(iri.getRawFragment());*/
                return true;
            }else return false;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * Method to convert a URI os String reference to a resource to a good ID.
     * NOTE: URI string: scheme://authority/path?query#fragment
     * @param uriResource the String or URI reference Resource.
     * @return the String id of the Resource.
     */
    private static String toId(Object uriResource){
        return URI.create(toString(uriResource).replaceAll("\\r\\n|\\r|\\n", " ").replaceAll("\\s+", "_").trim()).toString();
    }

    private static String toString(Object uriResource){
        return String.valueOf(uriResource);
    }

    private static boolean isUri(Object uriResource){
        if(uriResource instanceof URI)return true;
        else{
            try { URI.create(String.valueOf(uriResource));return true;
            }catch(Exception e){ return false;}
        }
    }

    private static boolean isStringNoEmpty(Object uriResource){
        return (uriResource instanceof String && !isNullOrEmpty(String.valueOf(uriResource)));
    }

    private static boolean isStringEmpty(Object uriResource){
        return (uriResource instanceof String && isNullOrEmpty(toString(uriResource)));
    }

    private static boolean isStringOrUriEmpty(Object uriResource){
        return (
                (uriResource instanceof String && isNullOrEmpty(toString(uriResource))) ||
                (uriResource instanceof URI && isNullOrEmpty(toString(uriResource)))
        );
    }

    private static boolean isString(Object uriResource){
        return (uriResource instanceof String);
    }

    private static IRI toIri(Object uriResource){
        return IRIFactory.uriImplementation().construct(toString(uriResource));
    }

    /**
     * Method to Returns true if the parameter is null or empty. false otherwise.
     * @param text string text.
     * @return true if the parameter is null or empty.
     */
    public static boolean isNullOrEmpty(String text) {
        return (text == null) || text.equals("") || text.isEmpty() || text.trim().isEmpty() ;
    }



}//end of the class JenaKit

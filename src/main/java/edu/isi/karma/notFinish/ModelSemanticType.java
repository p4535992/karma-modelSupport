package edu.isi.karma.notFinish;

import edu.isi.karma.util.SparqlUtil;
import edu.isi.karma.util.StringUtilExtended;

/**
 * Created by 4535992 on 26/11/2015.
 */
public class ModelSemanticType {

    private boolean isClass;
    private String nameColumn;
    private String fullType;    //http://schema.org/addressCountry
    private String domainLabel; //gr:BusinessEntity1
    private String domainId;    //http://purl.org/goodrelations/v1#BusinessEntity1
    private String domainUri;   //http://purl.org/goodrelations/v1#BusinessEntity
    private String rdfLiteralType; //xsd:string
    private String id;   //for make simple a simple code by
    //private String metaProperty; //http://purl.org/goodrelations/v1#BusinessEntity1

    public ModelSemanticType(){}

    public ModelSemanticType(boolean isClass,String nameColumn,
                             String fullType,String domainUri,
                             String rdfLiteralType){
        this.id = StringUtilExtended.generateMD5Token(1);
        this.isClass = isClass;
        this.nameColumn = nameColumn;
        this.fullType = fullType;
        this.domainLabel = SparqlUtil.preparePrefixLabel(domainUri)+id;
        this.domainId = domainUri+id;
        this.domainUri = domainUri;
        this.rdfLiteralType = rdfLiteralType;
    }



    public boolean isClass() {
        return isClass;
    }

    public void setIsClass(boolean isClass) {
        this.isClass = isClass;
    }

    public String getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public String getFullType() {
        return fullType;
    }

    public void setFullType(String fullType) {
        this.fullType = fullType;
    }

    public String getDomainLabel() {
        return domainLabel;
    }

    public String getDomainId() {
        return domainId;
    }

    public String getDomainUri() {
        return domainUri;
    }

    public void setDomainUri(String domainUri) {
        this.domainUri = domainUri;
    }

    public String getRdfLiteralType() {
        return rdfLiteralType;
    }

    public void setRdfLiteralType(String rdfLiteralType) {
        this.rdfLiteralType = rdfLiteralType;
    }


}

package edu.isi.karma.notFinish;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import edu.isi.karma.util.SparqlUtil;
import edu.isi.karma.util.StringUtilExtended;

/**
 * Created by tenti on 26/11/2015.
 */
public class ModelColumn {

    private String fullType;    //http://schema.org/addressCountry
    private String domainLabel; //gr:BusinessEntity1
    private String domainId;    //http://purl.org/goodrelations/v1#BusinessEntity1
    private String domainUri;   //http://purl.org/goodrelations/v1#BusinessEntity
    private String rdfLiteralType; //xsd:string
    private String id;   //for make simple a simple code by

    private String rdfProperty;
    private String nameColumn;
    private boolean isClass;
    private boolean isPrimary;

    public String getDomainLabel() {
        return domainLabel;
    }

    public void setDomainLabel(String domainLabel) {
        this.domainLabel = domainLabel;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    private boolean isKey;
    private boolean isTrainAndShowUpdates;


    public ModelColumn(String fullType, String domainUri, String rdfProperty, String nameColumn,
                       boolean isClass, boolean isPrimary, boolean isKey, boolean isTrainAndShowUpdates,
                       XSDDatatype xsd) {
        this.id = StringUtilExtended.generateMD5Token(1);
        this.fullType = fullType;
        this.domainUri = domainUri;
        this.rdfProperty = rdfProperty;
        this.nameColumn = nameColumn;
        this.isClass = isClass;
        this.isPrimary = isPrimary;
        this.isKey = isKey;
        this.isTrainAndShowUpdates = isTrainAndShowUpdates;
        this.rdfLiteralType = xsd.getURI().replace(XSDDatatype.XSD, "xsd:");//xsd:string;
        this.domainLabel = SparqlUtil.preparePrefixLabel(domainUri)+id;
        this.domainId = domainUri+id;
    }

    public ModelColumn(){}

    public String getRdfLiteralType() {
        return rdfLiteralType;
    }

    public void setRdfLiteralType(XSDDatatype xsd) {
        this.rdfLiteralType = xsd.getURI().replace(XSDDatatype.XSD, "xsd:");
    }

    public void setRdfLiteralType(String rdfLiteralType) {
        this.rdfLiteralType = rdfLiteralType;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setIsKey(boolean isKey) {
        this.isKey = isKey;
    }

    public boolean isTrainAndShowUpdates() {
        return isTrainAndShowUpdates;
    }

    public void setIsTrainAndShowUpdates(boolean isTrainAndShowUpdates) {
        this.isTrainAndShowUpdates = isTrainAndShowUpdates;
    }

    public boolean isClass() {
        return isClass;
    }

    public void setIsClass(boolean isClass) {
        this.isClass = isClass;
    }

    public String getFullType() {
        return fullType;
    }

    public void setFullType(String fullType) {
        this.fullType = fullType;
    }

    public String getDomainUri() {
        return domainUri;
    }

    public void setDomainUri(String domainUri) {
        this.domainUri = domainUri;
    }

    public String getRdfProperty() {
        return rdfProperty;
    }

    public void setRdfProperty(String rdfProperty) {
        this.rdfProperty = rdfProperty;
    }

    public String getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }
}

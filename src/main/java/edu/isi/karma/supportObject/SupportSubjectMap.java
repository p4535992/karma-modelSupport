package edu.isi.karma.supportObject;


import edu.isi.karma.kr2rml.NamedGraph;
import edu.isi.karma.kr2rml.SubjectMap;
import edu.isi.karma.kr2rml.template.ColumnTemplateTerm;
import edu.isi.karma.kr2rml.template.StringTemplateTerm;
import edu.isi.karma.kr2rml.template.TemplateTerm;
import edu.isi.karma.kr2rml.template.TemplateTermSet;
import edu.isi.karma.rep.alignment.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SupportSubjectMap {

    private SubjectMap subjectMap;
    private String id;
    private String columnHNodeId;
    private String graphUri;
    private NamedGraph graph;
    private List<TemplateTermSet> rdfsType;
    private List<String> rdfsTypeString;

<<<<<<< HEAD
    private String nameSpaceKarma = "http://isi.edu/integration/karma/dev#"+this.id;
=======
    private String nameSpaceKarmaId = "http://isi.edu/integration/karma/dev#"+this.id;

    public String getNameSpaceKarma() {
        return nameSpaceKarmaId ;
    }

    public void setNameSpaceKarma(String nameSpaceKarma) {
        this.nameSpaceKarmaId  = nameSpaceKarma;
    }
>>>>>>> origin/master

    public SupportSubjectMap() {}

    public SupportSubjectMap(String id,String columnName,List<String> rdfsType,String graphUri) {
<<<<<<< HEAD
=======
        this.id = id;
>>>>>>> origin/master
        this.columnHNodeId = columnName;
        this.rdfsType = createRdfsType(rdfsType);
        this.rdfsTypeString = rdfsType;
        this.graphUri = graphUri;
        this.graph = createGraph(graphUri);
        this.subjectMap = createSubjectMap(id, createTemplateTermSetByColumn(columnName), this.graph, this.rdfsType);
    }

<<<<<<< HEAD
    public SupportSubjectMap(String id,String columnName,String rdfType,String graphUri) {
=======
    public SupportSubjectMap(String id,String columnName,List<String> rdfsType) {
        this.id = id;
        this.columnHNodeId = columnName;
        this.rdfsType = createRdfsType(rdfsType);
        this.rdfsTypeString = rdfsType;
        this.subjectMap = createSubjectMap(id, createTemplateTermSetByColumn(columnName), this.graph, this.rdfsType);
    }

    public SupportSubjectMap(String id,String columnName,String rdfType,String graphUri) {
        this.id = id;
>>>>>>> origin/master
        this.columnHNodeId = columnName;
        this.rdfsType = createRdfsType(rdfType);
        this.rdfsTypeString = Collections.singletonList(rdfType);
        this.graphUri = graphUri;
        this.graph = createGraph(graphUri);
        this.subjectMap = createSubjectMap(id, createTemplateTermSetByColumn(columnName), this.graph, this.rdfsType);
    }

    public SupportSubjectMap(SubjectMap subjectMap) {
        this.id = subjectMap.getId();
        this.columnHNodeId = subjectMap.getTemplate().getAllTerms().get(0).getTemplateTermValue();
        this.rdfsType = subjectMap.getRdfsType();
        List<String> list = new ArrayList<>();
        for(TemplateTermSet set: rdfsType){
            for(TemplateTerm term : set.getAllTerms()){
                list.add(term.getTemplateTermValue());
            }
        }
        this.rdfsTypeString = list;
        if(subjectMap.getGraph() != null) {
            this.graphUri = subjectMap.getGraph().getGraphLabel().getUri();
            this.graph = subjectMap.getGraph();
        }
        this.subjectMap = subjectMap;

    }

   /* private TemplateTermSet createTemplateTermSetByString(List<String> termValueStrings,Boolean hasUri){
        TemplateTermSet tts = new TemplateTermSet();
        for(String termValue :termValueStrings){
            tts.addTemplateTermToSet(new StringTemplateTerm(termValue,hasUri));
        }
        return tts;
    }*/

    /*public static TemplateTermSet createTemplateTermSetByColumn(List<String> termValueColumns){
        TemplateTermSet tts = new TemplateTermSet();
        for(String termValue : termValueColumns){
            tts.addTemplateTermToSet(new ColumnTemplateTerm(termValue));
        }
        return tts;
    }*/

    private TemplateTermSet createTemplateTermSetByColumn(String termValueColumn){
        TemplateTermSet tts = new TemplateTermSet();
        tts.addTemplateTermToSet(new ColumnTemplateTerm(termValueColumn));
        return tts;
    }

    private List<TemplateTermSet> createRdfsType(String rdfTypeTermValue){
        TemplateTermSet tts = new TemplateTermSet();
        tts.addTemplateTermToSet(new StringTemplateTerm(rdfTypeTermValue,true));
        return new ArrayList<TemplateTermSet>(Collections.singletonList(tts));
    }

    private List<TemplateTermSet> createRdfsType(List<String> rdfsTypeTermValues){
        TemplateTermSet tts = new TemplateTermSet();
        for(String termValue :rdfsTypeTermValues){
            tts.addTemplateTermToSet(new StringTemplateTerm(termValue,true));
        }
        return new ArrayList<TemplateTermSet>(Collections.singletonList(tts));
    }

    private SubjectMap createSubjectMap(
            String id,String termValueString,String graphUri,List<String> rdfsType) {
        return createSubjectMap(id,
                createTemplateTermSetByColumn(termValueString),
                createGraph(graphUri),
               createRdfsType(rdfsType));
    }

    private SubjectMap createSubjectMap(
            String id,TemplateTermSet termValue,NamedGraph graph,List<TemplateTermSet> rdfsType){
        return new SubjectMap(id,termValue, graph,rdfsType);
    }

  /*  private SubjectMap createSubjectMap(
            String id,List<String> termValueStrings,String graphUri,List<String> rdfsType){
        TemplateTermSet templateTermSet = createTemplateTermSetByColumn(termValueStrings);
        List<TemplateTermSet> rdfsType2 = createRdfsType(rdfsType);
        return createSubjectMap(id,templateTermSet,graphUri,rdfsType2);
    }*/

    private NamedGraph createGraph(String graphUri){
        return new NamedGraph(new Label(graphUri));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColumnHNodeId() {
        return columnHNodeId;
    }

    public void setColumnHNodeId(String columnHNodeId) {
        this.columnHNodeId = columnHNodeId;
    }

    public String getGraphUri() {
        return graphUri;
    }

    public void setGraphUri(String graphUri) {
        this.graphUri = graphUri;
    }

    public List<String> getRdfsTypeString() {
        return rdfsTypeString;
    }

    public void setRdfsTypeString(List<String> rdfsTypeString) {
        this.rdfsTypeString = rdfsTypeString;
    }

<<<<<<< HEAD
=======
    public SubjectMap getSubjectMap() {
        return subjectMap;
    }

    public void setSubjectMap(SubjectMap subjectMap) {
        this.subjectMap = subjectMap;
    }

>>>>>>> origin/master
    @Override
    public String toString() {
        return "SupportSubjectMap{" +
                "columnHNodeId='" + columnHNodeId + '\'' +
                ", graphUri='" + graphUri + '\'' +
                ", rdfsTypeString=" + rdfsTypeString +
                '}';
    }
}

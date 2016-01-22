package edu.isi.karma.supportObject;

import edu.isi.karma.kr2rml.NamedGraph;
import edu.isi.karma.kr2rml.ObjectMap;
import edu.isi.karma.kr2rml.RefObjectMap;
<<<<<<< HEAD
=======
import edu.isi.karma.kr2rml.SubjectMap;
>>>>>>> origin/master
import edu.isi.karma.kr2rml.planning.TriplesMap;
import edu.isi.karma.kr2rml.template.ColumnTemplateTerm;
import edu.isi.karma.kr2rml.template.TemplateTerm;
import edu.isi.karma.kr2rml.template.TemplateTermSet;
import edu.isi.karma.rep.alignment.Label;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SupportObjectMap {

    private ObjectMap objectMap;
    private String id;
    private String columnHNodeId;
    private String graphUri;
    private NamedGraph graph;
    private TemplateTermSet rdfsLiteralTypes;
    private List<String> rdfsLiteralTypesString;

    public SupportObjectMap() {}

    public SupportObjectMap(ObjectMap objectMap) {
        this.id = objectMap.getId();
<<<<<<< HEAD
        this.columnHNodeId = objectMap.getTemplate().getAllTerms().get(0).getTemplateTermValue();
        this.rdfsLiteralTypes = objectMap.getRdfLiteralType();
        List<String> list = new ArrayList<>();
        for(TemplateTerm term : rdfsLiteralTypes.getAllTerms()){
            list.add(term.getTemplateTermValue());
        }
        this.rdfsLiteralTypesString = list;
        if(objectMap.getRefObjectMap().getParentTriplesMap().getSubject().getGraph() !=null){
            this.graph = objectMap.getRefObjectMap().getParentTriplesMap().getSubject().getGraph();
            this.graphUri = this.graph.getGraphLabel().getUri();
=======
        if(objectMap.getTemplate() !=null) {
            this.columnHNodeId = objectMap.getTemplate().getAllTerms().get(0).getTemplateTermValue();
        }else{
            this.columnHNodeId =
                    objectMap.getRefObjectMap().getParentTriplesMap().getSubject()
                            .getTemplate().getAllTerms().get(0).getTemplateTermValue();
        }
        if(objectMap.getRdfLiteralType() !=null) {
            this.rdfsLiteralTypes = objectMap.getRdfLiteralType();
            List<String> list = new ArrayList<>();
            for (TemplateTerm term : rdfsLiteralTypes.getAllTerms()) {
                list.add(term.getTemplateTermValue());
            }
            this.rdfsLiteralTypesString = list;
        }else{
            this.rdfsLiteralTypesString = new ArrayList<>();
        }
        if(objectMap.getRefObjectMap()!=null) {
            if (objectMap.getRefObjectMap().getParentTriplesMap().getSubject().getGraph() != null) {
                this.graph = objectMap.getRefObjectMap().getParentTriplesMap().getSubject().getGraph();
                this.graphUri = this.graph.getGraphLabel().getUri();
            }
>>>>>>> origin/master
        }
        this.objectMap = objectMap;
    }

<<<<<<< HEAD
    public SupportObjectMap(String id,String columnName,List<String> rdfsLiteralType,String graphUri) {
=======
   /* public SupportObjectMap(String id,String columnName,List<String> rdfsLiteralType,String graphUri) {
>>>>>>> origin/master
        this.id = id;
        this.columnHNodeId = columnName;
        this.rdfsLiteralTypes = createTemplateTermSetByColumn(rdfsLiteralType);
        this.rdfsLiteralTypesString = rdfsLiteralType;
        this.graphUri = graphUri;
        this.graph = createGraph(graphUri);
        this.objectMap = createObjectMap(id,createTemplateTermSetByColumn(columnHNodeId),rdfsLiteralTypes);
    }

    public SupportObjectMap(String id,String columnName,String rdfLiteralType,String graphUri) {
        this.id = id;
        this.columnHNodeId = columnName;
        this.rdfsLiteralTypes = createTemplateTermSetByColumn(rdfLiteralType);
        this.rdfsLiteralTypesString = Collections.singletonList(rdfLiteralType);
        this.graphUri = graphUri;
        this.graph = createGraph(graphUri);
        this.objectMap = createObjectMap(id,createTemplateTermSetByColumn(columnHNodeId),rdfsLiteralTypes);
    }

    public SupportObjectMap(String id,String columnName,List<String> rdfsLiteralType) {
        this.id = id;
        this.columnHNodeId = columnName;
        this.rdfsLiteralTypes = createTemplateTermSetByColumn(rdfsLiteralType);
        this.rdfsLiteralTypesString = rdfsLiteralType;
        this.objectMap = createObjectMap(id,createTemplateTermSetByColumn(columnHNodeId),rdfsLiteralTypes);
    }

    public SupportObjectMap(String id,String columnName,String rdfLiteralType) {
        this.id = id;
        this.columnHNodeId = columnName;
        this.rdfsLiteralTypes = createTemplateTermSetByColumn(rdfLiteralType);
        this.rdfsLiteralTypesString = Collections.singletonList(rdfLiteralType);
        this.objectMap = createObjectMap(id,createTemplateTermSetByColumn(columnHNodeId),rdfsLiteralTypes);
<<<<<<< HEAD
    }

    private ObjectMap createObjectMap(String id,TemplateTermSet template,TemplateTermSet rdfLiteralType){
=======
    }*/

    /*private ObjectMap createObjectMap(String id,TemplateTermSet template,TemplateTermSet rdfLiteralType){
>>>>>>> origin/master
        return new ObjectMap(id,template,rdfLiteralType);
    }

    private ObjectMap createObjectMap(String id,RefObjectMap refObjectMap){
        return new ObjectMap(id,refObjectMap);
    }

    private RefObjectMap createRefObjectMap(String id,TriplesMap triplesMap){
        return new RefObjectMap(id,triplesMap);
    }

    public static TemplateTermSet createTemplateTermSetByColumn(List<String> termValueColumns){
        TemplateTermSet tts = new TemplateTermSet();
        for(String termValue : termValueColumns){
            tts.addTemplateTermToSet(new ColumnTemplateTerm(termValue));
        }
        return tts;
    }

    private TemplateTermSet createTemplateTermSetByColumn(String termValueColumn){
        TemplateTermSet tts = new TemplateTermSet();
        tts.addTemplateTermToSet(new ColumnTemplateTerm(termValueColumn));
        return tts;
    }

    private NamedGraph createGraph(String graphUri){
        return new NamedGraph(new Label(graphUri));
<<<<<<< HEAD
    }
=======
    }*/
>>>>>>> origin/master

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

    public NamedGraph getGraph() {
        return graph;
    }

    public void setGraph(NamedGraph graph) {
        this.graph = graph;
    }

    public String getGraphUri() {
        return graphUri;
    }

    public void setGraphUri(String graphUri) {
        this.graphUri = graphUri;
    }

    public List<String> getRdfsLiteralTypesString() {
        return rdfsLiteralTypesString;
    }

    public void setRdfsLiteralTypesString(List<String> rdfsLiteralTypesString) {
        this.rdfsLiteralTypesString = rdfsLiteralTypesString;
    }

    @Override
    public String toString() {
        return "SupportObjectMap{" +
                "columnHNodeId='" + columnHNodeId + '\'' +
                ", graphUri='" + graphUri + '\'' +
                ", rdfsLiteralTypesString=" + rdfsLiteralTypesString +
                '}';
    }
}

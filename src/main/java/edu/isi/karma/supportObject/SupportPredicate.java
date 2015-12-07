package edu.isi.karma.supportObject;


import com.hp.hpl.jena.vocabulary.RDFSyntax;
import edu.isi.karma.kr2rml.Predicate;
import edu.isi.karma.kr2rml.template.StringTemplateTerm;
import edu.isi.karma.kr2rml.template.TemplateTerm;
import edu.isi.karma.kr2rml.template.TemplateTermSet;
import org.openrdf.model.vocabulary.RDF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SupportPredicate {

    private Predicate predicate;
    private String id;
    private List<String> rdfsTypeString;

    public SupportPredicate() {}

    public SupportPredicate(Predicate predicate) {
        this.id = predicate.getId();
        List<String> rdfsType = new ArrayList<>();
        for(TemplateTerm termValue: predicate.getTemplate().getAllTerms()){
            rdfsType.add(termValue.getTemplateTermValue());
        }
        this.rdfsTypeString = cleanList(rdfsType);
        this.predicate = predicate;
    }

    public SupportPredicate(String id,String rdfType) {
        this.id = id;
        this.rdfsTypeString = cleanList(Collections.singletonList(rdfType));
        this.predicate = createPredicate(this.id,this.rdfsTypeString);
    }

    public SupportPredicate(String id,List<String> rdfsType) {
        this.id = id;
        this.rdfsTypeString = cleanList(rdfsType);
        this.predicate = createPredicate(this.id,this.rdfsTypeString);
    }

    private Predicate createPredicate(String id,String rdfType){
        return createPredicate(id,Collections.singletonList(rdfType));
    }

    private Predicate createPredicate(String id,List<String> rdfsType){
        Predicate pred =  new Predicate(id);
        pred.setTemplate(createTemplateTermSetByString(rdfsType,true));
        return pred;
    }

    private TemplateTermSet createTemplateTermSetByString(List<String> termValueStrings,Boolean hasUri){
        TemplateTermSet tts = new TemplateTermSet();
        for(String termValue :termValueStrings){
            tts.addTemplateTermToSet(new StringTemplateTerm(termValue,hasUri));
        }
        return tts;
    }

    private TemplateTermSet createTemplateTermSetByString(String termValueString,Boolean hasUri){
        TemplateTermSet tts = new TemplateTermSet();
        tts.addTemplateTermToSet(new StringTemplateTerm(termValueString,hasUri));
        return tts;
    }

    private List<String> cleanList(List<String> rdfsType){
        for(int i = 0; i < rdfsType.size(); i++){
            if(rdfsType.get(i).contains("http://isi.edu/integration/karma/dev#")){
                rdfsType.set(i,RDF.TYPE.toString());
            }
        }
        return rdfsType;
    }

    public List<String> getRdfType() {
        return rdfsTypeString;
    }

    public void setRdfType(List<String> rdfType) {
        this.rdfsTypeString = rdfType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public String toString() {
        return "SupportPredicate{" +
                "rdfType='" + rdfsTypeString + '\'' +
                '}';
    }
}

package edu.isi.karma.support;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import edu.isi.karma.kr2rml.NamedGraph;
import edu.isi.karma.kr2rml.SubjectMap;
import edu.isi.karma.kr2rml.mapping.KR2RMLMapping;
import edu.isi.karma.kr2rml.template.ColumnTemplateTerm;
import edu.isi.karma.kr2rml.template.TemplateTerm;
import edu.isi.karma.kr2rml.template.TemplateTermSet;
import edu.isi.karma.rep.RepFactory;
import edu.isi.karma.rep.alignment.Label;
import edu.isi.karma.webserver.KarmaException;
import org.apache.commons.collections.map.LinkedMap;
import org.json.JSONArray;

import java.util.*;

public class ApplySubjectMapSupport extends ApplyMappingSupport {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApplySubjectMapSupport .class);

    private LinkedMap linkSubjects;

    protected ApplySubjectMapSupport () {
        getSubjectMap();
    }

    private static  ApplySubjectMapSupport  instance = null;

    public static  ApplySubjectMapSupport  getInstance(){
        if(instance == null)  instance = new  ApplySubjectMapSupport ();
        return instance;
    }

    protected Map<String,SubjectMap> getSubjectMap(){
        if(linkSubjects == null)  this.linkSubjects = new LinkedMap(mapping.getSubjectMapIndex());
        return linkSubjects;
    }

    //TESTED
    protected boolean hasSubjectImpl(String name){
        for(Map.Entry<String,SubjectMap> entry : getSubjectMap().entrySet()){
            List<TemplateTerm> listTerm = entry.getValue().getTemplate().getAllTerms();
            for(TemplateTerm s : listTerm) {
                if(s.getTemplateTermValue().equalsIgnoreCase(name)){
                    return true;
                }
            }
        }
        return false;
    }

    //TESTED
    protected TemplateTerm readTemplateTermImpl(String name){
        for(Map.Entry<String,SubjectMap> entry : getSubjectMap().entrySet()){
            List<TemplateTerm> listTerm = entry.getValue().getTemplate().getAllTerms();
            for(TemplateTerm s : listTerm) {
                if(s.getTemplateTermValue().equalsIgnoreCase(name)){
                    return s;
                }
            }
        }
        return null;
    }

    //TESTED
    protected SubjectMap readSubjectMapByKeyImpl(String key){
        return getSubjectMap().get(key);
    }

    //TESTED
    protected SubjectMap readSubjectMapByNameImpl(String name){
        for(Map.Entry<String,SubjectMap> entry : getSubjectMap().entrySet()){
            SubjectMap sub = entry.getValue();
            List<TemplateTerm> listTerm = sub.getTemplate().getAllTerms();
            for(TemplateTerm s : listTerm) {
                if(s.getTemplateTermValue().equalsIgnoreCase(name)){
                    return sub;
                }
            }
        }
        return null;
    }

    //TESTED
    protected TemplateTerm readTemplateTermImpl(int indexSubject,int indexTerm){
        return readTemplateTermsImpl(indexSubject).get(indexTerm);
    }

    //TESTED
    protected List<TemplateTerm> readTemplateTermsImpl(int indexSubject){
        SubjectMap sm = (SubjectMap) linkSubjects.getValue(indexSubject);
        return sm.getTemplate().getAllTerms();
    }

    //TESTED
    protected TemplateTermSet readRdfTypeImpl(int indexSubject,int indexTerm){
        return readRdfsTypeImpl(indexSubject).get(indexTerm);
    }

    //TESTED
    protected List<TemplateTermSet> readRdfTypeImpl(String name){
        for(Map.Entry<String,SubjectMap> entry : getSubjectMap().entrySet()){
            List<TemplateTerm> listTerm = entry.getValue().getTemplate().getAllTerms();
            for(TemplateTerm s : listTerm) {
                if(s.getTemplateTermValue().equalsIgnoreCase(name)){
                    return entry.getValue().getRdfsType();
                }
            }
        }
        return null;
    }

    //TESTED
    protected TemplateTermSet readRdfTypeImpl(String name,int indexTerm){
        for(Map.Entry<String,SubjectMap> entry : getSubjectMap().entrySet()){
            List<TemplateTerm> listTerm = entry.getValue().getTemplate().getAllTerms();
            for(TemplateTerm s : listTerm) {
                if(s.getTemplateTermValue().equalsIgnoreCase(name)){
                    return entry.getValue().getRdfsType().get(indexTerm);
                }
            }
        }
        return null;
    }

    //TESTED
    protected List<TemplateTermSet> readRdfsTypeImpl(int indexSubject){
        SubjectMap sm = (SubjectMap) linkSubjects.getValue(indexSubject);
        return sm.getRdfsType();
    }

    //------------------------------------------------------------------------------------


    protected void updateGraph(SubjectMap subject,String uriRef){
        subject.setGraph(new NamedGraph(new Label(uriRef)));
    }



    protected void updateTemplateTermValueImpl(SubjectMap subject,String uri){
        List<TemplateTermSet> listTerm = new ArrayList<>();
        TemplateTermSet tts = new TemplateTermSet();

    }

    protected void updateTemplateStringValue(String oldValue,String newValue){
        TemplateTerm tts = readTemplateTermImpl(oldValue);

    }


}

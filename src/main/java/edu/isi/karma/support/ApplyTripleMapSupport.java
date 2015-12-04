package edu.isi.karma.support;

import edu.isi.karma.kr2rml.*;
import edu.isi.karma.kr2rml.planning.TriplesMap;
import edu.isi.karma.kr2rml.template.TemplateTerm;
import edu.isi.karma.kr2rml.template.TemplateTermSet;
import edu.isi.karma.rep.alignment.Label;
import edu.isi.karma.webserver.KarmaException;
import edu.stanford.nlp.util.ArrayMap;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.collections.map.MultiValueMap;

import java.util.*;

public class ApplyTripleMapSupport extends ApplyMappingSupport {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApplyTripleMapSupport.class);

    private LinkedMap linkTriples;

    protected ApplyTripleMapSupport() {
        getTriplesMap();
        if(isOffline  && !isSetted){
            try {
                setupKarmaMetadata();
            } catch (KarmaException e) {
                logger.error("Error occurred while initializing the Karma MetaData...");
                System.exit(-1);
            }
        }
    }

    protected ApplyTripleMapSupport(boolean isOffline) {
        this.isOffline = isOffline;
        new ApplyTripleMapSupport();
    }

    private static ApplyTripleMapSupport instance = null;

    public static ApplyTripleMapSupport getInstance(){
        if(instance == null)  instance = new ApplyTripleMapSupport();
        return instance;
    }

    public static ApplyTripleMapSupport getInstance(boolean isOffline){
        if(instance == null)  instance = new ApplyTripleMapSupport(isOffline);
        return instance;
    }

    protected Map<String,TriplesMap> getTriplesMap(){
        if(linkTriples == null) {
            this.linkTriples = new LinkedMap(mapping.getTriplesMapIndex());
        }
        return linkTriples;
    }

    protected boolean hasTriplesMapImpl(String name){
        for(Map.Entry<String,TriplesMap> entry : getTriplesMap().entrySet()){
            List<TemplateTerm> listTerm = entry.getValue().getSubject().getTemplate().getAllTerms();
            for(TemplateTerm s : listTerm) {
                if(s.getTemplateTermValue().equalsIgnoreCase(name)){
                    return true;
                }
            }
        }
        return false;
    }

    protected List<PredicateObjectMap> readPredicateObjectBySubjectImpl(String name){
        List<PredicateObjectMap> list = new ArrayList<>();
        for(Map.Entry<String,TriplesMap> entry : getTriplesMap().entrySet()){
            //List<PredicateObjectMap> listPredicate = entry.getValue().getPredicateObjectMaps();
            List<TemplateTerm> listTerm = entry.getValue().getSubject().getTemplate().getAllTerms();
            for(TemplateTerm s : listTerm) {
                if(s.getTemplateTermValue().equalsIgnoreCase(name)){
                    list.addAll(entry.getValue().getPredicateObjectMaps());
                }
            }

        }
        return list;
    }

    protected TriplesMap readTriplesMapByKeyImpl(String key){
        return getTriplesMap().get(key);
    }

    protected TriplesMap readTriplesMapByNameImpl(String name){
        for(Map.Entry<String,TriplesMap> entry : getTriplesMap().entrySet()){
            SubjectMap sub = entry.getValue().getSubject();
            List<TemplateTerm> listTerm = sub.getTemplate().getAllTerms();
            for(TemplateTerm s : listTerm) {
                if(s.getTemplateTermValue().equalsIgnoreCase(name)){
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    protected PredicateObjectMap readPredicateObjectMapImpl(int indexSubject,int indexTerm){
        return readPredicateObjectMapsImpl(indexSubject).get(indexTerm);
    }

    protected List<PredicateObjectMap> readPredicateObjectMapsImpl(int indexSubject){
        TriplesMap sm = (TriplesMap) linkTriples.getValue(indexSubject);
        return sm.getPredicateObjectMaps();
    }

    protected Predicate readPredicateImpl(int indexSubject,int indexTerm){
        int index = 0;
        for(Map.Entry<Predicate,ObjectMap> pred : readPredicateObjectsImpl(indexSubject).entrySet()){
            if(index == indexTerm){
                return pred.getKey();
            }else{
                index++;
            }
        }
        return  null;
    }

    protected ObjectMap readObjectImpl(int indexSubject,int indexTerm){
        int index = 0;
        Map map = readPredicateObjectsImpl(indexSubject);
        Set entrySet = map.entrySet();
        Iterator it = entrySet.iterator();
        List list;
        while (it.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) it.next();
            list = (List) map.get(mapEntry.getKey());
            if(index == indexSubject) {
                for(int j = 0; j < list.size(); j++) {
                    // System.out.println("\t" + mapEntry.getKey() + "\t  " + list.get(j));
                    return (ObjectMap) list.get(indexTerm);
                }
                break;
            }
            index++;
        }
        return  null;
    }

    protected List<Predicate> readPredicatesBySubjectImpl(String subject){
        List<Predicate> predicates = new ArrayList<>();
        for(Map.Entry<Predicate,ObjectMap> pred : readPredicateObjectsImpl(subject).entrySet()){
            predicates.add(pred.getKey());
        }
        return predicates;
    }

    protected List<ObjectMap> readObjectMapBySubjectImpl(String subject){
        List<ObjectMap> objects = new ArrayList<>();
        for(Map.Entry<Predicate,ObjectMap> obj: readPredicateObjectsImpl(subject).entrySet()){
            objects.add(obj.getValue());
        }
        return objects;
    }

    /*protected TemplateTermSet readPredicateImpl(String name,int indexTerm){
        for(Map.Entry<String,SubjectMap> entry : getTriplesMap().entrySet()){
            List<TemplateTerm> listTerm = entry.getValue().getTemplate().getAllTerms();
            for(TemplateTerm s : listTerm) {
                if(s.getTemplateTermValue().equalsIgnoreCase(name)){
                    return entry.getValue().getRdfsType().get(indexTerm);
                }
            }
        }
        return null;
    }*/

    protected Map<Predicate,ObjectMap> readPredicateObjectsImpl(int indexSubject){
        Map<Predicate,ObjectMap> map= new MultiValueMap();
        TriplesMap sm = (TriplesMap) linkTriples.getValue(indexSubject);
        List<PredicateObjectMap> listPo = sm.getPredicateObjectMaps();
        for(PredicateObjectMap pom : listPo){
            map.put(pom.getPredicate(),pom.getObject());
        }
        return map;
    }

    protected Map<Predicate,ObjectMap> readPredicateObjectsImpl(String subject){
        Map<Predicate,ObjectMap> map= new MultiValueMap();
        TriplesMap sm = (TriplesMap) linkTriples.get(subject);
        List<PredicateObjectMap> listPo = sm.getPredicateObjectMaps();
        for(PredicateObjectMap pom : listPo){
            map.put(pom.getPredicate(),pom.getObject());
        }
        return map;
    }

    //-------------------------------------------------------------------------------------------------


}

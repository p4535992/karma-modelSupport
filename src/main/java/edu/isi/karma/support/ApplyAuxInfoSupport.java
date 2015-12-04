package edu.isi.karma.support;

import edu.isi.karma.kr2rml.KR2RMLVersion;
import edu.isi.karma.kr2rml.Prefix;
import edu.isi.karma.kr2rml.mapping.KR2RMLMappingAuxillaryInformation;
import edu.isi.karma.webserver.KarmaException;

import java.io.File;
import java.util.List;

/**
 * Created by 4535992 on 30/11/2015.
 * @author 4535992.
 * @version 2015-11-30.
 */
public class ApplyAuxInfoSupport extends ApplyMappingSupport {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApplyAuxInfoSupport.class);
    protected KR2RMLMappingAuxillaryInformation auxInfo;

    protected ApplyAuxInfoSupport() {}

    private static ApplyAuxInfoSupport instance = null;

    public static ApplyAuxInfoSupport getInstance(){
        if(instance == null) {
            instance = new ApplyAuxInfoSupport();
        }
        return instance;
    }

    public void extractWorkSheetHistoryFromModel() throws KarmaException {
       this.auxInfo = mapping.getAuxInfo();
    }

    protected String getModelVersion(){
        KR2RMLVersion version = mapping.getVersion();
        return version.toString();
    }

    public String getPrefixes(){
        List<Prefix> prefixes =  mapping.getPrefixes();
        StringBuilder b = new StringBuilder();
        for(Prefix p : prefixes){
            b.append("@prefix ").append(p.getPrefix()).append(" : <").append(p.getNamespace()).append(">.\n");
        }
        return b.toString();
    }

    protected String getSourceName(){
        return mapping.getId().getName();
    }










}

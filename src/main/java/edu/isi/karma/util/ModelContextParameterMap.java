package edu.isi.karma.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.prefs.Preferences;

/**
 * Created by 4535992 on 23/11/2015.
 */
public class ModelContextParameterMap {

        private static HashMap<ContextParameter, String> valuesMap = new HashMap<ContextParameter, String>();

        private static Logger logger = LoggerFactory
                .getLogger(ModelContextParameterMap.class);

        public enum ContextParameter {
            USER_DIRECTORY_PATH,
            PRELOADED_ONTOLOGY_DIRECTORY,
            USER_PREFERENCES_DIRECTORY,
            USER_CONFIG_DIRECTORY,
            R2RML_PUBLISH_DIR, R2RML_PUBLISH_RELATIVE_DIR,
            R2RML_USER_DIR,
        }

        static {

            // Find a safe place to store preferences
            String karmaDir = System.getenv("KARMA_USER_HOME");
            if(karmaDir == null)
            {
                karmaDir = System.getProperty("KARMA_USER_HOME");
                if(karmaDir == null) {
                    Preferences preferences = Preferences.userRoot().node("WebKarma");
                    karmaDir = preferences.get("KARMA_USER_HOME",  null);
                }
            }
            if(karmaDir == null)
            {
                String defaultLocation = System.getProperty("user.home") + File.separator + "karma";
                logger.info("KARMA_USER_HOME not set.  Defaulting to " + defaultLocation);
                File newKarmaDir = new File(defaultLocation);
                karmaDir = newKarmaDir.getAbsolutePath() + File.separator;

            }
            if(!karmaDir.endsWith(File.separator))
            {
                karmaDir += File.separator;
            }
            setParameterValue(ContextParameter.USER_DIRECTORY_PATH, karmaDir);
            logger.info("Karma home: " + karmaDir);

        }
        public static void setParameterValue(ContextParameter param, String value) {
            valuesMap.put(param, value);
        }

        public static String getParameterValue(ContextParameter param) {
            if (valuesMap.containsKey(param))
                return valuesMap.get(param);
//		logger.error("Parameter value does not exist! " + param);

            return "";
        }







}

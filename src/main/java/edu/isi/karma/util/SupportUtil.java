package edu.isi.karma.util;

import edu.isi.karma.rep.Worksheet;
import edu.isi.karma.rep.Workspace;
import edu.isi.karma.rep.WorkspaceManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 4535992 on 30/11/2015.
 * @author 4535992.
 * @version 2015-11-30.
 */
public class SupportUtil {

    public static boolean hostAvailabilityCheck(String server_address,int tcp_server_port) {
        try (Socket s = new Socket(server_address, tcp_server_port)) {
            if(s.isConnected()) s.close();
            return true;
        } catch (IOException ex) {
            /* ignore */
        }
        return false;
    }

    public static boolean isOffline(String server_address,int tcp_server_port){
        return !hostAvailabilityCheck(server_address,tcp_server_port);
    }


    public static Workspace createNewWorkspace(){
        return WorkspaceManager.getInstance().createWorkspace();
    }

    public static  Worksheet createNewWorkSheet(String tableName,Workspace workspace){
        return workspace.getFactory().createWorksheet(tableName, workspace, "UTF-8");
    }

    public static String generateMD5Token(int lengthToken){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        StringBuilder hexString = new StringBuilder();
        byte[] data = md.digest(RandomStringUtils.randomAlphabetic(lengthToken).getBytes());
        for (byte aData : data) {
            hexString.append(Integer.toHexString((aData >> 4) & 0x0F));
            hexString.append(Integer.toHexString(aData & 0x0F));
        }
        return hexString.toString();
    }

    public static void writePrettyPrintedJSONObjectToFile(JSONObject json){
        String prettyPrintedJSONString = json.toString(4);
        System.out.println(prettyPrintedJSONString);
    }
}

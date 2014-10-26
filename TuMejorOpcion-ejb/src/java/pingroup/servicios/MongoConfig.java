/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pingroup.servicios;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author estudiante
 */
public class MongoConfig {
    
    private MongoClient mongoClient;
    public DB db;

    public MongoConfig() {
        try {
            mongoClient = new MongoClient("localhost", 27017);
        } catch (UnknownHostException ex) {
            Logger.getLogger(MongoConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        db = mongoClient.getDB("test");
    }
}

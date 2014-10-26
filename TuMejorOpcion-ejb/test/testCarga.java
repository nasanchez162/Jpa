/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import pingroup.servicios.ServicioFacebook;
import pingroup.vos.Usuario;


/**
 *
 * @author estudiante
 */
public class testCarga {
    
    ServicioFacebook fb;
    
    @Rule
    public ContiPerfRule i = new ContiPerfRule();
    
    public testCarga() {
    }
    
    @Before
    public void setUp() {
        fb = new ServicioFacebook();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    @PerfTest(invocations = 600, threads = 600)
    public void testLogin() throws InterruptedException {
        String token  = "CAAUyjwcTV5sBAHmUq9fq0d0ZAEYTAuSs0iPCoACFmumec6CG3PJoaPKAb4DIu39UYZCBUDqp9HAladARsH3K7TpZBFj2LTEq2zdZCANsKR8ZBnOvwqFtMpOGeZCTEcrYHGYSDwYBX7uPeh4EEYiSZCeWsNNcP325qFuxgL25sqEMMzaN9rXyBQN3DQUUDduCdsUX3odbMqZAexZBnhWxMA0o2fqU6FL9NNiAZD";
        Usuario u = fb.getUsuario(token);
        fb.getAmigosQueUsanApp(token, u);
        try {
        fb.getTiendasEnLikes(token, u);
        } catch(Exception e) {
            e.printStackTrace();
        }
        fb.getTiendasEnWall(token, u);
    }
    
   /* 
    @Test
    @PerfTest(invocations = 92188, threads = 800)
    public void testRest() throws ProtocolException, IOException {
        
        URL url = new URL("http://localhost:8080/TuMejorOpcion_-_servicios/webresources/pingroup.servicios/validarCupon/EmanuelEsUnPingo/");
            
            HttpURLConnection yc = (HttpURLConnection) url.openConnection();
            
            yc.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;
            String resp = "";
            while ((inputLine = in.readLine()) != null)
                resp += inputLine;
            in.close();
    }*/
}

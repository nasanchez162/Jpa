/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package pingroup.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.net.ssl.HttpsURLConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import pingroup.interfaces.IServicioFacebookLocal;
import pingroup.interfaces.IServicioPersistenciaLocal;
import pingroup.vos.Cupon;
import pingroup.vos.Tienda;
import pingroup.vos.Usuario;

/**
 *
 * @author estudiante
 */
@Stateless
@Local
public class ServicioFacebook implements IServicioFacebookLocal {
    
    @EJB
    private IServicioPersistenciaLocal persistencia;
    
    public ServicioFacebook() {
     //   persistencia = new ServicioPersistenciaMock();
    }
    
    public List<Tienda> getTiendasEnLikes(String token,Usuario usu) throws Exception{
        
        Vector<Tienda> tiendas = new Vector<Tienda> ();
        
        try {
            URL url = new URL("https://graph.facebook.com/v2.1/me?access_token="+token+"&fields=likes{link,name,category}&format=json&method=get&pretty=0&suppress_http_code=1&limit=300");
            
            HttpsURLConnection yc = (HttpsURLConnection) url.openConnection();
            
            yc.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;
            String json = "";
            while ((inputLine = in.readLine()) != null)
                json += inputLine;
            in.close();
            
            JSONObject resp =  (JSONObject) JSONValue.parse(json);
            
            JSONArray data = ((JSONArray)((JSONObject) (resp.get("likes"))).get("data"));
            
            tiendas = new Vector<Tienda>();
            HashMap<String, String> mapa = new HashMap<String, String>();
            
            for (Tienda tienda : usu.getTiendaLike() ){
                mapa.put(tienda.getIdFacebook(), "");
            }
            for(Object o : data) {
                JSONObject actual = (JSONObject) o;
                
                Tienda temp = persistencia.buscarTiendaPorId(""+actual.get("id"));
                if(temp != null && mapa.get(""+actual.get("id")) == null) {
                    tiendas.add(temp);
                }
            }
            
        } catch(Exception e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return tiendas;
        
    }
    
    
    public List<Usuario> getAmigosQueUsanApp(String token, Usuario usuarioActual) {
        Vector<Usuario> usuarios = new Vector<Usuario>();
        
        try {
            URL url = new URL("https://graph.facebook.com/v2.1/me/friends?access_token="+token+"&fields=installed%2Cname&format=json&method=get&pretty=0&suppress_http_code=1");
            
            HttpsURLConnection yc = (HttpsURLConnection) url.openConnection();
            
            yc.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;
            String json = "";
            
            while ((inputLine = in.readLine()) != null)
                json += inputLine;
            in.close();
            JSONObject resp =  (JSONObject) JSONValue.parse(json);
             
            //System.out.println("!!!!!! AMIGOS resp -> " + resp);
            
            JSONArray data = (JSONArray) resp.get("data");

            
            
            for(Object o : data) {
                JSONObject actual = (JSONObject) o;
                //System.out.println("!!!!!! AMIGOS amigos -> " + o);
                Usuario t = persistencia.buscarUsuarioPorIdFacebook("" + actual.get("id"));
                if(t != null) {
                    
                    usuarios.add(t);
                }
            }
            
        } catch(Exception e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return usuarios;
       
    }
    
//    public Usuario getUsuario(String token) {
//        
//        Usuario usuario = new Usuario();
//        
//        try {
//            URL url = new URL("https://graph.facebook.com/v2.1/me?fields=email,name&access_token="+token+"&fields=installed%2Cname&format=json&method=get&pretty=0&suppress_http_code=1");
//            
//            HttpsURLConnection yc = (HttpsURLConnection) url.openConnection();
//            
//            yc.setRequestMethod("GET");
//            
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(
//                            yc.getInputStream()));
//            String inputLine;
//            String json = "";
//            while ((inputLine = in.readLine()) != null)
//                json += inputLine;
//            in.close();
//            JSONObject resp =  (JSONObject) JSONValue.parse(json);
//            
//            usuario.setIdFacebook("" + resp.get("id"));
//            usuario.setCupones(new Vector<Cupon>());
//            usuario.setTokenFacebook(token);
//            usuario.setUsername(""+resp.get("name"));
//            usuario.setCorreo(""+resp.get("email"));
//            
//            List t = this.getTiendasEnLikes(token);
//            t.addAll(this.getTiendasEnWall(token));
//            usuario.setTiendaLike(t);
//            
//        } catch(Exception e) {
//            //System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//        
//        return usuario;
//        
//    }
    
    public Usuario getUsuario(String token) {
        
        Usuario usuario = new Usuario();
        
        try {
            URL url = new URL("https://graph.facebook.com/v2.1/me?access_token="+token+"&fields=id%2Cname%2Cemail&format=json&method=get&pretty=0&suppress_http_code=1");
            
            HttpsURLConnection yc = (HttpsURLConnection) url.openConnection();
            
            yc.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;
            String json = "";
            while ((inputLine = in.readLine()) != null)
                json += inputLine;
            in.close();
            JSONObject resp =  (JSONObject) JSONValue.parse(json);
            
             //System.out.println("!!!!!! USUARIO token -> " + token);
            //System.out.println("!!!!!! USUARIO resp -> " + resp);
            
            usuario.setIdFacebook("" + resp.get("id"));
            //usuario.setCupones(new Vector<Cupon>());
            usuario.setTokenFacebook(token);
            usuario.setUsername(""+resp.get("name"));
            usuario.setCorreo(""+resp.get("email"));
            usuario.setAmigos(new Vector<Usuario> ());
            usuario.setNombreTiendas(new Vector<String> ());
            usuario.setTiendaLike(new Vector<Tienda>());
            
        } catch(Exception e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
        return usuario;
        
    }
    
    public List<Tienda> getTiendasEnWall(String token, Usuario usu) {
        Vector<Tienda> tiendas = new Vector<Tienda>();
        try {
            URL url = new URL("https://graph.facebook.com/v2.1/me?access_token="+token+"&fields=feed.limit(1000)%7Bstory%7D&format=json&method=get&pretty=0&suppress_http_code=1");
            
            HttpsURLConnection yc = (HttpsURLConnection) url.openConnection();
            
            yc.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;
            String json = "";
            while ((inputLine = in.readLine()) != null)
                json += inputLine;
            in.close();
            
            JSONObject resp =  (JSONObject) JSONValue.parse(json);
      
            //System.out.println("!!!!!! WALL token -> " + token);
            //System.out.println("!!!!!! WALL resp -> " + resp);
            System.out.println("!!! RESP -> " + resp);
            JSONArray data = (JSONArray) ((JSONObject) resp.get("feed")).get("data");
            
            HashMap<String, String> mapa = new HashMap<String, String>();
            
            for (Tienda tienda : usu.getTiendaLike() ){
                mapa.put(tienda.getIdFacebook(), "");
            }
            
            for(Object o : data) {
                JSONObject actual = (JSONObject) o;
                
                for(Tienda t : persistencia.getTiendas()) {
                    if(actual != null && actual.get("story") != null && ((String) actual.get("story")).contains(t.getNombre()) && mapa.get(t.getIdFacebook()) == null)
                        tiendas.add(t);
                }
            }
            
        } catch(Exception e) {
           //System.out.println(e.getMessage());
           e.printStackTrace();
        }
        return tiendas;
    }

    @Override
    public String getUserId(String token) {
        Usuario usuario = new Usuario();
        String ans="";
        try {
            URL url = new URL("https://graph.facebook.com/v2.1/me?fields=email,name&access_token="+token+"&fields=installed%2Cname&format=json&method=get&pretty=0&suppress_http_code=1");
            
            HttpsURLConnection yc = (HttpsURLConnection) url.openConnection();
            
            yc.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;
            String json = "";
            while ((inputLine = in.readLine()) != null)
                json += inputLine;
            in.close();
            JSONObject resp =  (JSONObject) JSONValue.parse(json);
            
            ans= ""+resp.get("id");
            
           
        } catch(Exception e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
        return ans;
    }
}
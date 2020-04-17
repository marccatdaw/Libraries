/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package einess;

import com.db4o.*;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author marccat
 */
public abstract class accesBd {
    //////
    //Atributs
    ////////
    private ObjectContainer bd;
    private Map<String,Integer> size;
    
    //////
    //Constructors
    ////////
  
    public accesBd(String file) {
        bd=Db4oEmbedded.openFile(file);
        size = new HashMap<String, Integer>();
    }
    
    public accesBd(EmbeddedConfiguration config, String file) {
        bd=Db4oEmbedded.openFile(config, file);
        size = new HashMap<String, Integer>();
    }
    
    ///////
    //Metodes
    //////
    
    ///////
    //Buscar tots els objectes que concordin amb aquella classe
    //in: la classe de la cual ha de buscar
    //out: array d'objects de la clase  en concret
    //////
    
    protected Object[] Cercar_tot_soda(Object obj){
        Query query = bd.query();
        query.constrain(obj);
        //s'executa la consulta
        ObjectSet result = query.execute();
        //retorna el resultat en format d'array static
        return result.toArray();
    } 
    
    ///////
    //Buscar l'objecte que concordi  amb aquella classe per el seu 
    // atribut identificador
    //in: la classe de la cual ha de buscar, 
    //out: array d'objects
    //////
    protected Object[] Cercar_una_soda(Object obj,String atribut,String valor){
        Query query = bd.query();
        query.constrain(obj);
        //es defineix el camp per el cual busca
        query.descend(atribut).constrain(valor);
        //executa la sentencia
        ObjectSet result = query.execute();
        //retorna el reusltat amb un array static
        return result.toArray();       
    }
   
    ///////
    //Buscar tots els objectes que concordin amb aquella classe
    //in: la classe de la cual ha de buscar
    //out: array d'objects
    //////
    public Object[] Cercar_byExample(Object obj){
        //fa la cerca amb el objecte com a model
        ObjectSet result = bd.queryByExample(obj);
        //retorna el reusltat amb un array static
        return result.toArray();
    }  
    
    ///////
    //Buscar tots els objectes que concordin amb aquella classe
    //in: la classe de la cual ha de buscar
    //out: retorna un objecte en concret
    //////
    public Object Cercar_una_byExample(Object obj){
        //fa la cerca amb el objecte com a model
        ObjectSet result = bd.queryByExample(obj);
        //es transforma a arrayList
        ArrayList<Object> resultat = Utils.To_ArrayList(result.toArray());
        //retorna el primer objecte (nomes hauria de trobar un)
        return resultat.get(0);
    } 
    
    ///////
    //Rep un objecte i el guarda en la bd
    // atribut identificador
    //in: objecte a guardar 
    //out: retorna un error, en cas que ho hi hagui retornaria un String vuit
    //////
    public String incloure(Object obj){
        //defineix string per guardar el error
        String error="";
        try{
            //casteja i guardar segons la clase ue pertany
            bd.store(Class.forName(obj.getClass().getName()).cast(obj));
        }catch(Exception ex){
            //guarda l'error
            error=ex.getMessage();
        }
        return error;
    }
    
    ///////
    //Rep un objecte que existeix pero ha estat modificat i el torna a guardar
    //a la base de dades sobreescribint la versió anterior
    //in: objecte a guardar 
    //out: retorna un error, en cas que ho hi hagui retornaria un String vuit
    //////
    public String modificar(Object obj){
        //defineix string per guardar el error
        String error="";
        try{
            //casteja i actualitza segons la clase ue pertany
            bd.store(Class.forName(obj.getClass().getName()).cast(obj));
        }catch(Exception ex){
            //guarda l'error
            error=ex.getMessage();
        }
        return error;
    }
    
    ///////
    //Rep un objecte a eliminar
    // atribut identificador
    //in: objecte a guardar 
    //out: retorna un error, en cas que ho hi hagui retornaria un String vuit
    //////
    public String eliminar(Object obj){
        //defineix string per guardar el error
        String error="";
        try{
            //casteja i elimina segons la clase ue pertany
            bd.delete(Class.forName(obj.getClass().getName()).cast(obj));
        }catch(Exception ex){
            //guarda l'error
            error=ex.getMessage();
        }
        return error;
    }
    
    ///////
    //comprova si el obj que es vol guardar ja esta a la bd
    // atribut identificador
    //in: objecte a guardar 
    //out: retorna boleà true si existeix i false sino
    //////
    public Object comprobar(Object obj){
        boolean cert = false;
        //busca el objecte
        Object[] buscar = Cercar_byExample(obj);
        Object obj_retornar = null;
        //comprova ue hi sigui
        if(buscar.length > 0){
            //indica ue si la trobat
            cert=true;
        }
        if(cert){
            //es el objecte ue retornara
            obj_retornar = buscar[0];
        }
        return obj_retornar;
    }
    
       
    ///////
    // rep el possible error ue pugi generar una acció is no hi ha anunciara 
    // tot ha sigut correcte
    // in: string  amb el possible error
    // out: - 
    //////
    public void mostrar_error(String error){
        //getiona si hi ha error
        if(error.equals("")){
            System.out.println("La accio s'ha realitzat Correctament");
        }else{
            System.out.println(error);
        }
    }
    
    

    ///////
    //Tanca la bd
    //in: la classe de la cual ha de buscar, 
    //out: -
    //////
    public void tancar(){
        //tanca la bd
        bd.close();
    }
}
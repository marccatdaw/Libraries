/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package einess;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author marccat
 */
public class sockets {
    
    /////////////////////////////////////////
    //reb un String del client o del servidor
    //in: demana el socket del kual rebra informació
    //out: el missatge ie han enviat desde l'altre banda
    //////////////////////////////////////
    public static String rebre(Socket socket) throws Exception{
        String msg;
        try
        {
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            msg = (String) ois.readObject();
        }
        catch(Exception e)
        {
                throw e;
        }
        return msg;
    } 
    
    /////////////////////////////////////////
    //Envia informació al client o al servidor
    //in: demana el socket de ki s'ha de enviar i el String ke enviara
    //out: -
    //////////////////////////////////////
    public static void enviar(Socket socket, String msg) throws Exception{
        try
        {
                OutputStream os = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(msg);
        }
        catch(Exception e)
        {
                throw e;
        }
    }
    
    
    
    /////////////////////////////////////////
    //reb un objecte del client o del servidor
    //in: demana el socket del kual rebra informació
    //out: retorna el objecte ue reb
    //////////////////////////////////////
    public static Object rebre_byte(Socket socket) throws Exception{
            Object obj;
            try
            {
                  InputStream is = socket.getInputStream();
                  ObjectInputStream ois = new ObjectInputStream(is);
                  obj = (Object) ois.readObject();

            }catch(Exception e)
            {
                    throw e;
            }
            return obj;
    }
   
    
    /////////////////////////////////////////
    //envia un objecte al client o al servidor
    //in: demana el socket i el objecte aue ha de enviar
    //out: -
    //////////////////////////////////////
    public static void enviar_byte(Socket socket, Object obj) throws Exception{
        try
        {
                OutputStream os = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(obj);
        }
        catch(Exception e)
        {
                throw e;
        }
    }
    
    
    /////////////////////////////////////////
    //envia un objecte/missatge a totes les coneccions
    //in: string (missatge a enviar), destinataris (arrylist de sockets) 
    //out: -
    //////////////////////////////////////
    public static void compartir_tots(String missatge,ArrayList sockets) throws Exception{
        Iterator<Socket> s = sockets.iterator();
        while(s.hasNext()){
            Socket jug = s.next();
            enviar(jug,missatge);
        }
    }
    
    
    /////////////////////////////////////////
    //tanca la coneccio del tots els socket rebuts(com a possibles detinataris)
    //in: destinataris/clients (arrylist de sockets) 
    //out: -
    //////////////////////////////////////
    public static void tancar_conexio(ArrayList sockets) throws IOException{ 
        
        Iterator<Socket> s = sockets.iterator();
        while(s.hasNext()){
                Socket jud = s.next();
                jud.close();
           
        }
    }
}

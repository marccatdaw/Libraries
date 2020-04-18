 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package einess;

import einess.exceptions.NumberDNINotCorrectException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marccat
 */
public class Utils {
    
    public static final String ISO ="ISO-8859-1";
    
    
     /**     
     *  Tranformació de dades
     */
    
    ////////////////////////////////////
    //utilitz el simpledataformat per transformar un string a date
    //in: demana la data en format String
    //out: retornara un Date o be una excepció
    ///////////////////////////////////
    public static Date  data_parse (String data, String patro) throws ParseException{
        SimpleDateFormat formatter1;
        formatter1 = new SimpleDateFormat(patro);
        Date  dataRetorn;
        dataRetorn = formatter1.parse(data);
        return dataRetorn;
    }
    
    ////////////////////////////////////
    //utilitz el simpledataformat per transformar un date a string
    //in: demana la data en format String
    //out: retornara un Date o be una excepció
    ///////////////////////////////////
    public static String  data_format (Date data, String patro) throws ParseException{
        SimpleDateFormat formatter1;
        formatter1 = new SimpleDateFormat(patro);
        String  dataStringRetorn;
        dataStringRetorn = formatter1.format(data);
        return dataStringRetorn;
    }
       
    
    /**
     *  Colleccions
     */ 

    ///////
    //Transforma un Object[] en ArrayList<>
    //in: Array de Object
    //out: ArrayList
    //////
    public static ArrayList To_ArrayList(Object[] obj) {
      ArrayList ar = new ArrayList();
        for (Object obj1 : obj) {
            try {
                ar.add(Class.forName(obj1.getClass().getName()).cast(obj1));
            }catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
                ar.clear();
            }
       }
       return ar;
    }

    ///////
    //Mostra Object[] 
    //in: Array de Object
    //out: -
    //////
    public static String show_Array(Object[] obj) {
        StringBuilder b = new StringBuilder();  
               
        for (Object obj1 : obj) {
            try {
                b.append((Class.forName(obj1.getClass().getName()).cast(obj1)));
            }catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
       }
       return b.toString();
    }
        
    
    
   /**     
     *  Lectura i escriptura de Fitxers
     */
      
    
    ////////////////////////////////////
    //demanar el nom d'un fitxer  i comprova si auet existeix a la ruta es-
    //pecificada
    //in: demanara que ha de introduir l'usuari, i la ruta d'on esta el fitxer
    //out: retorna un bolea si existeix o no 
    ///////////////////////////////////
    public static boolean llegir_nom_fitxer_String(String ruta){            
        File fitxer = new File(ruta);      
        return fitxer.exists();
    }

    //////
    // afaga el contingut d'un fitxer i guarda cada linia en una possició 
    // del arrayList.
    // in: string
    // out: arrayList<String>
    //////////
    public static ArrayList<String> read_line_to_line(String ruta){
        ArrayList<String> item = new ArrayList<String>();
        File file = new File(ruta);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                //System.out.println(sc.nextLine().toString());
                item.add(sc.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.out.println("error: "+e.getMessage());
            
        }  finally {
            if (sc != null) {
                sc.close();
            }
        }
        return item;
    }
    
    //////
    // afaga el contingut d'un fitxer i guarda cada linia en una possició.
    //  
    // del arrayList.
    // in: string
    // out: arrayList<String>
    //////////
    public static ArrayList<String[]> read_line_to_line(String ruta,String separator){
        ArrayList<String[]> item = new ArrayList<String[]>();
        File file = new File(ruta);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                //System.out.println(sc.nextLine().toString());
                String param = sc.nextLine().toString();
                String[] parts = param.split(separator);         
                item.add(parts);
            }

        } catch (FileNotFoundException e) {
            System.out.println("error: "+e.getMessage());
            
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        return item;
    }
    
    public static void write_line_to_line_without_scanner(ArrayList<String> info, String ruta, String cod){
        File fichero = null;
        BufferedWriter bw = null;
        try
        {
            fichero = new File(ruta);
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ruta), cod));
            for (int i = 0; i < info.size(); i++)
               bw.write(info.get(i)+"\n");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              bw.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    
    }
    
    
    
    public static ArrayList<String> read_line_to_line_without_scanner(String ruta, String cod){
        ArrayList<String> item = new ArrayList<String>();
        File file = new File(ruta);
       
        try {
            
           BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(ruta), cod));
           String sCadena="";
           while ((sCadena = bf.readLine())!=null) {
            item.add(sCadena);
           }
        
        } catch (FileNotFoundException e) {
             System.out.println("error: "+e.getMessage());
        } catch (IOException e) {
            System.out.println("error: "+e.getMessage());
        }
        
        return item;
    }

     public static ArrayList<String[]> read_line_to_line_without_scanner(String ruta, String cod, String separator){
        ArrayList<String[]> item = new ArrayList<String[]>();
        File file = new File(ruta);
       
        try {
            
           BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(ruta), cod));
           String sCadena="";
          
           while ((sCadena = bf.readLine())!=null) {
                String[] parts = sCadena.split(separator);         
                item.add(parts);
           }
        
        } catch (FileNotFoundException e) {
             System.out.println("error: "+e.getMessage());
        } catch (IOException e) {
            System.out.println("error: "+e.getMessage());
        }
        
        return item;
    }
    
    /**
     *  Altres
     */
    
    ////////////////////////////////////////
    // comproba si l'objecte es de la classe correcte
    // in: obj, string
    // out: boolean
    ////////
    public static boolean isCast(Object class1, String nomclass){
        boolean cert = false;
        String nomclasse_fullname = class1.getClass().toString();
        String nomclasse = nomclasse_fullname.substring(nomclasse_fullname.indexOf(".")+1);
       
        if(nomclasse.equals(nomclass)){
            cert=true;
        }
        return cert;     
    } 
    
    
    ///////
    //obte un numero random, entre dos numeros indicats
    //in: int, int
    //out: int
    //////////
    public static int randomWithRange(int min, int max)
    {
       //defineix el marge de numeros k ha d efer el random
       int range = (max - min) + 1;
       // obte un numero entre els especificats
       return (int)(Math.random() * range) + min;
    }
    
    ///////
    //obte un numero random, entre dos numeros indicats
    //in: float, float (el limit entre els quals ha de escollir un numero)
    //out: float (numero escolit)
    ///////////
    public static float randomWithRange(float min, float max)
    {
       //defineix el marge de numeros k ha d efer el random
       float range = (max - min) + 1;
       // obte un numero entre els especificats
       return (float)((Math.random() * range) + min);
    }
   
    
    ///////
    //separa un string segons el caracter determinat
    //in: String (missatge a separar),
    //    String(caracter que marca la separació)
    //out: ArrayList<String> (arraylist amb el resultat de la separació)
    ///////////
    public static ArrayList<String> separator_String(String string, String cod_separator){
    
        ArrayList<String>separat = new ArrayList<String>();
        String[] string_sep = string.split(cod_separator);
        for (int i = 0; i < string_sep.length; i++) {
            separat.add(string_sep[i]);
        }
        return separat;
        
    }
    
    ///////
    // a partir 
    //in: String (missatge a separar),
    //    String(caracter que marca la separació)
    //out: ArrayList<String> (arraylist amb el resultat de la separació)
    ///////////
    public static char getLetterDni(int numero) throws NumberDNINotCorrectException{
        
        if(Integer.toString(numero).length()!=8){
            throw new NumberDNINotCorrectException();
        }
        String caracters="TRWAGMYFPDXBNJZSQVHLCKE";
        int modul= numero % 23;
        return caracters.charAt(modul);  
    }
    
     static boolean isPrimary(String valor){
      boolean primary = false; 
      if(valor.indexOf("lang")==1){
        primary = true;
      }
      return primary;
    }
}

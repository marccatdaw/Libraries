 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package einess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    
    
    ////////////////////////////////////////
    // comproba si l'objecte es de la classe correcte
    // in: obj, string
    // out: boolean
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
                String[] parts = param.split(";");         
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
    
    //////
    // S'encarrega de mostrar el objecte per la consola
    // in: 
    // out: -
    //////////
    
    public static String Show_Object(Object obj1){
        StringBuilder b = new StringBuilder();
        Stack<Class> classes = knowClass(obj1);
        b.append("///***"+obj1.getClass().getSimpleName() +"***///"); 
        b.append("\r\n");
        for (int j = classes.size(); j > 0; j--) {
            Class c = classes.pop();
            Field[] atr = c.getDeclaredFields();
            
            for (int i = 0; i < atr.length; i++) {
                b.append( Character.toUpperCase(atr[i].getName().charAt(0)) + atr[i].getName().substring(1) );
                b.append(": ");
                Field atro = atr[i];
                atro.setAccessible(true);
                try {
                     switch(atro.getType().getSimpleName().toString()){
                         case "int":
                            if(atro.getInt(obj1)>0){
                                 b.append(atro.getInt(obj1));
                             }else{
                                 b.append("??????");
                             }
                             break;
                         case "float":
                             if(atro.getFloat(obj1)>0.0){
                                 b.append(atro.getFloat(obj1));
                             }else{
                                 b.append("??????");
                             }
                             break;
                         case "boolean":
                             b.append(atro.getBoolean(obj1));
                             break;
                         case "char":
                             if(atro.getChar(obj1)!=0){
                                b.append(atro.getChar(obj1));
                             }else{
                                b.append("??????");
                             }
                             break;
                         case "ArrayList":
                             if(atro.get(obj1)!=null){
                                ArrayList<Object> al = (ArrayList<Object>) atro.get(obj1);
                                if(!al.isEmpty()){
                                    //b.append("??????A");
                                   b.append("\n");
                                   b.append(Utils.show_Array(al.toArray()));

                                }else{
                                    //b.append("??????B");
                                }
                             }else{
                                b.append("??????");
                             }
                             break;
                         default: 
                             if(atro.getType().getSimpleName().toString().equals("Date") && atro.get(obj1)!=null){
                                b.append(Utils.data_format((Date)atro.get(obj1), "dd/MM/yyyy")); 
                             } else if(atro.getType().getSimpleName().toString().equals("String") && atro.get(obj1)!=null){
                               b.append(atro.get(obj1));
                             } else if(atro.get(obj1)!=null){
                                    b.append("\n");
                                     Method miMetodo = atro.getClass().getMethod("toString");
                                     b.append((Class.forName(atro.get(obj1).getClass().getName()).cast(atro.get(obj1))));

                             } else{
                                b.append("??????");
                             }
                             break;
                     }
                    //b.append(atro.getType().getSimpleName().toString());
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) { 
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                }catch (NoSuchMethodException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) { 
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                }
                b.append("\r\n");
            }
            
            
        }
      
        
        return b.toString();
    }
    
    private static  Stack<Class> knowClass(Object obj){
        Stack<Class> classes = new Stack<Class>();
        
        Class a = obj.getClass();
        String name = a.getSimpleName();
        classes.add(a);
        
        do{
        a = a.getSuperclass();
        name = a.getSimpleName();
        if(!name.equals("Object")){
            classes.add(a);
        }
        }while(!name.equals("Object"));
        
        
        return classes;
    }
   

}

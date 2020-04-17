/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package einess;

import java.util.Scanner;

/**
 *
 * @author marccat
 */
public class Teclat {
       ////////////////////////////////////
    //informa al usuari de que ha de introduir un String
    //in: demanara que ha de introduir l'usuari i una condicio true o false
    //out: allo que introueix l'usuari
    ///////////////////////////////////
    public static String demanar_String(String pregunta, boolean cert){
        String text="";
        do{
            try{
                if(cert){
                    System.out.println(pregunta);
                }else{
                    System.out.print(pregunta);
                }
                
                text=new Scanner(System.in).nextLine(); 
            }catch(Exception e){
                System.out.println("Si us plau, introdueix un Text");
                text="";
            }
        }while(text.equals(""));
        return text;
    }
    
    ////////////////////////////////////
    //informa al usuari de que ha de introduir un Integer
    //in: demanara que ha de introduir l'usuari
    //out: allo que introueix l'usuari, si hi ha un errror sera 0
    ///////////////////////////////////
    public static int demanar_Integer(String pregunta, boolean cert){
        int num=0;
        try{
            if(cert){
                    System.out.println(pregunta);
                }else{
                    System.out.print(pregunta);
                }
            num = new Scanner(System.in).nextInt();
  
        }catch(Exception e){
            System.out.println("Error: Heu introduirt un caracter no valid, per tant"
                    + "automaticament se li asignara un 0");
            num=0;
        }
        return num;
    }
        
    ////////////////////////////////////
    //informa al usuari de que ha de introduir un Integer
    //in: demanara que ha de introduir l'usuari i una condicio true o false
    //out: allo que introueix l'usuari, si hi ha un errror sera 0
    ///////////////////////////////////
    public static double demanar_Double(String pregunta,boolean cert){
        double num=0;
        try{
            if(cert){
                    System.out.println(pregunta);
                }else{
                    System.out.print(pregunta);
                }
            num = new Scanner(System.in).nextInt();
  
        }catch(Exception e){
            System.out.println("Error: Heu introduirt un caracter no valid, per tant"
                    + "automaticament se li asignara un 0");
            num=0;
        }
        return num;
    }
    
    
    
}

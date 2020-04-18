/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package einess;

import einess.Utils;
import einess.Utils;
import einess.Utils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marccat
 */
public class ShowObject {

    //////////////////////
    // ATRIBUTS STATIC
    /////////////////////
    private static String nomAtrMap1;
    private static String nomAtrMap2;
    private static String patroDate;
    
    
    //////////////////////
    // ATRIBUTS
    /////////////////////
    private Map<String,String> field;
    private boolean onlyMap;
    private boolean includeStatic;
    private boolean includeFinal;
    private boolean parseValuePrimaryInMap;
    private String patroNumValueMap;
    
    
    /*//////////////////////
    // ATRIBUTS V2
    /////////////////////
    private Object obj;*/
    private StringBuilder b;
    private Object showObject;
    
    //////////////////////
    // CONSTRUCTORS
    /////////////////////
    public ShowObject() {
        this.field = new HashMap<String,String>();
        this.onlyMap=false;
        this.includeStatic =false;
        this.includeFinal =false;
        ShowObject.nomAtrMap1="????";
        ShowObject.nomAtrMap2="????";
        ShowObject.patroDate="dd/MM/yyyy";
        this.parseValuePrimaryInMap=false;
        this.patroNumValueMap="0.00";
        b = new StringBuilder();
    }
    public ShowObject(Map fields) {
        this.field = fields;
        this.onlyMap=false;
        this.includeStatic =false;
        this.includeFinal =false;
        ShowObject.nomAtrMap1="????";
        ShowObject.nomAtrMap2="????";
        ShowObject.patroDate="dd/MM/yyyy";
        this.parseValuePrimaryInMap=false;
        this.patroNumValueMap="0.00";
        b = new StringBuilder();
    }

    
    
    //////////////////////
    // CONFIGURACIÓ
    /////////////////////
    public void setOnlyMap(boolean onlyMap) {
        this.onlyMap = onlyMap;
    }
    public void setShowStatic(boolean includeStatic) {
        this.includeStatic = includeStatic;
    }
    public void setShowFinal(boolean includeFinal) {
        this.includeFinal = includeFinal;
    }

    public static void setNameAtrKey(String nomAtrMap1) {
        ShowObject.nomAtrMap1 = nomAtrMap1;
    }

    public static void setNameAtrValue(String nomAtrMap2) {
        ShowObject.nomAtrMap2 = nomAtrMap2;
    }

    public static void setPatroDate(String patroDate) {
        ShowObject.patroDate = patroDate;
    }

    public void setParseValuePrimaryInMap(boolean parseValuePrimaryInMap) {
        this.parseValuePrimaryInMap = parseValuePrimaryInMap;
    }

    public void setPatroNumValueMap(String patroNumValueMap) {
        this.patroNumValueMap = patroNumValueMap;
    }




    //////////////////////
    // METODES
    /////////////////////
    public String Show_Object(Object obj1){
        StringBuilder b = new StringBuilder();
        Stack<Class> classes = knowClass(obj1);
        b.append("///***"+obj1.getClass().getSimpleName() +"***///");
        b.append("\r\n");
        for (int j = classes.size(); j > 0; j--) {
            Class c = classes.pop();
            Field[] atr = c.getDeclaredFields();

            for (int i = 0; i < atr.length; i++) {
                String nameField = filter(atr[i]);
                if(nameField!=null){
                    b.append( nameField );
                    b.append(": ");
                    Field atro = atr[i];
                    atro.setAccessible(true);
                    try {
                         switch(atro.getType().getSimpleName().toString()){
                             case "int":
                                //if(atro.getInt(obj1)>0){
                                     b.append(atro.getInt(obj1));
                                 //}else{
                                 //    b.append("??????");
                                 //}
                                 break;
                             case "float":
                                 //if(atro.getFloat(obj1)>0.0){
                                     b.append(atro.getFloat(obj1));
//                                 }else{
//                                     b.append("??????");
//                                 }
                                 break;
                             case "boolean":
                                // b.append(atro.getBoolean(obj1));
                                 if(atro.getBoolean(obj1)){
                                     b.append("Sí");
                                 }else{
                                     b.append("No");
                                 }
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
                                        b.append("??????");
                                    }
                                 }else{
                                    b.append("??????");
                                 }
                                 break;
                             case "Map":
                                 if(atro.get(obj1)!=null){
                                    Iterator it = ((Map<Object,Object>)atro.get(obj1)).keySet().iterator();
                                    if(!((Map<Object,Object>)atro.get(obj1)).isEmpty()){

                                        while(it.hasNext()){
                                          b.append("\n");
                                          Object ob = it.next();
                                          //b.append(ob.getClass().getTypeName().toString());
                                          //b.append(((Map<Object,Object>)atro.get(obj1)).get(ob).getClass().getTypeName().toString());

                                          if(Utils.isPrimary(ob.getClass().getTypeName())){
                                              b.append(ShowObject.nomAtrMap1);
                                              b.append(": ");
                                              b.append(ob);
                                              b.append("\n");
                                          }else{
                                              b.append(ob);
                                          }
                                          
                                          b.append(ShowObject.nomAtrMap2);
                                              b.append(": ");
                                          if(((Map<Object,Object>)atro.get(obj1)).get(ob)!=null){
                                          if(!Utils.isPrimary(((Map<Object,Object>)atro.get(obj1)).get(ob).getClass().getTypeName()) ){
                                              
                                              //b.append(((Map<Object,Object>)atro.get(obj1)).get(ob));
                                              if(((Map<Object,Object>)atro.get(obj1)).get(ob)!=null){
                                                if(!this.parseValuePrimaryInMap ){
                                                  b.append(((Map<Object,Object>)atro.get(obj1)).get(ob));
                                                }else{
                                                    if(((Map<Object,Object>)atro.get(obj1)).get(ob).getClass().getSimpleName().equals("Double") && ((Map<Object,Object>)atro.get(obj1)).get(ob)!=null ){
                                                      b.append((new DecimalFormat("0.00")).format(((Map<Object,Object>)atro.get(obj1)).get(ob)));
                                                    }else{
                                                     b.append("??????");

                                                    }
                                                }
                                              }
                                               b.append("\n");
                                          }else{
                                              b.append(((Map<Object,Object>)atro.get(obj1)).get(ob));
                                              
                                             
                                          }
                                        }else{
                                          b.append("??????");
                                          b.append("\n");
                                          }

                                        }
                                    }else{
                                        b.append("??????");
                                    }
                                 }else{
                                    b.append("??????");
                                 }
                                 break;
                             default:
                                 if(atro.getType().getSimpleName().toString().equals("Date") && atro.get(obj1)!=null){
                                    b.append(Utils.data_format((Date)atro.get(obj1), this.patroDate));
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


        }


        return b.toString();
    }
    private Stack<Class> knowClass(Object obj){
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

    private String filter(Field field){
        String res = showFieldName(field.getName());
        boolean showStatic = showStaticField(field);
        boolean showFinal = showFinalField(field);

        if(showStatic){
            res=null;
        }

        if(showFinal){
            res=null;
        }

        return res;

    }

    private boolean showStaticField(Field field){
        boolean staticValue = false;
        if(!this.includeStatic){
            int num=field.getModifiers();
            staticValue=Modifier.isStatic(num);
        }
        return staticValue;
    }

    private boolean showFinalField(Field field){
        boolean staticFinal = false;
        if(!this.includeFinal){
            int num=field.getModifiers();
            staticFinal=Modifier.isFinal(num);
        }
        return staticFinal;
    }

    private String showFieldName(String name){
        String res = field.get(name);
        if(res!=null){
            name = Character.toUpperCase(res.charAt(0)) + res.substring(1);
        }else if(res==null && this.onlyMap ) {
            name=null;
        } else{
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        }

        return name;
    }


    ////////////////////////////
    //NOUS METODES
    /////////
    
    public String show(){
    StringBuilder b = new StringBuilder();
    showTitle();
    recorrerObject();
    
    return b.toString();
    }

    private void recorrerObject() {
        Stack<Class> classes = knowClass(this.showObject);
        for (int i = classes.size(); i > 0; i--) {
            Class classe = classes.pop();
            Field[] atr = classe.getDeclaredFields();
            for (int j = 0; j < atr.length; j++) {
                
            }
        }
    }
    
    
    private void showTitle(){
        b.append("///***"+this.showObject.getClass().getSimpleName() +"***///");
        b.append("\r\n");
    }
    
    private void showInt(int num){
    b.append(num);
    }
    private void showFloat(float numFloat){
    b.append(numFloat);
    }
    private void showDouble(int numDouble){
    b.append(numDouble);
    }
    private void showChar(int numChar){
    b.append(numChar);
    }
    private void showBoolean(int numBoolean){
    b.append(numBoolean);
    }
    private void showShort(float numShort){
    b.append(numShort);
    }
    private void showLong(int numLong){
    b.append(numLong);
    }
    private void showByte(int numByte){
    b.append(numByte);
    }
    
    
    

}

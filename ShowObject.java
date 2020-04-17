/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package einess;

import einess.Utils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marccat
 */
public class ShowObject {
    
    Map<String,String> field; 
    boolean onlyMap;

    public ShowObject() {
        this.field = new HashMap<String,String>();
        this.onlyMap=false;
    }
    
    public ShowObject(Map fields) {
        this.field = fields;
        this.onlyMap=false;
    }

    public void setOnlyMap(boolean onlyMap) {
        this.onlyMap = onlyMap;
    }
        
    public String Show_Object(Object obj1){
        StringBuilder b = new StringBuilder();
        Stack<Class> classes = knowClass(obj1);
        b.append("///***"+obj1.getClass().getSimpleName() +"***///"); 
        b.append("\r\n");
        for (int j = classes.size(); j > 0; j--) {
            Class c = classes.pop();
            Field[] atr = c.getDeclaredFields();
            
            for (int i = 0; i < atr.length; i++) {
                String nameField = showFieldName(atr[i].getName());
                if(nameField!=null){
                    b.append( nameField );
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
    
    
    
}

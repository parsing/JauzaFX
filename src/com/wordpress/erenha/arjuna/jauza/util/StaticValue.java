/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.erenha.arjuna.jauza.util;

/**
 *
 * @author Hindarwan
 */
public class StaticValue {
    //config
    public final static String defaultWebAddress = "http://localhost:8080";
    public final static String sesameServer = "http://localhost:8080/openrdf-sesame";
//    public final static String sesameRepositoryID = "onelinkDemo";
    public final static String sesameRepositoryID = "onelink-repository";
    public final static String nsResource = "http://localhost:8888/resource/";
    
    //message
    public final static String annotation_create_individual_title = 
            "Create Individual";
    public final static String annotation_create_individual = 
            "1. Give unique identifier or use generated identifier.\n"
            + "2. Choose type of individual.";
}

package com.contactmanager.utils;

import java.io.PrintWriter;
import java.util.List;

import com.contactmanager.entity.Contact;

public class CSVExporter {

    public static void export(PrintWriter writer,List<Contact> contacts){

        writer.println("ID,Name,Email,Phone,Address");

        for(Contact c:contacts){

            writer.println(
            c.getId()+","+
            c.getName()+","+
            c.getEmail()+","+
            c.getPhone()+","+
            c.getAddress());

        }
    }

}
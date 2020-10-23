import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class jsonToJava {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        ArrayList<customerDetails> a = new ArrayList<customerDetails>();
        // create array list to hold all the java objects

        JSONArray ja = new JSONArray();


        Class.forName("com.mysql.cj.jdbc.Driver");
        //Load the class dynamically during runtime , creates the instances of the class

        Connection conn = null;
        // create a connection variable

        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Business", "root", "briter");
        //pass connection details through driver manager

        Statement st = conn.createStatement();
        //Create object class statement , which will help us create queries

        ResultSet rs = st.executeQuery("select * from CustomerInfo where Location = 'Asia' and  PurchasedDate = curdate();");
        // Result holds all the result

        while (rs.next()) {

            //Iterating through each row in DB converting that in
            // to Java object that storing that in arraylist

            customerDetails cd = new customerDetails();
            cd.setCourseName(rs.getString(1));
            cd.setPurchasedDate(rs.getString(2));
            cd.setAmount(rs.getInt(3));
            cd.setLocation(rs.getString(4));
            a.add(cd);   //create json file for java object

        }

        //download Jackson core, jackson databind and Jackson annotations inorder to use object mapper class

        for (int i = 0; i < a.size(); i++){
            //Iterarting through the array and converting that to Java object

            ObjectMapper ob = new ObjectMapper();
            //ObjectMapper class and how to serialize Java objects into JSON and
            // deserialize JSON string into Java objects.

            ob.writeValue(new File("C:\\Komal\\CoreJava\\jsonJava\\src\\main\\java\\Finaljson"), a.get(i));
            //writeValue API to serialize any Java object as JSON output.
            // a.get(i) has the Java object

            Gson g = new Gson();
            // Gson class helps to convert Java object to Json  String

            String jsonstring = g.toJson(a.get(i));
            ja.add(jsonstring);
            // Storing all the json strings in a json array list

        }

        JSONObject jo = new JSONObject();
        // Creating Json object
        //Json simple dependency

        jo.put("data",ja);
        // Create Json array with data as an object, and all json strings as its values



        //apache common text dependency
        String unescapedstring = StringEscapeUtils.unescapeJava(jo.toJSONString());

        String string1 = unescapedstring.replace("\"}", "}");
        String finalstring = string1.replace("\"{", "{");

        FileWriter file = new FileWriter("C:\\Komal\\CoreJava\\jsonJava\\src\\main\\java\\Finaljson.json");
        file.write(finalstring);




    }



}


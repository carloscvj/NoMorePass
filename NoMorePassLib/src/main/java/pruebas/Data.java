/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

/**
 *
 * @author becario
 */
import java.util.Date;

public class Data {

   public Data(String aString, int aInt, Integer aInteger, Date aDate) {
      super();
      this.aString = aString;
      this.aInt = aInt;
      this.aInteger = aInteger;
      this.aDate = aDate;
   }

   private String aString = "a String";
   private int aInt = 22;
   private Integer aInteger = new Integer(11);
   private Date aDate = new Date();

   @Override
   public String toString() {
      return "Data [aString=" + aString + ", aInt=" + aInt + ", aInteger="
            + aInteger + ", aDate=" + aDate + "]";
   }

}

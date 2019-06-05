/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas.json;

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
   private Integer aInteger = 11;
   private Date aDate = new Date();

   @Override
   public String toString() {
      return "Data [aString=" + getaString() + ", aInt=" + getaInt() + ", aInteger="
            + getaInteger() + ", aDate=" + getaDate() + "]";
   }

    public String getaString() {
        return aString;
    }

    public void setaString(String aString) {
        this.aString = aString;
    }

    public int getaInt() {
        return aInt;
    }

    public void setaInt(int aInt) {
        this.aInt = aInt;
    }

    public Integer getaInteger() {
        return aInteger;
    }

    public void setaInteger(Integer aInteger) {
        this.aInteger = aInteger;
    }

    public Date getaDate() {
        return aDate;
    }

    public void setaDate(Date aDate) {
        this.aDate = aDate;
    }

}

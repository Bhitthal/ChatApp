
import java.net.*;
import java.util.*;
import java.io.*;
import java.nio.*;
 
public class IPAddress {
 public void  getInterfaces (){
      try {
         Enumeration e = NetworkInterface.getNetworkInterfaces();
 
         while(e.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) e.nextElement();
            System.out.println("Net interface: "+ni.getName());
 
            Enumeration e2 = ni.getInetAddresses();
 
            while (e2.hasMoreElements()){
               InetAddress ip = (InetAddress) e2.nextElement();
               System.out.println("IP address: "+ ip.toString());
            }
         }
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }
 
   public static void main(String[] args) {
    IPAddress ip = new IPAddress();
    ip.getInterfaces();
   }
}
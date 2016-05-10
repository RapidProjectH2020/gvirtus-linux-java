/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gvirtusfe;

import java.io.IOException;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author cferraro
 */
public class CudaDr_memory {

    public CudaDr_memory() {
    }

    String cuMemAlloc(Frontend fe, Result res, long size) throws IOException{

        Buffer b = new Buffer();
        byte[] bits = this.longToByteArray(size);
           for (int i =0; i< bits.length;i++){
               b.AddByte(bits[i] & 0xFF);
        }
        String pointer = "";
        fe.Execute("cuMemAlloc",b,res);
        pointer = getHex(res,8);
        return pointer;
    }
     
    
        private String getHex (Result res,int size) throws IOException{

        byte[] array=new byte[size];
        for (int i=0; i<size;i++){
            byte bit=res.getInput_stream().readByte();
            array[i]=bit;
        }
        String hex = DatatypeConverter.printHexBinary(array);
        System.out.println(hex);
        return hex;
    }
    
        
    void cuMemcpyHtoD(Frontend fe, Result res, String dst, float[] src, int count) throws IOException {
        
        Buffer b = new Buffer();
         byte[] bits = this.longToByteArray(count);
           for (int i =0; i< bits.length;i++){
               b.AddByte(bits[i] & 0xFF);
        }
        b.Add(dst);
           for (int i =0; i< bits.length;i++){
               b.AddByte(bits[i] & 0xFF);
        }
        b.Add(src);
        fe.Execute("cuMemcpyHtoD", b, res);
    
    }    
        
        
     public byte[] longToByteArray(long value) {
        return new byte[] {
            (byte) value,
            (byte) (value >> 8),
            (byte) (value >> 16),
            (byte) (value >> 24),
            (byte) (value >> 32),
            (byte) (value >> 40),
            (byte) (value >> 48),
            (byte) (value >> 56)

        };
    }

 
}

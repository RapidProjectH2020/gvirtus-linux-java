/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gvirtusfe;

/**
 *
 * @author cferraro
 */
public class Buffer {

    String mpBuffer;
    int mLenght;
    int mBackOffset;
    int mOffset;
    
    public Buffer() {
        mpBuffer = "";
        mLenght = 0;
        mBackOffset=0;
        mOffset=0;
    }
    
   
   
    public void AddPointerNull(){
        byte[] bites = {(byte)0, (byte)0, (byte)0, (byte)0,(byte)0, (byte)0, (byte)0,(byte)0 };
        mpBuffer+=javax.xml.bind.DatatypeConverter.printHexBinary(bites);
        mLenght+=Long.SIZE/8;
        mBackOffset=mLenght;
    }
    
     public void Add(int item){
        byte[] bites = {(byte)item, (byte)0, (byte)0, (byte)0,(byte)0, (byte)0, (byte)0,(byte)0 };
        mpBuffer+=javax.xml.bind.DatatypeConverter.printHexBinary(bites);
        mLenght+=Integer.SIZE/8;
        mBackOffset=mLenght;
    }
      public void AddInt(int item){
        byte[] bites = {(byte) item, (byte)0, (byte)0, (byte)0}; 
        mpBuffer+= javax.xml.bind.DatatypeConverter.printHexBinary(bites);
        mLenght+=Integer.SIZE/8;
        mBackOffset=mLenght;
    }
    public void AddPointer(int item){
         byte[] bites = {(byte) item, (byte)0, (byte)0, (byte)0}; 
        int size = (Integer.SIZE/8);
        this.Add(size);
        mpBuffer+=javax.xml.bind.DatatypeConverter.printHexBinary(bites);
        mLenght+=size;
        mBackOffset=mLenght;
    }
    
    public String GetString(){
        return mpBuffer;
    }
     public long Size(){
        return mpBuffer.length();
    }

    void AddStruct(CudaRt_device.cudaDeviceProp struct) {
        byte[] bites =new byte[640];
        bites[0]=(byte)0x78;
        bites[1]=(byte)0x02;
        for (int i =2; i< 640;i++){
            bites[i]=(byte)0;
            
        }
        mpBuffer+=javax.xml.bind.DatatypeConverter.printHexBinary(bites);
        mLenght+=bites.length;
        mBackOffset=mLenght;
        
        
    }
    
}

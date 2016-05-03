/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gvirtusfe;

import java.io.IOException;


/**
 *
 * @author cferraro
 */
public class CudaRt_device {

    public CudaRt_device() {
    }
    
    public int cudaGetDeviceCount(Frontend fe, Result res) throws IOException{

        Buffer b = new Buffer();
        b.AddPointer(0);
        String outputbuffer = "";
        fe.Execute("cudaGetDeviceCount",b,res);
        int sizeType = res.getInput_stream().readByte();
        for (int i =0 ; i< 7;i++) res.getInput_stream().readByte();
        for (int i =0; i<sizeType;i++){
                    if (i == 0 ) {
                        byte bb = res.getInput_stream().readByte();
                        outputbuffer+= Integer.toHexString(bb & 0xFF);
                    }
                    else res.getInput_stream().readByte();
                }
                StringBuilder out2= new StringBuilder();
                if (outputbuffer.length()>2){
                        for (int i = 0; i < outputbuffer.length()-1; i+=2) {
                            String str = outputbuffer.substring(i, i+2);
                            out2.insert(0,str);
                         }
                    outputbuffer = String.valueOf(Integer.parseInt(out2.toString(),16));
                }
        
        return Integer.valueOf(outputbuffer);
    }
    
    public int cudaDeviceCanAccessPeer(Frontend fe,Result res , int device , int peers) throws IOException{
        Buffer b = new Buffer();
        b.AddPointer(0);
        b.AddInt(device);
        b.AddInt(peers);
        String outputbuffer="";
        fe.Execute("cudaDeviceCanAccessPeer",b,res);
        int sizeType = res.getInput_stream().readByte();
        for (int i =0 ; i< 7;i++) res.getInput_stream().readByte();
        for (int i =0; i<sizeType;i++){
            if (i == 0 ) {
                byte bb = res.getInput_stream().readByte();
                outputbuffer+= Integer.toHexString(bb & 0xFF);
            }
            else res.getInput_stream().readByte();
        }
        StringBuilder out2= new StringBuilder();
        if (outputbuffer.length()>2){
                for (int i = 0; i < outputbuffer.length()-1; i+=2) {
                    String str = outputbuffer.substring(i, i+2);
                    out2.insert(0,str);
                }
            outputbuffer = String.valueOf(Integer.parseInt(out2.toString(),16));
        }
        return Integer.valueOf(outputbuffer);
    }
    
    public int cudaDriverGetVersion(Frontend fe, Result res) throws IOException{


       Buffer b = new Buffer();
       b.AddPointer(0);
       String outputbuffer = "";
       fe.Execute("cudaDriverGetVersion",b,res);
       int sizeType = res.getInput_stream().readByte();
       for (int i =0 ; i< 7;i++) res.getInput_stream().readByte();
       for (int i =0; i<sizeType;i++){
                   if (i == 0 || i == 1) {
                       byte bb = res.getInput_stream().readByte();
                       outputbuffer+= Integer.toHexString(bb & 0xFF);
                   }
                   else res.getInput_stream().readByte();
               }

               StringBuilder out2= new StringBuilder();
               if (outputbuffer.length()>2){
                       for (int i = 0; i < outputbuffer.length()-1; i+=2) {
                           String str = outputbuffer.substring(i, i+2);
                           out2.insert(0,str);
                        }
                   outputbuffer = String.valueOf(Integer.parseInt(out2.toString(),16));
               }

       return Integer.valueOf(outputbuffer);
   }

    public int cudaRuntimeGetVersion(Frontend fe, Result res) throws IOException{
        
        Buffer b = new Buffer();
        b.AddPointer(0);
        String outputbuffer = "";
        fe.Execute("cudaRuntimeGetVersion",b,res);
        int sizeType = res.getInput_stream().readByte();
        for (int i =0 ; i< 7;i++) res.getInput_stream().readByte();
         for (int i =0; i<sizeType;i++){
                    if (i == 0 || i == 1) {
                        byte bb = res.getInput_stream().readByte();
                        outputbuffer+= Integer.toHexString(bb & 0xFF);
                    }
                    else res.getInput_stream().readByte();
                }
                StringBuilder out2= new StringBuilder();
                if (outputbuffer.length()>2){
                        for (int i = 0; i < outputbuffer.length()-1; i+=2) {
                            String str = outputbuffer.substring(i, i+2);
                            out2.insert(0,str);
                         }
                    outputbuffer = String.valueOf(Integer.parseInt(out2.toString(),16));
                }
        return Integer.valueOf(outputbuffer);
    }
     
     
    public int cudaSetDevice(Frontend fe, int device, Result res) throws IOException{
      
        Buffer b = new Buffer();
        b.Add(device);
        fe.Execute("cudaSetDevice",b,res);
        return 0;
    }
    
    public String cudaGetErrorString(Frontend fe, int error, Result res) throws IOException{
        
        Buffer b = new Buffer();
        b.AddInt(error);
        String outbuffer="";
        StringBuilder output = new StringBuilder();
        fe.Execute("cudaGetErrorString",b,res);
        int sizeType = res.getInput_stream().readByte();
        //System.out.print("sizeType " + sizeType);
        
        for (int i =0 ; i< 7;i++) res.getInput_stream().readByte();
        res.getInput_stream().readByte();
        //System.out.print("sizeType " + sizeType);
        
        for (int i =0 ; i< 7;i++) res.getInput_stream().readByte();
        

        for (int i =0; i< sizeType;i++){
            byte bit = res.getInput_stream().readByte();
            outbuffer+= Integer.toHexString(bit);
            //System.out.print(outbuffer.toString());
        }
        for (int i = 0; i < outbuffer.length()-1; i+=2) {
              String str = outbuffer.substring(i, i+2);
              output.append((char)Integer.parseInt(str, 16));

        }
        return output.toString();       
       
    }
    
    public void cudaDeviceReset(Frontend fe,Result res) throws IOException{
        Buffer b = new Buffer();
        fe.Execute("cudaDeviceReset", b, res);
    }
    
    
    private int getInt (Result res) throws IOException{
        
    StringBuilder output = new StringBuilder();
           for (int i =0; i< 4;i++){
            byte bit = res.getInput_stream().readByte();
           int a = bit & 0xFF;
            if (a==0){
                output.insert(0,Integer.toHexString(a));
                output.insert(0,Integer.toHexString(a));
            }
            else{ 
            output.insert(0,Integer.toHexString(a));
            }
        }
        return Integer.parseInt(output.toString(),16);
        
    
    }
   
    private long getLong (Result res) throws IOException{
    
        StringBuilder output = new StringBuilder();
        for (int i =0; i<8 ;i++){
            byte bit = res.getInput_stream().readByte();
            int a = bit & 0xFF;
            if (a==0){
                output.insert(0,Integer.toHexString(a));
                output.insert(0,Integer.toHexString(a));
            }
            else{ 
            output.insert(0,Integer.toHexString(a));
            }
        }
    
    return Long.parseLong(output.toString(),16);
    }
    
    public  cudaDeviceProp cudaGetDeviceProperties (Frontend fe, Result res , int device) throws IOException{
        Buffer b = new Buffer();
        String outbuffer="";
        StringBuilder output = new StringBuilder();
        cudaDeviceProp struct = new cudaDeviceProp();
        
        b.AddStruct(struct);
        b.AddInt(device);
        fe.Execute("cudaGetDeviceProperties", b,res);
        int sizeType = 640;
        for (int i =0; i<8 ;i++){
             res.getInput_stream().readByte();
        } // lettura size vettore buffer
        //lettura nome device
        for (int i =0; i< 256;i++){
            byte bit = res.getInput_stream().readByte();
            
            outbuffer+= Integer.toHexString(bit);
        }
         for (int i = 0; i < outbuffer.length()-1; i+=2) {
              String str = outbuffer.substring(i, i+2);
              output.append((char)Integer.parseInt(str, 16));
        }
//        System.out.println("il nome del device è "+ output.toString());
        struct.name=output.toString();
        
        struct.totalGlobalMem=this.getLong(res);
//        System.out.println("il valore di totalGlobalMem è "+struct.totalGlobalMem);
        
        struct.sharedMemPerBlock=this.getLong(res);
//        System.out.println("il valore di sharedMemPerBlock è "+ struct.sharedMemPerBlock);
        struct.regsPerBlock=this.getInt(res);
//        System.out.println("il valore di regsPerBLock è "+struct.regsPerBlock );
        
        struct.warpSize=this.getInt(res);
        
        struct.memPitch=this.getLong(res);
        
        
        struct.maxThreadsPerBlock= this.getInt(res);
        
        struct.maxThreadsDim[0]= this.getInt(res);
        struct.maxThreadsDim[1]= this.getInt(res); 
        struct.maxThreadsDim[2]= this.getInt(res);
        
        struct.maxGridSize[0]= this.getInt(res);
        struct.maxGridSize[1]= this.getInt(res); 
        struct.maxGridSize[2]= this.getInt(res);
        
        struct.clockRate= this.getInt(res); //check
        struct.totalConstMem= this.getLong(res);
        
        struct.major= this.getInt(res);
        struct.minor= this.getInt(res);
        
        struct.textureAlignment= this.getLong(res);
        struct.texturePitchAlignment= this.getLong(res); //check

        
        struct.deviceOverlap= this.getInt(res);
        struct.multiProcessorCount= this.getInt(res);
        struct.kernelExecTimeoutEnabled= this.getInt(res);
        struct.integrated= this.getInt(res);
        struct.canMapHostMemory= this.getInt(res);
        struct.computeMode= this.getInt(res);
        struct.maxTexture1D= this.getInt(res);
        struct.maxTexture1DMipmap= this.getInt(res);
        struct.maxTexture1DLinear= this.getInt(res); //check
        
        
        struct.maxTexture2D[0]= this.getInt(res);
        struct.maxTexture2D[1]= this.getInt(res); 

        struct.maxTexture2DMipmap[0]= this.getInt(res);
        struct.maxTexture2DMipmap[1]= this.getInt(res);
        
        struct.maxTexture2DLinear[0]= this.getInt(res);
        struct.maxTexture2DLinear[1]= this.getInt(res); 
        struct.maxTexture2DLinear[2]= this.getInt(res);
        
        struct.maxTexture2DGather[0]= this.getInt(res);
        struct.maxTexture2DGather[1]= this.getInt(res); 

        struct.maxTexture3D[0]= this.getInt(res);
        struct.maxTexture3D[1]= this.getInt(res); 
        struct.maxTexture3D[2]= this.getInt(res);
        
        struct.maxTexture3DAlt[0]= this.getInt(res);
        struct.maxTexture3DAlt[1]= this.getInt(res); 
        struct.maxTexture3DAlt[2]= this.getInt(res);
        
        struct.maxTextureCubemap= this.getInt(res);

        struct.maxTexture1DLayered[0]= this.getInt(res);
        struct.maxTexture1DLayered[1]= this.getInt(res);
        
        struct.maxTexture2DLayered[0]= this.getInt(res);
        struct.maxTexture2DLayered[1]= this.getInt(res); 
        struct.maxTexture2DLayered[2]= this.getInt(res);
        
        struct.maxTextureCubemapLayered[0]= this.getInt(res);
        struct.maxTextureCubemapLayered[1]= this.getInt(res); 
        
        struct.maxSurface1D=this.getInt(res);
        
        
        struct.maxSurface2D[0]=this.getInt(res);
        struct.maxSurface2D[1]=this.getInt(res);
        
        struct.maxSurface3D[0]=this.getInt(res);
        struct.maxSurface3D[1]=this.getInt(res); 
        struct.maxSurface3D[2]=this.getInt(res);
        
        struct.maxSurface1DLayered[0]=this.getInt(res);
        struct.maxSurface1DLayered[1]=this.getInt(res); 
                
        struct.maxSurface2DLayered[0]=this.getInt(res);
        struct.maxSurface2DLayered[1]=this.getInt(res); 
        struct.maxSurface2DLayered[2]=this.getInt(res);
        
        struct.maxSurfaceCubemap=this.getInt(res);
        
        
        struct.maxSurfaceCubemapLayered[0]=this.getInt(res);
        struct.maxSurfaceCubemapLayered[1]=this.getInt(res); 
        
        struct.surfaceAlignment=this.getLong(res);
        
        struct.concurrentKernels=this.getInt(res);
        struct.ECCEnabled=this.getInt(res);
        struct.pciBusID=this.getInt(res);
        struct.pciDeviceID=this.getInt(res);
        struct.pciDomainID=this.getInt(res);
        struct.tccDriver=this.getInt(res);
        struct.asyncEngineCount=this.getInt(res);
        struct.unifiedAddressing=this.getInt(res);
        struct.memoryClockRate=this.getInt(res);
        struct.memoryBusWidth=this.getInt(res);
        struct.l2CacheSize=this.getInt(res);
        struct.maxThreadsPerMultiProcessor=this.getInt(res);
        struct.streamPrioritiesSupported=this.getInt(res);
        struct.globalL1CacheSupported=this.getInt(res);
        struct.localL1CacheSupported=this.getInt(res);

        struct.sharedMemPerMultiprocessor=this.getLong(res);

        struct.regsPerMultiprocessor=this.getInt(res);
        struct.managedMemory=this.getInt(res);
        struct.isMultiGpuBoard=this.getInt(res);
        struct.multiGpuBoardGroupID=this.getInt(res);
        
        
//        System.out.println("il valore di warpSize è "+ struct.warpSize);
//        
//        System.out.println("il valore di memPitch è "+ struct.memPitch);
//        
//        
//        System.out.println("il valore di maxThreadsPerBlock è "+  struct.maxThreadsPerBlock);
//        
//        System.out.println("il valore di maxThreadsDim[0] è "+  struct.maxThreadsDim[0]);
//        System.out.println("il valore di maxThreadsDim[1] è "+  struct.maxThreadsDim[1]); 
//        System.out.println("il valore di maxThreadsDim[2] è "+  struct.maxThreadsDim[2]);
//        
//              
//        System.out.println("il valore di maxGridSize[0] è "+  struct.maxGridSize[0]);
//        System.out.println("il valore di maxGridSize[1] è "+  struct.maxGridSize[1]); 
//        System.out.println("il valore di maxGridSize[2] è "+  struct.maxGridSize[2]);
//        
//        System.out.println("il valore di clockRate è "+  struct.clockRate); //check
//        System.out.println("il valore di totalConstMem è "+  struct.totalConstMem);
//        
//        System.out.println("il valore di major è "+  struct.major);
//        System.out.println("il valore di minor è "+  struct.minor);
//     
//                
//        System.out.println("il valore di deviceOverlap è "+  struct.deviceOverlap);
//        System.out.println("il valore di multiProcessorCount è "+  struct.multiProcessorCount);
//        System.out.println("il valore di kernelExecTimeoutEnabled è "+  struct.kernelExecTimeoutEnabled);
//        System.out.println("il valore di integrated è "+  struct.integrated);
//        System.out.println("il valore di canMapHostMemory è "+  struct.canMapHostMemory);
//        
//        System.out.println("il valore di computeMode è "+  struct.computeMode);
//        System.out.println("il valore di maxTexture1D è "+  struct.maxTexture1D);
//        System.out.println("il valore di maxTexture1DMipmap è "+  struct.maxTexture1DMipmap);
//        System.out.println("il valore di maxTexture1DLinear è "+  struct.maxTexture1DLinear); //check
//        
//        
//        System.out.println("il valore di maxTexture2D[0] è "+  struct.maxTexture2D[0]);
//        System.out.println("il valore di maxTexture2D[1] è "+  struct.maxTexture2D[1]); 
//
//        System.out.println("il valore di maxTexture2DMipmap[0] è "+  struct.maxTexture2DMipmap[0]);
//        System.out.println("il valore di maxTexture2DMipmap[1] è "+  struct.maxTexture2DMipmap[1]);
//        
//        
//              
//        System.out.println("il valore di maxTexture2DLinear[0] è "+  struct.maxTexture2DLinear[0]);
//        System.out.println("il valore di maxTexture2DLinear[1] è "+  struct.maxTexture2DLinear[1]); 
//        System.out.println("il valore di maxTexture2DLinear[2] è "+  struct. maxTexture2DLinear[2]);
//        
//        System.out.println("il valore di maxTexture2DGather[0] è "+  struct.maxTexture2DGather[0]);
//        System.out.println("il valore di maxTexture2DGather[1] è "+  struct.maxTexture2DGather[1]); 
//
//        System.out.println("il valore di maxTexture3D[0] è "+  struct.maxTexture3D[0]);
//        System.out.println("il valore di maxTexture3D[1] è "+  struct.maxTexture3D[1]); 
//        System.out.println("il valore di maxTexture3D[2] è "+  struct.maxTexture3D[2]);
//        
//        System.out.println("il valore di maxTexture3DAlt[0] è "+  struct.maxTexture3DAlt[0]);
//        System.out.println("il valore di maxTexture3DAlt[1] è "+  struct.maxTexture3DAlt[1]); 
//        System.out.println("il valore di maxTexture3DAlt[2] è "+  struct.maxTexture3DAlt[2]);
//          System.out.println("il valore di maxTextureCubemap è "+  struct.maxTextureCubemap);
//
//        System.out.println("il valore di maxTexture1DLayered[0] è "+  struct.maxTexture1DLayered[0]);
//        System.out.println("il valore di maxTexture1DLayered[1] è "+  struct.maxTexture1DLayered[1]);
//        
//        System.out.println("il valore di maxTexture2DLayered[0] è "+  struct.maxTexture2DLayered[0]);
//        System.out.println("il valore di maxTexture2DLayered[1] è "+  struct.maxTexture2DLayered[1]); 
//        System.out.println("il valore di maxTexture2DLayered[2] è "+  struct.maxTexture2DLayered[2]);
//        
//        System.out.println("il valore di maxTextureCubemapLayered[0] è "+  struct.maxTextureCubemapLayered[0]);
//        System.out.println("il valore di maxTextureCubemapLayered[1] è "+  struct.maxTextureCubemapLayered[1]); 
//        
//                
//        System.out.println("il valore di maxSurface1D è "+  struct.maxSurface1D);
//        
//        
//        System.out.println("il valore di maxSurface2D[0] è "+  struct.maxSurface2D[0] );
//        System.out.println("il valore di maxSurface2D[1] è "+  struct.maxSurface2D[1]);
//        
//        System.out.println("il valore di maxSurface3D[0] è "+  struct.maxSurface3D[0]);
//        System.out.println("il valore di maxSurface3D[1] è "+  struct.maxSurface3D[1]); 
//        System.out.println("il valore di maxSurface3D[2] è "+  struct.maxSurface3D[2]);
//        
//        System.out.println("il valore di maxSurface1DLayered[0] è "+  struct.maxSurface1DLayered[0]);
//        System.out.println("il valore di maxSurface1DLayered[1] è "+  struct.maxSurface1DLayered[1]); 
//                
//        System.out.println("il valore di maxSurface2DLayered[0] è "+  struct.maxSurface2DLayered[0]);
//        System.out.println("il valore di maxSurface2DLayered[1] è "+  struct.maxSurface2DLayered[1]); 
//        System.out.println("il valore di maxSurface2DLayered[2] è "+  struct.maxSurface2DLayered[2]);
//        
//        
//        System.out.println("il valore di maxSurfaceCubemap è "+  struct.maxSurfaceCubemap);
//        
//        
//        System.out.println("il valore di maxSurfaceCubemapLayered[0] è "+  struct.maxSurfaceCubemapLayered[0]);
//        System.out.println("il valore di maxSurfaceCubemapLayered[1] è "+  struct.maxSurfaceCubemapLayered[1]); 
//        
//        System.out.println("il valore di surfaceAlignment è "+  struct.surfaceAlignment);
//        
//        	System.out.println("il valore di concurrentKernels è "+  struct.concurrentKernels);
//        System.out.println("il valore di ECCEnabled è "+  struct.ECCEnabled);
//        System.out.println("il valore di pciBusID è "+  struct.pciBusID);
//        System.out.println("il valore di pciDeviceID è "+  struct.pciDeviceID);
//        System.out.println("il valore di pciDomainID è "+  struct.pciDomainID);
//        System.out.println("il valore di tccDriver è "+  struct.tccDriver);
//        System.out.println("il valore di asyncEngineCount è "+  struct.asyncEngineCount);
//        
//              System.out.println("il valore di unifiedAddressing è "+  struct.unifiedAddressing);
//        System.out.println("il valore di memoryClockRate è "+  struct.memoryClockRate);
//        
//        
//        System.out.println("il valore di memoryBusWidth è "+  struct.memoryBusWidth);
//        System.out.println("il valore di l2CacheSize è "+  struct.l2CacheSize);
//        System.out.println("il valore di maxThreadsPerMultiProcessor è "+  struct.maxThreadsPerMultiProcessor);
//        System.out.println("il valore di streamPrioritiesSupported è "+  struct.streamPrioritiesSupported);
//        System.out.println("il valore di globalL1CacheSupported è "+  struct.globalL1CacheSupported);
//        System.out.println("il valore di localL1CacheSupported è "+  struct.localL1CacheSupported);
//
//        System.out.println("il valore di sharedMemPerMultiprocessor è "+  struct.sharedMemPerMultiprocessor);
//
//        System.out.println("il valore di regsPerMultiprocessor è "+  struct.regsPerMultiprocessor);
//        System.out.println("il valore di managedMemory è "+  struct.managedMemory);
//        System.out.println("il valore di isMultiGpuBoard è "+  struct.isMultiGpuBoard);
//        System.out.println("il valore di multiGpuBoardGroupID è "+  struct.multiGpuBoardGroupID);
//        
        return struct;
    }
    
    public class cudaDeviceProp{
        
        String name;
        long totalGlobalMem=0;             /**< Global memory available on device in bytes */
        long sharedMemPerBlock=0;          /**< Shared memory available per block in bytes */
        int    regsPerBlock=0;               /**< 32-bit registers available per block */
        int    warpSize=0;                   /**< Warp size in threads */
        long memPitch=0;                   /**< Maximum pitch in bytes allowed by memory copies */
        int    maxThreadsPerBlock=0;         /**< Maximum number of threads per block */
        int[]    maxThreadsDim= new int[3];           /**< Maximum size of each dimension of a block */
        int[]    maxGridSize= new int[3];             /**< Maximum size of each dimension of a grid */
        int    clockRate=0;                  /**< Clock frequency in kilohertz */
        long totalConstMem=0;              /**< Constant memory available on device in bytes */
        int    major=0;                      /**< Major compute capability */
        int    minor=0;                      /**< Minor compute capability */
        long textureAlignment=0;           /**< Alignment requirement for textures */
        long texturePitchAlignment=0;      /**< Pitch alignment requirement for texture references bound to pitched memory */
        int    deviceOverlap=0;              /**< Device can concurrently copy memory and execute a kernel. Deprecated. Use instead asyncEngineCount. */
        int    multiProcessorCount=0;        /**< Number of multiprocessors on device */
        int    kernelExecTimeoutEnabled=0;   /**< Specified whether there is a run time limit on kernels */
        int    integrated=0;                 /**< Device is integrated as opposed to discrete */
        int    canMapHostMemory=0;           /**< Device can map host memory with cudaHostAlloc/cudaHostGetDevicePointer */
        int    computeMode=0;                /**< Compute mode (See ::cudaComputeMode) */
        int    maxTexture1D=0;               /**< Maximum 1D texture size */
        int    maxTexture1DMipmap=0;         /**< Maximum 1D mipmapped texture size */
        int    maxTexture1DLinear=0;         /**< Maximum size for 1D textures bound to linear memory */
        int[]    maxTexture2D= new int[2];            /**< Maximum 2D texture dimensions */
        int[]    maxTexture2DMipmap= new int[2];      /**< Maximum 2D mipmapped texture dimensions */
        int[]    maxTexture2DLinear= new int[3];      /**< Maximum dimensions (width, height, pitch) for 2D textures bound to pitched memory */
        int[]    maxTexture2DGather= new int[2];      /**< Maximum 2D texture dimensions if texture gather operations have to be performed */
        int[]    maxTexture3D= new int[3];            /**< Maximum 3D texture dimensions */
        int[]    maxTexture3DAlt= new int[3];         /**< Maximum alternate 3D texture dimensions */
        int    maxTextureCubemap=0;          /**< Maximum Cubemap texture dimensions */
        int[]    maxTexture1DLayered= new int[2];     /**< Maximum 1D layered texture dimensions */
        int[]    maxTexture2DLayered= new int[3];     /**< Maximum 2D layered texture dimensions */
        int[]   maxTextureCubemapLayered= new int[2];/**< Maximum Cubemap layered texture dimensions */
        int    maxSurface1D=0;               /**< Maximum 1D surface size */
        int[]    maxSurface2D= new int[2];            /**< Maximum 2D surface dimensions */
        int[]    maxSurface3D= new int[3];            /**< Maximum 3D surface dimensions */
        int[]    maxSurface1DLayered= new int[2];     /**< Maximum 1D layered surface dimensions */
        int[]    maxSurface2DLayered= new int[3];     /**< Maximum 2D layered surface dimensions */
        int    maxSurfaceCubemap=0;          /**< Maximum Cubemap surface dimensions */
        int[]    maxSurfaceCubemapLayered= new int[2];/**< Maximum Cubemap layered surface dimensions */
        long surfaceAlignment=0;           /**< Alignment requirements for surfaces */
        int    concurrentKernels=0;          /**< Device can possibly execute multiple kernels concurrently */
        int    ECCEnabled=0;                 /**< Device has ECC support enabled */
        int    pciBusID=0;                   /**< PCI bus ID of the device */
        int    pciDeviceID=0;                /**< PCI device ID of the device */
        int    pciDomainID=0;                /**< PCI domain ID of the device */
        int    tccDriver=0;                  /**< 1 if device is a Tesla device using TCC driver, 0 otherwise */
        int    asyncEngineCount=0;           /**< Number of asynchronous engines */
        int    unifiedAddressing=0;          /**< Device shares a unified address space with the host */
        int    memoryClockRate=0;            /**< Peak memory clock frequency in kilohertz */
        int    memoryBusWidth=0;             /**< Global memory bus width in bits */
        int    l2CacheSize=0;                /**< Size of L2 cache in bytes */
        int    maxThreadsPerMultiProcessor=0;/**< Maximum resident threads per multiprocessor */
        int    streamPrioritiesSupported=0;  /**< Device supports stream priorities */
        int    globalL1CacheSupported=0;     /**< Device supports caching globals in L1 */
        int    localL1CacheSupported=0;      /**< Device supports caching locals in L1 */
        long sharedMemPerMultiprocessor=0; /**< Shared memory available per multiprocessor in bytes */
        int    regsPerMultiprocessor=0;      /**< 32-bit registers available per multiprocessor */
        int    managedMemory=0;              /**< Device supports allocating managed memory on this system */
        int    isMultiGpuBoard=0;            /**< Device is on a multi-GPU board */
        int    multiGpuBoardGroupID=0;       /**< Unique identifier for a group of devices on the same multi-GPU board */
       
    
    public cudaDeviceProp() {
        
        }

    
        
    }

}


package gvirtusfe;

import gvirtusfe.CudaRt_device.cudaDeviceProp;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * 
 * @author Carmine Ferraro, Valentina Pelliccia
 *  University of Naples Parthenope
 * @version 1.0
 * 
 */

public class GVirtuSFE {
    
    static String ip="127.0.0.1"; //change localhost with remote host if necessary
    static int port = 9991;
    
    public static void main(String[] args) throws IOException {
       
        deviceQuery();
//        matrixMul();
    }

    public static float[] constantInit(float[] data, int size, float val){
        for (int i = 0; i < size; ++i)
        {
            data[i] = val;
        }
        return data;
    }
    
    
    
    static String readFile(String path, Charset encoding) throws IOException {
      byte[] encoded = Files.readAllBytes(Paths.get(path));
      return new String(encoded, encoding);
    }
    
    public static void matrixMul() throws IOException{

        System.out.println( "matrixMulDrv (Driver API)");
        Frontend FE = new Frontend(ip,port);
        Result res = new Result();
        int CUdevice=0;
        CudaDr_device dr = new CudaDr_device();
        CudaDr_initialization dr_in= new CudaDr_initialization();
        dr_in.cuInit(FE, res, 0);
        int device = dr.cuDeviceGet(FE, res, CUdevice);
        int numberofdevice = dr.cuDeviceGetCount(FE, res);
        int[] computeCapability = dr.cuDeviceComputeCapability(FE, res, device);
        System.out.println("computeCapability is "+computeCapability[0]+"."+computeCapability[1]);
        int GPUoverlap= dr.cuDeviceGetAttribute(FE, res, 15, device);
        System.out.println("GPUOverlap is "+GPUoverlap);
        String name = dr.cuDeviceGetName(FE, res, 255,device );
        System.out.println("Device name is "+name);
        long totalMem = dr.cuDeviceTotalMem(FE, res, device);
        System.out.println("Total mem is "+totalMem);
        CudaDr_context ctx = new CudaDr_context();
        String context = ctx.cuCtxCreate(FE, res, 0, 0);
        System.out.println("Context pointer is "+context);
        
        String p = "/src/gvirtusfe/matrixMul_kernel64.ptx";
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String ptxSource = readFile(s+p,Charset.defaultCharset());
        int jitNumOptions = 3;
        int[] jitOptions = new int[jitNumOptions];
       
        // set up size of compilation log buffer
        jitOptions[0] = 4;//CU_JIT_INFO_LOG_BUFFER_SIZE_BYTES;
        long jitLogBufferSize = 1024;
        long jitOptVals0 = jitLogBufferSize;

        // set up pointer to the compilation log buffer
        jitOptions[1] = 3;//CU_JIT_INFO_LOG_BUFFER;
        
        char[] jitLogBuffer = new char[(int)jitLogBufferSize];
        char [] jitOptVals1 = jitLogBuffer;

        // set up pointer to set the Maximum # of registers for a particular kernel
        jitOptions[2] = 0;// CU_JIT_MAX_REGISTERS;
        long jitRegCount = 32;
        long jitOptVals2 = jitRegCount;

        CudaDr_module dr_mod = new CudaDr_module();

        String cmodule = dr_mod.cuModuleLoadDataEx(FE, res, ptxSource, jitNumOptions, jitOptions, jitOptVals0, jitOptVals1, jitOptVals2);
        String cfunction = dr_mod.cuModuleGetFunction(FE, res, cmodule, "matrixMul_bs16_64bit");
        System.out.println("pointer cfunction " + cfunction);

        // allocate host memory for matrices A and B
        int block_size = 16;
        int WA = (4 * block_size); // Matrix A width
        int HA = (6 * block_size); // Matrix A height
        int WB = (4 * block_size); // Matrix B width
        int HB = WA;  // Matrix B height
        int WC = WB;  // Matrix C width 
        int HC = HA;  // Matrix C height
        int size_A = WA * HA;
        
        
        
        int mem_size_A = Float.SIZE/8 * size_A;
        float[] h_A = new float[mem_size_A];
        
        int size_B = WB * HB;
        int mem_size_B = Float.SIZE/8 * size_B;
        float[] h_B = new float[mem_size_B];
        float valB = 0.01f;
        h_A = constantInit(h_A,size_A,1.0f);  
        h_B = constantInit(h_B,size_B,valB);
        
//         // allocate device memory
//        CUdeviceptr d_A;
//        checkCudaErrors(cuMemAlloc(&d_A, mem_size_A));
//    CUdeviceptr d_B;
//    checkCudaErrors(cuMemAlloc(&d_B, mem_size_B));
//        
        
        ctx.cuCtxDestroy(FE, res, context);
        
        
        
    }
    
    public static void deviceQuery() throws IOException{
        
        Frontend FE = new Frontend(ip,port);
        Result res = new Result();
        CudaRt_device dv = new CudaRt_device();
        System.out.println("Starting...\nCUDA Device Query (Runtime API) version (CUDART static linking)\n\n");
        int deviceCount = dv.cudaGetDeviceCount(FE,res);
        if (res.getExit_code() != 0){
            System.out.println("cudaGetDeviceCount returned "+ res.getExit_code()+" -> "+ dv.cudaGetErrorString(FE,res.getExit_code(),res));
            System.out.println("Result = FAIL\n");
            return;
        }
        if (deviceCount == 0){
            System.out.println("There are no available device(s) that support CUDA\n");
        } else{
             System.out.println("Detected "+ deviceCount+" CUDA Capable device(s)");
        }
        for (int i=0;i<deviceCount;i++){
            dv.cudaSetDevice(FE, i,res);
            cudaDeviceProp  deviceProp;
            deviceProp = dv.cudaGetDeviceProperties(FE,res,i);
            System.out.println("\nDevice "+i+": "+deviceProp.name);
            int driverVersion = dv.cudaDriverGetVersion(FE,res);
            int runtimeVersion = dv.cudaRuntimeGetVersion(FE, res);
            System.out.println("CUDA Driver Version/Runtime Version:         " + driverVersion/1000+"."+(driverVersion%100)/10+ " / "+  runtimeVersion/1000+"."+  (runtimeVersion%100)/10);
            System.out.println("CUDA Capability Major/Minor version number:  "+deviceProp.major+"."+deviceProp.minor);
            System.out.println("Total amount of global memory:                 "+deviceProp.totalGlobalMem/1048576.0f+" MBytes ("+deviceProp.totalGlobalMem+" bytes)\n");
            System.out.println("GPU Clock rate:                              "+deviceProp.clockRate* 1e-3f+" Mhz ("+deviceProp.clockRate * 1e-6f+")");
            System.out.println("Memory Clock rate:                           "+deviceProp.memoryClockRate * 1e-3f+" Mhz");
            System.out.println("Memory Bus Width:                            "+deviceProp.memoryBusWidth + "-bit");
            if (deviceProp.l2CacheSize == 1){
            System.out.println("L2 Cache Size:                               "+deviceProp.l2CacheSize+" bytes");
            }
            System.out.println("Maximum Texture Dimension Size (x,y,z)         1D=("+deviceProp.maxTexture1D+"), 2D=("+deviceProp.maxTexture2D[0]+","+  deviceProp.maxTexture2D[1]+"), 3D=("+deviceProp.maxTexture3D[0]+", "+deviceProp.maxTexture3D[1]+", "+deviceProp.maxTexture3D[2]+")");
            System.out.println("Maximum Layered 1D Texture Size, (num) layers  1D=("+deviceProp.maxTexture1DLayered[0]+"), "+deviceProp.maxTexture1DLayered[1]+" layers");
            System.out.println("Maximum Layered 2D Texture Size, (num) layers  2D=("+deviceProp.maxTexture2DLayered[0]+", "+deviceProp.maxTexture2DLayered[1]+"), "+deviceProp.maxTexture2DLayered[2]+" layers");
            System.out.println("Total amount of constant memory:               "+deviceProp.totalConstMem+" bytes");
            System.out.println("Total amount of shared memory per block:       "+deviceProp.sharedMemPerBlock+" bytes");
            System.out.println("Total number of registers available per block: "+deviceProp.regsPerBlock);
            System.out.println("Warp size:                                     "+deviceProp.warpSize);
            System.out.println("Maximum number of threads per multiprocessor:  "+deviceProp.maxThreadsPerMultiProcessor);
            System.out.println("Maximum number of threads per block:           "+deviceProp.maxThreadsPerBlock);
            System.out.println("Max dimension size of a thread block (x,y,z): ("+ deviceProp.maxThreadsDim[0]+", "+ deviceProp.maxThreadsDim[1]+", "+ deviceProp.maxThreadsDim[2]+")");
            System.out.println("Max dimension size of a grid size    (x,y,z): ("+ deviceProp.maxGridSize[0]+", "+ deviceProp.maxGridSize[1]+","+ deviceProp.maxGridSize[2]+")");
            System.out.println("Maximum memory pitch:                          "+deviceProp.memPitch+" bytes");
            System.out.println("Texture alignment:                             "+deviceProp.textureAlignment+" bytes");
            if (deviceProp.deviceOverlap == 0) System.out.println("Concurrent copy and kernel execution:          No with "+deviceProp.asyncEngineCount+" copy engine(s)" );
            else System.out.println("Concurrent copy and kernel execution:          Yes with "+deviceProp.asyncEngineCount+" copy engine(s)" );
            if ( deviceProp.kernelExecTimeoutEnabled == 0) System.out.println("Run time limit on kernels:                     No");
            else  System.out.println("Run time limit on kernels:                     Yes");      
            int x = dv.cudaDeviceCanAccessPeer(FE, res, i, 1);
            System.out.println("Test device "+i+ " peer is "+x);
            dv.cudaDeviceReset(FE, res);
            System.out.println("Cuda reset successfull");
       }
    }
    
    public static void runtimeMemoryMalloc() throws IOException{
        
        Frontend FE = new Frontend(ip,port);
        Result res = new Result();
        CudaRt_memory mem = new CudaRt_memory();
        String pointerA = mem.cudaMalloc(FE, res, 25);
        float[] h_A = new float[25];
        h_A = constantInit(h_A,25,1.0f);
        mem.cudaMemcpy(FE, res, pointerA, h_A , h_A.length, 1);
        
    }
}

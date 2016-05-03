/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gvirtusfe;

import gvirtusfe.CudaRt_device.cudaDeviceProp;
import java.io.IOException;

/**
 *
 * @author cferraro
 */
public class GVirtuSFE {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Frontend FE = new Frontend("127.0.0.1");
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
            
            
            
            
            int x = dv.cudaDeviceCanAccessPeer(FE, res, i, 0);
            System.out.println("Test device "+i+ " peer is "+x);
            dv.cudaDeviceReset(FE, res);
            System.out.println("Cuda reset successfull");
       }
       
    }

    
}

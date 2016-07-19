import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MainApplication {

	public static float[] constantInit(float[] data, int size, float val) {
		for (int i = 0; i < size; ++i) {
			data[i] = val;
		}
		return data;
	}

	public static float[][] initMat(int righe, int colonne, float val) {
		float[][] data = new float[righe][colonne];
		for (int i = 0; i < righe; i++)
			for (int j = 0; j < colonne; j++)
				data[i][j] = val;

		return data;
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	
	public static void deviceQuery() throws IOException {

		CudaRtFrontend runtime = new CudaRtFrontend("193.205.230.23",9991);
		System.out.println("Starting...\nCUDA Device Query (Runtime API) version (CUDART static linking)\n\n");
		int deviceCount = runtime.cudaGetDeviceCount();
		if (Util.ExitCode.getExit_code() != 0) {

			
			System.out.println("cudaGetDeviceCount returned " + Util.ExitCode.getExit_code() + " -> "
					+ runtime.cudaGetErrorString(Util.ExitCode.getExit_code()));
			System.out.println("Result = FAIL\n");
			return;
		}
		if (deviceCount == 0) {
			System.out.println("There are no available device(s) that support CUDA\n");
			
		} else {
			System.out.println("Detected " + deviceCount + " CUDA Capable device(s)");
		}
		for (int i = 0; i < deviceCount; i++) {
			runtime.cudaSetDevice(i);
			CudaDeviceProp deviceProp = new CudaDeviceProp();
			deviceProp = runtime.cudaGetDeviceProperties( i);
			System.out.println("\nDevice " + i + ": " + deviceProp.getName());
			int driverVersion = runtime.cudaDriverGetVersion();
			int runtimeVersion = runtime.cudaRuntimeGetVersion();
			System.out.println("CUDA Driver Version/Runtime Version:         " + driverVersion / 1000 + "."
					+ (driverVersion % 100) / 10 + " / " + runtimeVersion / 1000 + "." + (runtimeVersion % 100) / 10);
			System.out.println("CUDA Capability Major/Minor version number:  " + deviceProp.getMajor() + "."
					+ deviceProp.getMinor());
			System.out.println(
					"Total amount of global memory:                 " + deviceProp.getTotalGlobalMem() / 1048576.0f
							+ " MBytes (" + deviceProp.getTotalGlobalMem() + " bytes)\n");
			System.out.println("GPU Clock rate:                              " + deviceProp.getClockRate() * 1e-3f
					+ " Mhz (" + deviceProp.getClockRate() * 1e-6f + ")");
			System.out.println(
					"Memory Clock rate:                           " + deviceProp.getMemoryClockRate() * 1e-3f + " Mhz");
			System.out
					.println("Memory Bus Width:                            " + deviceProp.getMemoryBusWidth() + "-bit");
			if (deviceProp.getL2CacheSize() == 1) {
				System.out.println(
						"L2 Cache Size:                               " + deviceProp.getL2CacheSize() + " bytes");
			}
			System.out.println("Maximum Texture Dimension Size (x,y,z)         1D=(" + deviceProp.getMaxTexture1D()
					+ "), 2D=(" + deviceProp.getMaxTexture2D()[0] + "," + deviceProp.getMaxTexture2D()[1] + "), 3D=("
					+ deviceProp.getMaxTexture3D()[0] + ", " + deviceProp.getMaxTexture3D()[1] + ", "
					+ deviceProp.getMaxTexture3D()[2] + ")");
			System.out.println(
					"Maximum Layered 1D Texture Size, (num) layers  1D=(" + deviceProp.getMaxTexture1DLayered()[0]
							+ "), " + deviceProp.getMaxTexture1DLayered()[1] + " layers");
			System.out.println("Maximum Layered 2D Texture Size, (num) layers  2D=("
					+ deviceProp.getMaxTexture2DLayered()[0] + ", " + deviceProp.getMaxTexture2DLayered()[1] + "), "
					+ deviceProp.getMaxTexture2DLayered()[2] + " layers");
			System.out.println(
					"Total amount of constant memory:               " + deviceProp.getTotalConstMem() + " bytes");
			System.out.println(
					"Total amount of shared memory per block:       " + deviceProp.getSharedMemPerBlock() + " bytes");
			System.out.println("Total number of registers available per block: " + deviceProp.getRegsPerBlock());
			System.out.println("Warp size:                                     " + deviceProp.getWarpSize());
			System.out.println(
					"Maximum number of threads per multiprocessor:  " + deviceProp.getMaxThreadsPerMultiProcessor());
			System.out.println("Maximum number of threads per block:           " + deviceProp.getMaxThreadsPerBlock());
			System.out.println("Max dimension size of a thread block (x,y,z): (" + deviceProp.getMaxThreadsDim()[0]
					+ ", " + deviceProp.getMaxThreadsDim()[1] + ", " + deviceProp.getMaxThreadsDim()[2] + ")");
			System.out.println("Max dimension size of a grid size    (x,y,z): (" + deviceProp.getMaxGridSize()[0] + ", "
					+ deviceProp.getMaxGridSize()[1] + "," + deviceProp.getMaxGridSize()[2] + ")");
			System.out.println("Maximum memory pitch:                          " + deviceProp.getMemPitch() + " bytes");
			System.out.println(
					"Texture alignment:                             " + deviceProp.getTextureAlignment() + " bytes");
			if (deviceProp.getDeviceOverlap() == 0)
				System.out.println("Concurrent copy and kernel execution:          No with "
						+ deviceProp.getAsyncEngineCount() + " copy engine(s)");
			else
				System.out.println("Concurrent copy and kernel execution:          Yes with "
						+ deviceProp.getAsyncEngineCount() + " copy engine(s)");
			if (deviceProp.getKernelExecTimeoutEnabled() == 0)
				System.out.println("Run time limit on kernels:                     No");
			else
				System.out.println("Run time limit on kernels:                     Yes");
			int x = runtime.cudaDeviceCanAccessPeer(i, 1);
			System.out.println("Test device " + i + " peer is " + x);
			runtime.cudaDeviceReset();
			System.out.println("Cuda reset successfull");
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		
		//deviceQuery();
		matrixMul(4,6,4); // argument passed are Width of A, Height of A, Width of B
	
		
	}
	public static void matrixMul(int widthA,int heightA,int widthB) throws IOException {

	CudaDrFrontend driver = new CudaDrFrontend("193.205.230.23",9991);
	
	long time1, time2;
	System.out.println("matrixMulDrv (Driver API)");

	driver.cuInit(0);
	time1 = System.currentTimeMillis();
	String context = driver.cuCtxCreate( 0, 0);
	driver.cuDeviceGetName(255, 0);
	driver.cuDeviceGet(0);
	String p = "/src/matrixMul_kernel64.ptx";
	Path currentRelativePath = Paths.get("");
	String s = currentRelativePath.toAbsolutePath().toString();
	String ptxSource = readFile(s + p, Charset.defaultCharset());
	int jitNumOptions = 3;
	int[] jitOptions = new int[jitNumOptions];

	// set up size of compilation log buffer
	jitOptions[0] = 4;// CU_JIT_INFO_LOG_BUFFER_SIZE_BYTES;
	long jitLogBufferSize = 1024;
	long jitOptVals0 = jitLogBufferSize;

	// set up pointer to the compilation log buffer
	jitOptions[1] = 3;// CU_JIT_INFO_LOG_BUFFER;

	char[] jitLogBuffer = new char[(int) jitLogBufferSize];
	char[] jitOptVals1 = jitLogBuffer;

	// set up pointer to set the Maximum # of registers for a particular
	// kernel
	jitOptions[2] = 0;// CU_JIT_MAX_REGISTERS;
	long jitRegCount = 32;
	long jitOptVals2 = jitRegCount;



	String cmodule = driver.cuModuleLoadDataEx(ptxSource, jitNumOptions, jitOptions, jitOptVals0,
			jitOptVals1, jitOptVals2);

	String cfunction = driver.cuModuleGetFunction(cmodule, "matrixMul_bs32_32bit");

	// allocate host memory for matrices A and B
	int block_size = 32; // larger block size is for Fermi and above
	int WA = (widthA * block_size); // Matrix A width
	int HA = (heightA * block_size); // Matrix A height
	int WB = (widthB * block_size); // Matrix B width
	int HB = WA; // Matrix B height
	int WC = WB; // Matrix C width
	int HC = HA; // Matrix C height
	System.out.println("WA:" + WA + " HA:" + HA + " WB:" + WB + " HB:" + HB);
	int size_A = WA * HA;
	int mem_size_A = Float.SIZE / 8 * size_A;
	float[] h_A = new float[size_A];
	int size_B = WB * HB;
	int mem_size_B = Float.SIZE / 8 * size_B;
	float[] h_B = new float[size_B];
	float valB = 0.01f;
//System.out.prinf("%.2f", valB);

	h_A = constantInit(h_A, size_A, 1.0f);
	h_B = constantInit(h_B, size_B, valB);
	// allocate device memory
	String d_A;
	d_A = driver.cuMemAlloc( mem_size_A);
	String d_B;
	d_B = driver.cuMemAlloc( mem_size_B);
	driver.cuMemcpyHtoD(d_A, h_A, mem_size_A);
	driver.cuMemcpyHtoD(d_B, h_B, mem_size_B);
	// allocate device memory for result
	long size_C = WC * HC;
	float[] h_C = new float[WC * HC];

	long mem_size_C = Float.SIZE / 8 * size_C;
	String d_C;
	

	d_C = driver.cuMemAlloc(mem_size_C);
	Util.Dim3 grid = new Util.Dim3(WC / block_size, HC / block_size, 1);

	int offset = 0;
	// setup execution parameters


	driver.cuParamSetv( cfunction, offset, d_C, Util.Sizeof.LONG);

	offset += Util.Sizeof.LONG;
	driver.cuParamSetv( cfunction, offset, d_A, Util.Sizeof.LONG);
	offset += Util.Sizeof.LONG;
	driver.cuParamSetv( cfunction, offset, d_B, Util.Sizeof.LONG);
	offset += Util.Sizeof.LONG;

	int Matrix_Width_A = WA;
	int Matrix_Width_B = WB;
	int Sizeof_Matrix_Width_A = Util.Sizeof.INT;
	int Sizeof_Matrix_Width_B = Util.Sizeof.INT;


	driver.cuParamSeti( cfunction, offset, Matrix_Width_A);


	offset += Sizeof_Matrix_Width_A;


	driver.cuParamSeti( cfunction, offset, Matrix_Width_B);
	offset += Sizeof_Matrix_Width_B;


	driver.cuParamSetSize( cfunction, offset);


	driver.cuFuncSetBlockShape( cfunction, block_size, block_size, grid.z);

	driver.cuFuncSetSharedSize( cfunction, 2 * block_size * block_size * (Float.SIZE / 8));

	driver.cuLaunchGrid( cfunction, grid.x, grid.y);

	h_C =  driver.cuMemcpyDtoH( d_C, mem_size_C);


	time2 = System.currentTimeMillis();
	System.out.println("Execution time WITH GPU in ms : " + (time2 - time1));
	boolean correct = true;
	System.out.println("Checking computed result for correctness...");
	System.out.println("check! Matrix[" + 0 + "]=" + h_C[0] + ", ref=" + WA * valB);

	for (int i = 0; i < WC * HC; i++) {
		if (Math.abs(h_C[i] - (WA * valB)) > 1e-2) {
			// System.out.println(i + "Error! Matrix["+i+"]="+h_C[i]+",
			// ref="+WA*valB+" error term is > 1e-4\n");
			correct = false;
		}
	}

	System.out.println(correct ? "Result = PASS" : "Result = FAIL");

	 driver.cuMemFree( d_A);
	 driver.cuMemFree( d_B);
	 driver.cuMemFree( d_C);
	driver.cuCtxDestroy( context);

	time1 = System.currentTimeMillis();
	float[][] a = generaMatrice(HA, WA, 1.0f);
	float[][] b = generaMatrice(WB, HB, valB);

	float[][] matC = prodotto(a, b);
	time2 = System.currentTimeMillis();
	System.out.println("Execution time (CPU) in ms : " + (time2 - time1));
	System.out.println("check! Matrix[" + 0 + "]=" + matC[0][0] + "%.8f, ref=" + WA * valB);

	}
	
	
	public static float[][] generaMatrice(int dim1, int dim2, float valB) {
		float[][] matrice = new float[dim1][dim2];
		for (int i = 0; i < matrice.length; i++)
			for (int j = 0; j < matrice[i].length; j++)
				matrice[i][j] = valB;
		return matrice;
	}

	public static float[][] prodotto(float[][] a, float[][] b) {
		if (a[0].length != b.length) {
			System.out.println("a[0].length" + a[0].length);
			System.out.println("b.length" + b.length);
			System.out.println("NON SI PUO' FARE!!!");
		}
		int n = a.length;
		int m = b.length;
		int l = b[0].length;
		float[][] prodotto = new float[n][l];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < l; j++)
				for (int x = 0; x < m; x++)
					prodotto[i][j] += a[i][x] * b[x][j];
		return prodotto;
	}
}

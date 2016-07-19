import java.util.HashMap;
import java.util.Map;

public final class Util {
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	private final static Map<Character, Integer> hexToDec = new HashMap<Character, Integer>();

	 static {
		    hexToDec.put('A', 10);
		    hexToDec.put('B', 11);
		    hexToDec.put('C', 12);
		    hexToDec.put('D', 13);
		    hexToDec.put('E', 14);
		    hexToDec.put('F', 15);
		  }

	 public static String bytesToHex(byte[] bytes) {

		    char[] hexChars = new char[bytes.length * 2];
		    for (int j = 0; j < bytes.length; j++) {
		      int v = bytes[j] & 0xFF;
		      hexChars[j * 2] = hexArray[v >>> 4];
		      hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		    }
		    return new String(hexChars).trim();
		  }
		
		 public static byte[] hexToBytes(String hexString) {
			    byte[] bytes = new byte[hexString.length() / 2];

			    for (int i = 0; i < hexString.length(); i += 2) {
			      char c1 = hexString.charAt(i);
			      char c2 = hexString.charAt(i + 1);

			      int n1 = Character.getNumericValue(c1);
			      if (n1 < 0) {
			        n1 = hexToDec.get(c1);
			      }

			      int n2 = Character.getNumericValue(c2);
			      if (n2 < 0) {
			        n2 = hexToDec.get(c2);
			      }

			      bytes[i / 2] = (byte) (n1 * 16 + n2);
			    }

			    return bytes;
			  }
	 
	public static byte[] longToByteArray(long value) {
	    return new byte[] {
	        (byte) value, (byte) (value >> 8), (byte) (value >> 16), (byte) (value >> 24),
	        (byte) (value >> 32), (byte) (value >> 40), (byte) (value >> 48), (byte) (value >> 56)};
	}
	    
	public static byte[] intToByteArray(int value) {
        return new byte[] {
            (byte) value, (byte) (value >> 8), (byte) (value >> 16), (byte) (value >> 24)};
    }
	
	public static class Dim3 {
	    public int x;
	    public int y;
	    public int z;
	    
	    public Dim3(int x, int y, int z) {
	        this.x = x;
	        this.y = y;
	        this.z = z;
	    }
	}
	
	public static class Sizeof {
	    public static final int BYTE = Byte.SIZE / 8;
	    public static final int CHAR = Character.SIZE / 8;
	    public static final int SHORT = Short.SIZE / 8;
	    public static final int INT = Integer.SIZE / 8;
	    public static final int FLOAT = Float.SIZE / 8;
	    public static final int LONG = Long.SIZE / 8;
	    public static final int DOUBLE = Double.SIZE / 8;
	}
	
	public static class ExitCode {
		public static int exit_code=1;
		public static int getExit_code() { return exit_code;}
		public static void setExit_code( int exitCode) { exit_code = exitCode; }
	}
	
}

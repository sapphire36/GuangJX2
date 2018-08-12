package org.kzcw.common.Iot.youren;
public class CRC16 {
    public static byte[] getData(String...strings) {
        byte[] data = new byte[]{};
        for (int i = 0; i<strings.length;i++) {
            int x = Integer.parseInt(strings[i], 16);
            byte n = (byte)x;
            byte[] buffer = new byte[data.length+1];
            byte[] aa = {n};
            System.arraycopy( data,0,buffer,0,data.length);
            System.arraycopy( aa,0,buffer,data.length,aa.length);
            data = buffer;
        }
        return getData(data);
    }
    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return getData(bytes);
    }
    
    private static byte[] getData(byte[] aa) {
        byte[] bb = getCrc16(aa);
        byte[] cc = new byte[aa.length+bb.length];
        System.arraycopy(aa,0,cc,0,aa.length);
        System.arraycopy(bb,0,cc,aa.length,bb.length);
        return cc;
    }
    private static byte[] getCrc16(byte[] arr_buff) {
        int len = arr_buff.length;
        int crc = 0xFFFF;
        int i, j;
        for (i = 0; i < len; i++) {
            crc = ((crc & 0xFF00) | (crc & 0x00FF) ^ (arr_buff[i] & 0xFF));
            for (j = 0; j < 8; j++) {
                if ((crc & 0x0001) > 0) {
                    crc = crc >> 1;
                    crc = crc ^ 0xA001;
                } else
                    crc = crc >> 1;
            }
        }
        return intToBytes(crc);
    }
    private static byte[] intToBytes(int value)  {
        byte[] src = new byte[2];
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }
    public static String byteTo16String(byte[] data) {
        StringBuffer buffer = new StringBuffer();
        for (byte b : data) {
            buffer.append(byteTo16String(b));
        }
        return buffer.toString();
    }
    public static String byteTo16String(byte b) {
        StringBuffer buffer = new StringBuffer();
        int aa = (int)b;
        if (aa<0) {
            buffer.append(Integer.toString(aa+256, 16)+" ");
        }else if (aa==0) {
            buffer.append("00 ");
        }else if (aa>0 && aa<=15) {
            buffer.append("0"+Integer.toString(aa, 16)+" ");
        }else if (aa>15) {
            buffer.append(Integer.toString(aa, 16)+" ");
        }
        return buffer.toString();
    }
    
    public static String checkcode(String codewithoutcheckcode)
    {
    	byte[] dd =CRC16.toBytes(codewithoutcheckcode);
    	String str = CRC16.byteTo16String(dd).toLowerCase();
    	String str1=str.substring(str.length()-6,str.length()-1);
    	StringBuilder sb=new StringBuilder();
    	sb.append(str1);
    	sb.delete(2,3);
    	String str2=sb.toString();
    	return str2;
    }
}

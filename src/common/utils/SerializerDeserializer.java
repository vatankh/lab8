package common.utils;

import java.io.*;

public class SerializerDeserializer {
    public static byte[] serilizeobj(Object obj) throws IOException {
        ByteArrayOutputStream bstream=new ByteArrayOutputStream();
        ObjectOutput oo=new ObjectOutputStream(bstream);
        oo.writeObject(obj);
        oo.close();
        return bstream.toByteArray();
    }


    public static Object deserilizObj(byte[] receive) throws IOException, ClassNotFoundException {
        ObjectInputStream iStream =new ObjectInputStream(new ByteArrayInputStream(receive));
        Object myObj1= iStream.readObject();
        iStream.close();
        return myObj1;
    }
}

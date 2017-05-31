/**
 Copyright 2013 Andrew Mahone
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package Utils;

import java.io.*;
import java.util.Arrays;

public class Serializer {

    public Object deserialize(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            return fromStream(bis);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] serialize(Object o) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            toStream(bos, o);
            bos.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return bos.toByteArray();
    }

    public Object fromFile(String filename) throws IOException {
        InputStream inFile = new FileInputStream(filename);
        return fromStream(inFile);
    }

    public void toFile (String filename, Object o) throws IOException {
        OutputStream outFile = new FileOutputStream(filename);
        toStream(outFile, o);
    }

    public void toStream (OutputStream os, Object o) throws IOException {
        ObjectOutput out = null;
        try {
            out = getObjectOutput(os);
            out.writeObject(o);
        } finally {
            if (out != null)
                out.close();
        }
    }

    public Object fromStream(InputStream is) throws IOException {
        ObjectInput in = null;
        try {
            in = getObjectInput(is);
            return in.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        } finally {
            if (in != null)
                in.close();
        }
    }

    protected ObjectInput getObjectInput(InputStream is) throws IOException {
        return new ObjectInputStream(is);
    }

    protected ObjectOutput getObjectOutput(OutputStream os) throws IOException {
        return new ObjectOutputStream(os);
    }

    private static final class Test implements Serializable {
        private final long value = 0x0102030405060708L;
    }

    private static class Test1 implements Serializable {
        Object writeReplace() {
            System.out.println("writeReplace");
            return this;
        }
    }

    private static class Test2 implements Serializable {
        Test1 t = new Test1();
    }


/*
    public static void main(String[] args) throws IOException {
        System.out.println(Arrays.toString(new Serializer().toBytes("ABC")));
        Serializer testSerializer = new Serializer() {
            @Override
            protected ObjectOutput getObjectOutput(OutputStream os) throws IOException {
                return new ObjectOutputStream(os) {
                    {
                        this.enableReplaceObject(true);
                    }

                    @Override
                    protected Object replaceObject(Object object) throws IOException {
                        System.out.printf( "replaceObject: %s\n", object);
                        return object;
                    }
                };
            }
        };

        Test2 t = (Test2) testSerializer.fromBytes(testSerializer.toBytes(new Test2()));
    }
*/
}
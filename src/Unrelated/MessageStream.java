package Unrelated;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by dongmingchao on 2017/7/19.
 */
public class MessageStream extends PrintStream{
    OutputStream meOut= new OutputStream() {
        @Override
        public native void write(int b) throws IOException;

        @Override
        public void write(byte[] b) throws IOException {
            super.write(b);
        }
    };

    public MessageStream(OutputStream out) {
        super(out);
    }
}

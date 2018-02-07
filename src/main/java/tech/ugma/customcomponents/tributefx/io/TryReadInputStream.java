package tech.ugma.customcomponents.tributefx.io;

import java.io.*;

public class TryReadInputStream extends FilterInputStream {
    private final int maxPushbackBufferSize;

    /**
     * Creates a <code>FilterInputStream</code>
     * by assigning the  argument <code>in</code>
     * to the field <code>this.in</code> so as
     * to remember it for later use.
     *
     * @param in the underlying input stream, or <code>null</code> if
     *           this instance is to be created without an underlying stream.
     */
    public TryReadInputStream(InputStream in, int maxPushbackBufferSize) {
        super(new PushbackInputStream(in, maxPushbackBufferSize));
        this.maxPushbackBufferSize = maxPushbackBufferSize;
    }

    /**
     * Reads from input stream the <code>length</code> of bytes to given buffer. The read bytes are still avilable
     * in the stream
     *
     * @param buffer the destination buffer to which read the data
     * @param offset  the start offset in the destination <code>buffer</code>
     * @aram length how many bytes to read from the stream to buff. Length needs to be less than
     *        <code>maxPushbackBufferSize</code> or IOException will be thrown
     *
     * @return number of bytes read
     * @throws java.io.IOException in case length is
     */
    public int tryRead(byte[] buffer, int offset, int length) throws IOException {
        validateMaxLength(length);

        // NOTE: below reading byte by byte instead of "int bytesRead = is.read(firstBytes, 0, maxBytesOfResponseToLog);"
        // because read() guarantees to read a byte

        int bytesRead = 0;

        int nextByte = 0;

        for (int i = 0; (i < length) && (nextByte >= 0); i++) {
            nextByte = read();
            if (nextByte >= 0) {
                buffer[offset + bytesRead++] = (byte) nextByte;
            }
        }

        if (bytesRead > 0) {
            ((PushbackInputStream) in).unread(buffer, offset, bytesRead);
        }

        return bytesRead;

    }

    public byte[] tryRead(int maxBytesToRead) throws IOException {
        validateMaxLength(maxBytesToRead);

        ByteArrayOutputStream baos = new ByteArrayOutputStream(); // as ByteArrayOutputStream to dynamically allocate internal bytes array instead of allocating possibly large buffer (if maxBytesToRead is large)

        // NOTE: below reading byte by byte instead of "int bytesRead = is.read(firstBytes, 0, maxBytesOfResponseToLog);"
        // because read() guarantees to read a byte

        int nextByte = 0;

        for (int i = 0; (i < maxBytesToRead) && (nextByte >= 0); i++) {
            nextByte = read();
            if (nextByte >= 0) {
                baos.write((byte) nextByte);
            }
        }

        byte[] buffer = baos.toByteArray();

        if (buffer.length > 0) {
            ((PushbackInputStream) in).unread(buffer, 0, buffer.length);
        }

        return buffer;

    }

    private void validateMaxLength(int length) throws IOException {
        if (length > maxPushbackBufferSize) {
            throw new IOException(
                    "Trying to read more bytes than maxBytesToRead. Max bytes: " + maxPushbackBufferSize + ". Trying to read: " +
                            length);
        }
    }

}
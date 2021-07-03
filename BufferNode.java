/**
 * Doubly Linked List node containing Buffer and
 * pointers to its previous and next node
 * @author mark paes,vishalms
 * @version 1.0
 */
public class BufferNode {
    /**
     * Buffer
     */
    private Buffer buffer;
    /**
     * Pointer to the previous buffer
     */
    private BufferNode prev;
    /**
     * pointer to the next buffer
     */
    private BufferNode next;


    /**
     * Constustor to initalise the buffer.
     * @param buffer Buffer
     */
    public BufferNode(Buffer buffer) {
        this.buffer = buffer;
    }


    /**
     *
      * @return buffer
     */
    public Buffer getBuffer() {
        return buffer;
    }


    /**
     *
     * @return pointer to previous buffer
     */
    public BufferNode getPrev() {
        return prev;
    }


    /**
     *
     * @return pointer to next buffer
     */
    public BufferNode getNext() {
        return next;
    }


    /**
     *
     * @param prev set previous pointer of the buffer
     */
    public void setPrev(BufferNode prev) {
        this.prev = prev;
    }


    /**
     *
     * @param next set next pointer of the buffer
     */
    public void setNext(BufferNode next) {
        this.next = next;
    }
}

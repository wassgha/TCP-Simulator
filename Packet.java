import java.util.*;

/**
 * A class which represents a packet
 */
public class Packet {
  private Message msg; //the enclosed message
  private int seqnum; //packets seq. number
  private int acknum; //packet ack. number
  private int checksum; //packet checksum
  private int status; // not usable = 0, not sent, usable = 1, sent = 2, acked=3,
  private int rcvwnd;

  Random ran; //random number generator

  public Packet(Message msg, int seqnum, int acknum) {
    this.msg = msg;
    this.seqnum = seqnum;
    this.acknum = acknum;
    setChecksum();
    this.ran = new Random();
    this.status = 0;
    this.rcvwnd = 0;
  }

  public Packet(Packet other) {
    this.msg = new Message(new String(other.msg.getMessage()));
    this.seqnum = other.seqnum;
    this.acknum = other.acknum;
    this.checksum = other.checksum;
    this.ran = other.ran;
  }

  public int getAcknum() {
    return acknum;
  }

  public int getSeqnum() {
    return seqnum;
  }

  public Message getMessage() {
    return msg;
  }

  public int getRcvwnd() {
    return rcvwnd;
  }

  public void setRcvwnd(int r) {
    this.rcvwnd = r;
  }

  public void setStatus (int status) {
    this.status = status;
  }

  public int getStatus () {
    return status;
  }

  public void setChecksum() {
    int cs = seqnum + acknum;
    String message = msg.getMessage();
    for (int i = 0; i < message.length(); i++) {
      cs += message.charAt(i);
    }
    // System.out.println("CHECKSUM: " + cs);
    this.checksum = cs;
  }

  public boolean isCorrupt() {
    int cs = seqnum + acknum;
    String message = msg.getMessage();
    for (int i = 0; i < message.length(); i++) {
      cs += message.charAt(i);
    }
    return this.checksum != cs;
  }

  /**
   * This method corrupts the packet the following way:
   * corrupt the message with a 75% chance
   * corrupt the seqnum with 12.5% chance
   * corrupt the ackum with 12.5% chance
   */
  public void corrupt() {
    double num =ran.nextDouble();

    if(num < 0.75)
      this.msg.corruptMessage();
    else if(num < 0.875)
      this.seqnum = this.seqnum + 1;
    else
      this.acknum = this.acknum + 1;
  }

  public String toString () {
    String output = "|\033[0;33mPacket:\033[0m\t\t\t\t\t\t\t\t|\n";

    output += "|\tSequence Number:\t" + seqnum + "\t\t\t\t\t|\n";
    output += "|\tACK Number:\t\t" + acknum + "\t\t\t\t\t|\n";
    output += "|\tChecksum:\t\t" + checksum + "\t\t\t\t\t|\n";
    output += "|\tMessage:\t\t" + msg + "\n";

    return output;
  }
}

import java.io.*;
import java.net.*;

/**
 * Simple UDP client - contact the time service
 * @author Ian Darwin, ian@darwinsys.com.
 */
public class DaytimeUDP {
	/** The UDP port number */
	public final static int DAYTIME_PORT = 13;

	/** A buffer plenty big enough for the date string */
	protected final static int PACKET_SIZE = 100;

	/** The main program that drives this network client.
	 * @param argv[0] hostname, running daytime/udp server
	 */
	public static void main(String[] argv) throws IOException {
		if (argv.length < 1) {
			System.err.println("usage: java DayTime host");
			System.exit(1);
		}
		String host = argv[0];
		InetAddress servAddr = InetAddress.getByName(host);
		DatagramSocket sock = new DatagramSocket();
		sock.connect(servAddr, DAYTIME_PORT);
		byte[] buffer = new byte[PACKET_SIZE];

		// The udp packet we will send and receive
		DatagramPacket packet = new DatagramPacket(
			buffer, PACKET_SIZE, servAddr, DAYTIME_PORT);

		/* Send zero-length packet to server */
		packet.setLength(1);
		sock.send(packet);
		System.err.println("Sent it");

		// Receive a packet and print it.
		sock.receive(packet);
		System.err.println("Got packet of size " + packet.getLength());
		System.out.println("Date on " + host + " is " + 
			new String(buffer, 0, packet.getLength()));
	}
}
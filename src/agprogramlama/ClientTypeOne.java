// Yavuz Selim GÜLER
// 1306160016

package agprogramlama;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientTypeOne
{
    private static InetAddress host;
    private static final int PORT = 2828;

    public static void main(String[] args)
    {
	try
	{
            host = InetAddress.getLocalHost();
	}
	catch(UnknownHostException uhEx)
	{
            System.out.println("\nHost ID not found!\n");
            System.exit(1);
	}
	sendMessages();
    }

    private static void sendMessages()
    {
	Socket socket = null;

	try
	{
            socket = new Socket(host,PORT);

            Scanner networkInput = new Scanner(socket.getInputStream());
            PrintWriter networkOutput =new PrintWriter(socket.getOutputStream(),true);
            
            networkOutput.println("28001");

            Scanner userEntry = new Scanner(System.in);

            String message;
            String response;
            
            //Makine Ekleme
            String[] makine = {"2820","Adi","ID","Tur","Hiz","EMPTY"};
                        
            System.out.print("Makine Adi Girin: ");
            makine[1] = userEntry.nextLine();
            System.out.print("Makine ID Girin: ");
            makine[2] = userEntry.nextLine();
            System.out.print("Makine Turu Girin: ");
            makine[3] = userEntry.nextLine();
            System.out.print("Makine Hizi Girin: ");
            makine[4] = userEntry.nextLine();
                        
            for(int i = 0;i<6;i++)
            {
                networkOutput.println(makine[i]);
            }
                        
            if(networkInput.nextLine().equals("28201"))
            {
                System.out.println("Makine basariyla baglandi.");
                System.out.println();
            }
            else if(networkInput.nextLine().equals("28209"))
            {
                System.out.println("Makine baglanamadi.");
                System.out.println();
            }
                        
            do
            {
		System.out.print("Enter message ('quit' to exit): ");
                message = userEntry.nextLine();
		networkOutput.println(message);
            }while (!message.equals("quit"));
	}
	catch(IOException ioEx)
	{
            ioEx.printStackTrace();
	}

	finally
	{
            try
            {
		System.out.println("\nClosing connection...");
		socket.close();
            }
            catch(IOException ioEx)
            {
		System.out.println("Unable to disconnect!");
		System.exit(1);
            }
	}
    }
}
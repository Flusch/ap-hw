// Yavuz Selim GÜLER
// 1306160016

package agprogramlama;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server
{
    private static ServerSocket serverSocket;
    private static final int PORT = 2828;
        
    public static String[][] makineler = new String[20][5];
    public static int makineSayac = 0;

    public static void main(String[] args) throws IOException
    {
	try
	{
            serverSocket = new ServerSocket(PORT);
            System.out.println("Port Acildi.");
	}
	catch (IOException ioEx)
	{
            System.out.println("\nUnable to set up port!");
            System.exit(1);
	}

	do
	{
            Socket client = serverSocket.accept();

            ClientHandler handler = new ClientHandler(client);

            handler.start();
	}while (true);
    }
}

class ClientHandler extends Thread
{
    private Socket client;
    private Scanner input;
    private PrintWriter output;

    public ClientHandler(Socket socket)
    {
	client = socket;

	try
	{
            input = new Scanner(client.getInputStream());
            output = new PrintWriter(client.getOutputStream(),true);
	}
	catch(IOException ioEx)
	{
            ioEx.printStackTrace();
	}
    }

    public void run()
    {
                
        String[] received = new String[50];
        String trash;
        
        String clientTuru = input.nextLine();
        
        if(clientTuru.equals("28001"))
            {
                System.out.println("\nMakine baglandi.");
            }
            else if(clientTuru.equals("28002"))
            {
                System.out.println("\nPlanlamaci baglandi.");
            }
            
        do
        {
            try
            { //bugfix
                received[0] = input.nextLine();
            }
            catch (Exception e)
            {
                received[0] = "99";
            }
                  
            if (!received[0].equals("quit"))
            {
                int statusCode = Integer.parseInt(received[0]);
                switch(statusCode){
                        
                    //kullanici adi
                    case 2801:
                        received[1] = input.nextLine();
                        if(received[1].equals("admin"))
                        {
                            output.println("28011");
                        }
                        else
                        {
                            output.println("28019");
                        }
                        break;
                            
                    //sifre
                    case 2802:
                        received[1] = input.nextLine();
                        if(received[1].equals("admin"))
                        {
                            output.println("28021");
                        }
                        else
                        {
                            output.println("28029");
                        }
                        break;
                    
                    //is istegi
                    case 2811:
                        String isTuru, isMiktari;
                        int makineId = -1;
                        
                        isTuru = input.nextLine();
                        isMiktari = input.nextLine();
                        
                        for(int i = 0;i<Server.makineSayac;i++)
                        {
                            if(Server.makineler[i][2].equals(isTuru))
                            {
                                if(Server.makineler[i][4].equals("EMPTY"))
                                {
                                    makineId = i;
                                    output.println("28111");
                                    //Uygun makine bulundu
                                    break;
                                }
                                /*else if(Server.makineler[i][4].equals("BUSY"))
                                {
                                    output.println("28112");
                                    output.println("Uygun turde makine var fakat bosta degil. Is kuyruga aliniyor.");
                                    break;
                                }*/
                            }
                        }
                        
                        if(makineId == -1){
                            output.println("28119");
                            //Uygun turde makine yok
                            break;
                        }
                        
                        try {
                            int sure = Integer.parseInt(isMiktari)/Integer.parseInt(Server.makineler[makineId][3]);
                            output.println("Is " + sure + " saniye surecek");
                            Server.makineler[makineId][4] = "BUSY";
                            Thread.sleep(sure*1000);
                            Server.makineler[makineId][4] = "EMPTY";
                        }
                        catch (Exception e){}
                        
                        break;
                    
                    //makine listeleme
                    case 2812:
                        output.println(Server.makineSayac);
                        
                        if(Server.makineSayac>0)
                        {
                            output.println("28121");
                            for(int i = 0;i<Server.makineSayac;i++)
                            {
                                for(int j = 0;j<5;j++)
                                {
                                    output.println(Server.makineler[i][j]);
                                }
                            }
                        }
                        else
                        {
                            output.println("28129");
                        }
                        break;
                        
                    //Makine musaitlik durumu    
                    case 2813:
                        //debug
                        System.out.println("Makine Musaitlik Bilgisi");
                        //debug
                        output.println(Server.makineSayac);
                        if(Server.makineSayac>0){
                            output.println("28131");
                            for(int i = 0;i<Server.makineSayac;i++)
                            {
                                    output.println(Server.makineler[i][4]);
                            }
                        }
                        else
                        {
                            output.println("28139");
                        }
                        break;
                        
                    //makine ilk giris
                    case 2820:
                        for(int i = 1;i<6;i++)
                        {
                            received[i] = input.nextLine();
                        }
                        
                        if(Server.makineSayac<20)
                        {
                            for(int i = 0;i<5;i++)
                            {
                                Server.makineler[Server.makineSayac][i] = received[i+1];
                            }
                            output.println("28201");
                        }
                        else
                        {
                            System.out.println("Maksimum Makineye Ulasildi.");
                            output.println("28209");
                        }
                        Server.makineSayac++;
                        break;
                        
                    default:
                        break;
                }
            }
        }while (!received[0].equals("quit"));

        try
        {
            if (client!=null)
            {
		System.out.println("Closing down connection...");
		client.close();
            }
        }
            
        catch(IOException ioEx)
        {
            System.out.println("Unable to disconnect!");
        }
    }   
}

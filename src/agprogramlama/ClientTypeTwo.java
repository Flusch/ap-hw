// Yavuz Selim GÜLER
// 1306160016

package agprogramlama;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientTypeTwo
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
            PrintWriter networkOutput = new PrintWriter(socket.getOutputStream(),true);
            
            networkOutput.println("28002");
            
            Scanner userEntry = new Scanner(System.in);

            String message = "";
                        
            String kullaniciAdi;
            String onay;
            int yanlisSifreSayac = 0;
                        
            //kullanici adi kontrolu
            while(true)
            {                
                System.out.println();
                System.out.print("Kullanici Adi Girin: ");
                kullaniciAdi = userEntry.nextLine();
                System.out.println();
                
                if(kullaniciAdi.equals("quit"))
                {
                    message = kullaniciAdi;
                    break;
                }
                
                networkOutput.println("2801");
                networkOutput.println(kullaniciAdi);
                onay = networkInput.nextLine();
                if(onay.equals("28011"))
                {
                    System.out.println("Onaylandi. Sifrenizi girin;");
                    break;
                }
                else if(onay.equals("28019"))
                {
                    System.out.println("Kullanici adi sistemde mevcut degil. Tekrar deneyin. (cikmak isterseniz quit yazın)");
                }
            }
                        
            String sifre;
            
            if(!kullaniciAdi.equals("quit"))
            {
                //sifre kontrolu
                while(true)
                {
                    System.out.println();
                    System.out.print("Sifre Girin: ");
                    sifre = userEntry.nextLine();
                    System.out.println();
                    networkOutput.println("2802");
                    networkOutput.println(sifre);
                    onay = networkInput.nextLine();

                    if(onay.equals("28021"))
                    {
                        System.out.println("Sifre Onaylandi.");
                        System.out.println("Giris yapiliyor...");
                        break;
                    }
                    else if(onay.equals("28029"))
                    {
                        System.out.println("Girdiginiz sifre yanlis. Tekrar deneyin.");
                        System.out.println("Toplam 3 kez yanlis girerseniz sistem kapanacak.");
                        yanlisSifreSayac++;
                    }
                    if(yanlisSifreSayac == 3){
                        message = "quit";
                        System.out.println("Sifrenizi 3 kez yanlis girdiniz. Kapaniyor...");
                        break;
                    }
                }
            }
            
            if(!message.equals("quit"))
            {
                
                message = Menu();
                
                if(!message.equals("quit"))
                {
                    do
                    {
                        System.out.print("Enter message ('quit' to exit): ");
                        message = userEntry.nextLine();
                        networkOutput.println(message);
                    }while (!message.equals("quit"));
                }
            }
            
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
        
    public static String Menu()
    {
        Socket socket = null;
        
        try
        {
            socket = new Socket(host,PORT);

            Scanner networkInput = new Scanner(socket.getInputStream());
            PrintWriter networkOutput = new PrintWriter(socket.getOutputStream(),true);
            
            networkOutput.println(); //bugfix
                
            int secim, sayac;
            String statusCode;
        
            Scanner userEntry = new Scanner(System.in);
                
            System.out.println();
            System.out.println("Menuye Hosgeldin");
            System.out.println("1. Makineleri listele.");
            System.out.println("2. Makinelerin musaitlik durumlarını getir.");
            System.out.println("3. Makinelere is ver.");
            System.out.println("9. Sistemi kapat.");
            System.out.print("Secim Gir: ");
            secim = userEntry.nextInt();
            System.out.println();

            switch(secim)
            {
                case 1: //2812
                    networkOutput.println("2812");
                    sayac = networkInput.nextInt();
                    networkInput.nextLine(); //bugfix
                    statusCode = networkInput.nextLine();
                    if(statusCode.equals("28121"))
                    {
                        for(int i = 0; i < sayac; i++)
                        {                            
                            System.out.println("-----Makine " + i+1 + " Bilgileri-----");
                            System.out.println("Makine Adı: " + networkInput.nextLine());
                            System.out.println("Makine ID: " + networkInput.nextLine());
                            System.out.println("Makine Turu: " + networkInput.nextLine());
                            System.out.println("Makine Hizi: " + networkInput.nextLine());
                            System.out.println("Makine Durumu " + networkInput.nextLine());
                        }
                    }
                    else if(statusCode.equals("28129"))
                    {
                        System.out.println("Sistemde Makine Yok.");
                    }
                    
                    break;
                    
                case 2: //2813
                    networkOutput.println("2813");
                    sayac = networkInput.nextInt();
                    networkInput.nextLine(); //bugfix
                    statusCode = networkInput.nextLine();
                    if(statusCode.equals("28131")){    
                        for(int i = 0; i < sayac; i++)
                        {                            
                            System.out.println( i+1 + ". Makine: " + networkInput.nextLine());
                        }
                    }
                    else if(statusCode.equals("28139"))
                    {
                        System.out.println("Sistemde Makine Yok.");
                    }
                    break;
                        
                case 3: //2811
                    String temp;
                    
                    temp = userEntry.nextLine(); //bugfix
                    
                    networkOutput.println("2811");
                    
                    System.out.println();
                    System.out.print("Isin turunu girin: ");
                    temp = userEntry.nextLine();
                    networkOutput.println(temp);
                    
                    System.out.print("Isin miktarini girin: ");
                    temp = userEntry.nextLine();
                    networkOutput.println(temp);
                    System.out.println();
                    
                    statusCode = networkInput.nextLine();
                    
                    if(statusCode.equals("28111"))
                    {
                        System.out.println("Uygun makine bulundu.");
                        System.out.println(networkInput.nextLine());
                    }
                    /*else if(statusCode.equals("28112"))
                    {
                        System.out.println("Uygun makine bulundu fakat boşta degil. Is kuyruga ekleniyor.");
                        System.out.println(networkInput.nextLine());
                    }*/
                    else if(statusCode.equals("28119"))
                    {
                        System.out.println("Uygun turde makine yok.");
                    }
                    
                    break;
                        
                case 4:

                    break;
                        
                case 9:
                    try
                    {
                        socket.close();
                    }
                    catch(IOException ioEx)
                    {
                        System.out.println("Unable to disconnect!");
                    }
                    return "quit";

                default:
                    break;
            }
        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }

        finally
        {
            try
            {
                socket.close();
            }
            catch(IOException ioEx)
            {
                System.out.println("Unable to disconnect!");
            }
        }
        
        Menu();
        return "quit";
    }
}
package core;

import java.io.*;
import java.net.Socket;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

public class Conexion extends Thread
{
	private PrintWriter envios; 

	private BufferedReader recibos;

	private Socket vigente;

	private PublicKey clientkey; 

	private PublicKey serverkey;
	
	private String cifrado;

	public Conexion(Socket StoC, int idassigned, File archiv, byte[] hash, PublicKey llave, String cipher)
	{
		vigente = StoC; serverkey = llave; cifrado = cipher;
		try 
		{
			envios = new PrintWriter(vigente.getOutputStream(), true);
			recibos = new BufferedReader(new InputStreamReader(vigente.getInputStream()));			
		} 
		catch (Exception e) 
		{	e.printStackTrace();	}
	}

	public void run()
	{
		try 
		{
			int lC = Integer.parseInt(recibos.readLine());
			byte[] llaveC = new byte[lC];
			vigente.getInputStream().read(llaveC, 0, lC);
			X509EncodedKeySpec ks = new X509EncodedKeySpec(llaveC);
			KeyFactory kf = KeyFactory.getInstance(cifrado);
			clientkey = kf.generatePublic(ks);
			byte[] llaveS = serverkey.getEncoded(); 
			envios.println(llaveS.length);
			envios.print(llaveS);
			//Aqu� se hace el handshake y se env�a el archivo
			recibos.close();
			envios.close();
			vigente.close();
		}
		catch (Exception e) 
		{	e.printStackTrace();	}
	}

}

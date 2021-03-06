package core;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ElaboradorClientes 
{
	private static Scanner parametrizador = new Scanner(System.in);

	private static int totalClients = 0; 

	private static ArrayList<Cliente> encargo; 

	private static String ipaddress;

	private static int port;

	private static String hashing;
	
	public static int contador = 0; 

	public static ArrayList<RegistroLog> logcliente;
	
	private static void registrarLog()
	{
		File reporteC = new File("clientlog/Prueba_" + totalClients);
		try
		{
			PrintWriter reportador = new PrintWriter(reporteC);
			reportador.println("LOG FOR " + new Date());
			reportador.println("File Name: " + logcliente.get(0).getFileName());
			reportador.println("File Size: " + logcliente.get(0).getFileSize() + " MB");
			for(RegistroLog logC : logcliente)
				reportador.print(logC.toString());
			reportador.flush();	reportador.close();
		}
		catch(Exception e)
		{	e.printStackTrace();	}
	}

	public static void main(String[] args) 
	{
		encargo = new ArrayList<Cliente>(); hashing = "MD5";
		System.out.println("Bienvenido al Elaborador. Diga cu�ntos clientes va a crear");
		totalClients = parametrizador.nextInt(); 
		System.out.println("Por favor, ingrese la direcci�n IP del servidor");
		ipaddress = parametrizador.next(); 
		System.out.println("Ahora indique el puerto de conexion");
		port = parametrizador.nextInt();
		if(totalClients > 0)
		{
			logcliente = new ArrayList<RegistroLog>();
			while(encargo.size() < totalClients)
			{	Cliente neu = new Cliente(ipaddress, port, hashing); encargo.add(neu); neu.start();	}
			while(encargo.size() > 0)
			{
				Cliente c = encargo.get(0);
				if(c.isDone()) { logcliente.add(c.getReporte()); encargo.remove(c); }
			}
			registrarLog();
		}
	}
}

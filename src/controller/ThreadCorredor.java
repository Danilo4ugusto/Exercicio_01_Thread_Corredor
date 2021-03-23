package controller;

import java.util.concurrent.Semaphore;


public class ThreadCorredor extends Thread
{
	private int percurso;
	private final int distancia = 200;
	private static int chegada[] = new int [4];
	private static int posicao;
	private int idPessoa;
	private Semaphore semaforo;
	
	public ThreadCorredor(int idPessoa, Semaphore semaforo)
	{
		this.idPessoa = idPessoa;
		this.semaforo = semaforo;
	}
	
	@Override
	public void run()
	{
		Correr();
		
		try
		{
			semaforo.acquire();
			CruzarPorta();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			semaforo.release();
		}
		
		if(posicao == 4)
		{
			System.out.println("-----------------------------\n          Classificação:");
			
			for(int i = 0; i < chegada.length; i++)
			{				
				System.out.println(chegada[i] + "ª Pessoa foi o " + (i+1) + "º a chegar");
			}
			System.out.println("-----------------------------");
		}
	}

	private void Correr()
	{
		
		while(percurso < distancia)
		{
			int passo = (int) ((Math.random() * 2) + 4);
			percurso += passo;
			if ((distancia - percurso) > 0)
			{
				System.out.println("Pessoa #" + idPessoa + " andou " + passo + "m, faltam: " + (distancia - percurso) + "m.");				
			}
			else
			{				
				System.out.println("Pessoa #" + idPessoa + " andou " + passo + "m, faltam: 0m.");				
			}
			try
			{
				sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void CruzarPorta()
	{
		System.out.println("Pessoa #" + idPessoa + " chegou a porta.");
		int tempo = (int) ((Math.random() * 1001) + 1000);
		try
		{
			sleep(tempo);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("Pessoa #" + idPessoa + " levou " + (tempo / Math.pow(10, 3)) +"s para atravessar a porta.");
		chegada[posicao] = idPessoa;
		posicao ++;
	}
}
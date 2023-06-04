import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import javax.lang.model.element.Element;

import java.util.Scanner;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Test {
	
	private static String justText;
	private static StringTokenizer token;
	
	public Test()
	{
		
	}
	public static void main(String[] args) throws IOException
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Please input the link of the site you'd like to check / save");
		String url = scan.nextLine();
		checkText(url);
		System.out.println(justText);
		if (checkDifference())
		{
			System.out.println("Looks the same");
		}
		else
		{
			System.out.println("Something's changed");
		}
		writeToFile();
	}
	
	public static void checkText(String url) throws IOException
	{
		try {
			if (url.contains("C:\\"))
			{
				BufferedReader reader = new BufferedReader(new FileReader(url));
				
				String fileText = "";
				String line;

				while ((line = reader.readLine()) != null)
				{
					fileText += line + "\n";
				}
				reader.close();
				
				Document filet = Jsoup.parse(fileText);
				String ftext = filet.text();
				
				ftext = ftext.replaceAll("[^a-zA-Z ]", "");
				StringTokenizer tokens = new StringTokenizer(ftext);
				justText = ftext;
				justText = justText.replaceAll("\\s", "");
				token = tokens;
			}
			else
			{
				Document document = Jsoup.connect(url).get();
				String text = document.text();
				text = text.replaceAll("[^a-zA-z ]", "");
				StringTokenizer tokens = new StringTokenizer(text);
				System.out.println(text);
				justText = text;
				justText = justText.replaceAll("\\s", "");
				token = tokens;
			}
		}
		catch (IOException e)
		{
			System.out.println("Something went wrong");			
		}
	}
	
	public static void writeToFile()
	{
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("OriginalPage.txt"));
			writer.write(justText);
			writer.close();
		}
		catch (Exception e)
		{	
			System.out.println("Something went wrong");
		}
	}
	
	public static boolean checkDifference() throws FileNotFoundException
	{
		try {
			BufferedReader reader = new BufferedReader(new FileReader("OriginalPage.txt"));
			String line;
			String text = "";
			while ((line = reader.readLine()) != null)
				{
					text += line + "\n";
				}
			text = text.replaceAll("\\s", "");
			System.out.println(text);
			if (text.equals(justText))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			System.out.println("Something went wrong");
		}
		
		
		return false;
	}
}

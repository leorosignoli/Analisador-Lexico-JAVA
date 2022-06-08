package br.edu.uniceub.lexical.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class LanguageSextafFase {

	public static void main(String[] args) throws IOException {

		try {
			ArrayList<String> strings = portugol();
			String fullString = strings.stream().reduce("", (a, b) -> a + b);
			LexicalAnalyzer lexical = new LexicalAnalyzer(new StringReader(fullString));
			String titulo = "\n\n" + "|Lexema\t\t|\tToken\t\t\t|Posi��o |";
			System.out.println(titulo);
			System.out.println(StringUtils.repeat("-", 58));
			lexical.yylex();
			lexical.yytext();
		} catch (Exception e) {
			System.out.println("\nTexto inv�lido, cessando an�lise l�xica"
					+ "." + "\n" + "Erro: " + e.getLocalizedMessage());
		}
	}

	public static ArrayList<String> portugol() throws IOException {

		String rootPath = Paths.get("").toAbsolutePath().toString();
		String subPath = "/src/main/java/br/edu/uniceub/lexical/analyzer/";

		File file = new File(rootPath + subPath + "portugol.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		ArrayList<String> lines = new ArrayList<>();
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		fr.close(); // closes the stream and release the resources
		System.out.println("Conte�do do arquivo(linhas separadas por v�rgula)\n");
		System.out.println(lines); // returns a string that textually represents the object
		return lines;

	}
}
